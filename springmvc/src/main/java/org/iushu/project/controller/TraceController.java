package org.iushu.project.controller;

import org.iushu.project.bean.Actor;
import org.iushu.workflow.Application;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.SessionAttributeMethodArgumentResolver;

import javax.servlet.ServletRegistration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * @author iuShu
 * @since 3/30/21
 */
@RestController
@RequestMapping("/trace")
@SessionAttributes("preCheckModel")
public class TraceController {

    // url requires containing param entry 'name=iuShu'
    @RequestMapping(value = "/param/match", params = "name=iuShu")
    public String requestParameterCondition() {
        return "Request's parameter matched";
    }

    @RequestMapping(value = "/header/match", headers = "Host=www.iushu.com:8080")
    public String requestHeaderCondition() {
        return "Request's header matched";
    }

    @RequestMapping("/param")   // http://../param?key=name
    public String requestParam(@RequestParam("key") String key) {
        return key;
    }

    @RequestMapping("/param/map")   // http://../param/map?key=name&value=Rod
    public String requestParamMap(@RequestParam Map<String, String> params) {
        return params.toString();
    }

    @RequestMapping("/header")
    public String requestHeader(@RequestHeader("Accept-Encoding") String encoding, @RequestHeader("Connection") String connection) {
        return String.format("Accept-Encoding: %s<br>Connection: %s", encoding, connection);
    }

    @RequestMapping("/headers")
    public String requestHeaders(@RequestHeader MultiValueMap<String, String> headerMap, @RequestHeader HttpHeaders headers) {
        return String.format("MultiValueMap headers: %s<br><br>HttpHeaders: %s", headerMap, headers);
    }

    @RequestMapping("/cookie/set")
    public String setCookieValue(HttpServletRequest request, HttpServletResponse response) {
        Application.checkCookies(request.getCookies());
        Cookie cookie = new Cookie("iushu", "5889");
        cookie.setPath("/");
        cookie.setDomain("iushu.com");
        cookie.setMaxAge(10);
        response.addCookie(cookie);
        return "Cookie value setup finished";
    }

    @RequestMapping("/cookie/get")
    public String cookieValue(@CookieValue("iushu") String iushu) {
        return String.format("cookie value: %s", iushu);
    }

    // By default, any method argument that is not simple value type and is not resolved
    // by any other argument resolver is treated as if it were annotated with @ModelAttribute.
    @RequestMapping("/actor")
    public Actor formModel(Actor actor) {   // equals to: @ModelAttribute Actor actor
        actor.setActor_id((short) new Random().nextInt(1000));
        actor.setLast_update(new Date());
        return actor;
    }

    @RequestMapping("/actor/{first_name}/model/{last_name}")
    public Actor modelAttribute(@ModelAttribute Actor actor) {
        actor.setActor_id((short) new Random().nextInt(1000));
        actor.setLast_update(new Date());
        return actor;
    }

    /**
     * Can be served before action method as model transporting, authentication, logger and so on.
     * NOTE: Only available at current controller.
     *
     * @see AbstractHandlerMethodAdapter#handle
     * @see RequestMappingHandlerAdapter#handleInternal
     * @see RequestMappingHandlerAdapter#invokeHandlerMethod
     * @see ModelFactory#initModel
     * @see ModelFactory#invokeModelAttributeMethods invoke ModelAttribute methods before action method
     * ...
     * @see ServletInvocableHandlerMethod#invokeAndHandle
     * @see ServletInvocableHandlerMethod#invokeForRequest
     */
    @ModelAttribute("preCheckModel")
    public Model preCheck() {
        System.out.println("[preCheck] ModelAttribute method is invoked");
        Model model = new ExtendedModelMap();
        Actor actor = new Actor();
        actor.setActor_id((short) 999);
        actor.setFirst_name("Pioneer");
        actor.setLast_name("Soldier");
        actor.setLast_update(new Date());
        model.addAttribute("pioneer", actor);
        return model;
    }

    /**
     * @see #preCheck() supported by
     *
     * Supports @ModelAttribute method parameter and binding parameter with binding result.
     * @see ServletModelAttributeMethodProcessor
     */
    @RequestMapping("/model/transport")
    public Actor modelFlow(@ModelAttribute("preCheckModel") Model model, BindingResult result) {
        System.out.println("[BindingResult] " + (result.hasErrors() ? "Binding ok" : result.toString()));
        return (Actor) model.getAttribute("pioneer");
    }

    /**
     * @see #preCheck() supported by
     */
    @RequestMapping("/session/attr/set")
    public Actor sessionAttribute(@ModelAttribute("preCheckModel") Model model, HttpServletRequest request) {
        request.getSession(true);   // create session for request
        return (Actor) model.getAttribute("pioneer");
    }

    /**
     * @see #sessionAttribute(Model, HttpServletRequest)  supported by
     *
     * Supports @SessionAttribute method parameter
     * @see SessionAttributeMethodArgumentResolver
     */
    @RequestMapping("/session/attr/get")
    public Actor sessionAttribute(@SessionAttribute Model preCheckModel) {
        return (Actor) preCheckModel.getAttribute("pioneer");
    }

    /**
     * Redirect related components
     * @see org.springframework.web.servlet.mvc.support.RedirectAttributes
     * @see org.springframework.web.servlet.view.RedirectView
     * @see RequestMappingHandlerAdapter#ignoreDefaultModelOnRedirect
     *
     * Another way of passing data to the redirect target is by using flash attributes.
     * @see org.springframework.web.servlet.FlashMap holding flash attributes (not direct use)
     * @see org.springframework.web.servlet.FlashMapManager managed flush attributes (not direct use)
     * @see org.springframework.web.servlet.mvc.support.RedirectAttributes use this to propogate the FlashMap
     * @see org.springframework.web.servlet.support.RequestContextUtils access FlushMap from anywhere
     */
    @RequestMapping("/redirect")
    public ModelAndView redirect() {
//        return "redirect:/trace/actor";   // @ResponseBody will invalid this redirect type.
        return new ModelAndView("/trace/actor?first_name=Tiger");
    }

    /**
     * TODO finish this test
     *
     * Require multiple resolver supports like Common Upload module or Servlet 3.0 upload config.
     *
     * Servlet 3.0 needs to config the file storing directory.
     * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#customizeRegistration
     */
    @PostMapping("/upload")
    public String multipleFile(MultipartFile file) {
        Resource resource = file.getResource();
        return String.format("Upload: %s<br>Resource: %s", "NAME", resource.getClass().getName());
    }

    /**
     * NOTE: Require configure the UrlPathHelper first.
     * @see org.iushu.project.ProjectConfiguration#configurePathMatch(PathMatchConfigurer)
     */
    @RequestMapping("/matrix/{id}")  // http://../matrix/678;name=Matrix;sex=1
    public String matrixVariable(@PathVariable int id, @MatrixVariable String name, @MatrixVariable("sex") int gender) {
        return String.format("%s\t%s\t%s", id, name, gender);
    }

}
