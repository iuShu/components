package org.iushu.project.components;

import org.iushu.project.bean.Actor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;

/**
 * Typically @ExceptionHandler, @InitBinder and @ModelAttribute methods apply within the @Controller class in which they
 * are declared. @ControllerAdvice are apply such methods to more globally.
 *
 * Global @ExceptionHandler methods (from @ControllerAdvice) are applied after local ones (from @Controller).
 * By contrast, @InitBinder and @ModelAttribute methods are applied before local ones.
 *
 * By default, @ControllerAdvice methods apply to every request, but we can narrow that down to a subset of controllers.
 * @see ControllerAdvice#annotations() controllers with specified annotations
 * @see ControllerAdvice#value() controllers under base package
 * @see ControllerAdvice#basePackages()  controllers under base package
 * @see ControllerAdvice#assignableTypes() controllers assigned from spcified classes
 *
 * ControllerAdvice combines @ResponseBody that essentially means @ExceptionHandler methods are rendered to the response
 * body through message conversion.
 * @see org.springframework.web.bind.annotation.RestControllerAdvice
 *
 * @author iuShu
 * @since 4/2/21
 */
@ControllerAdvice
public class TraceControllerAdvice {

    @InitBinder
    public void globalInitBinder(WebDataBinder dataBinder) {
        System.out.println("GlobalBinder: " + dataBinder.getClass().getName());
    }

    @ModelAttribute
    public void globalModelAttribute(Model model) {
        Actor actor = new Actor();
        actor.setActor_id((short) 998);
        actor.setFirst_name("Global");
        actor.setLast_name("Model");
        actor.setLast_update(new Date());
        model.addAttribute("globalModel", actor);
        System.out.println("GlobalModel: " + actor);
    }

    @ExceptionHandler(Exception.class)
    public void globalExceptionHandler(Exception e) {
        System.out.println("GlobalException: " + e);
    }

}
