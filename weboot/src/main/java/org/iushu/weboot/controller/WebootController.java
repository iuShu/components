package org.iushu.weboot.controller;

import org.iushu.weboot.annotation.AccessLimit;
import org.iushu.weboot.component.AuthenticationManager;
import org.iushu.weboot.bean.User;
import org.iushu.weboot.component.SessionManager;
import org.iushu.weboot.exchange.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * @author iuShu
 * @since 6/24/21
 */
@RestController
public class WebootController {

    private final Logger logger = LoggerFactory.getLogger(WebootController.class);

    private static final String RSA_PBK = "wbpbk";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SessionManager sessionManager;

    @RequestMapping("/")
    @AccessLimit(login = false)
    public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
        sessionManager.getLoggedUser(request, response);
        return new ModelAndView("forward:index.html");
    }

    @RequestMapping("/index")
    @AccessLimit(login = false)
    public ModelAndView index() {
        return new ModelAndView("forward:index.html");
    }

    @PostMapping("/encrypt_key")
    @AccessLimit(login = false)
    public Response<String> getEncryptKey(HttpSession session) {
        String publicKey = (String) session.getAttribute(RSA_PBK);
        if (!isEmpty(publicKey))
            return Response.payload(publicKey);

        publicKey = authenticationManager.selectPublicKey();
        session.setAttribute(RSA_PBK, publicKey);
        return Response.payload(publicKey);
    }

    @PostMapping("/login")
    @AccessLimit(login = false)
    public Response login(HttpServletRequest request, HttpServletResponse response,
                          String username, String password, String pbk) {
        String sessionPbk = (String) request.getSession().getAttribute(RSA_PBK);
        if (sessionPbk == null || !sessionPbk.equals(pbk))
            return Response.failure("Illegal Access");

        password = authenticationManager.decode(password, pbk);
        logger.debug(String.format("user: %s\tpwd: %s", username, password));
        User user = sessionManager.login(request, response, username, password);
        if (user == null)
            return Response.failure("No such user, please register first");
        else if (user == SessionManager.PASSWORD_WRONG)
            return Response.failure("Wrong password, please try again");
        return Response.payload(user);
    }

    @PostMapping("/logout")
    public Response logout(HttpServletRequest request, HttpServletResponse response) {
        sessionManager.logout(request, response);
        return Response.success();
    }

}
