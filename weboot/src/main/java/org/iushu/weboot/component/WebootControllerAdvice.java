package org.iushu.weboot.component;

import org.iushu.weboot.exchange.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author iuShu
 * @since 6/24/21
 */
@ControllerAdvice
public class WebootControllerAdvice {

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        return Response.failure(e.getMessage());
    }

}
