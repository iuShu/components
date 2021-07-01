package org.iushu.weboot.component;

import org.iushu.weboot.exchange.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @author iuShu
 * @since 6/24/21
 */
@ControllerAdvice
public class WebootControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger(WebootControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exception(Exception e) {
        logger.error("", e);
        return new ResponseEntity<Response>(Response.failure("System error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
