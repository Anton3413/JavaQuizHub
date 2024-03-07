package com.example.javaquizhub.exception.exception_handlers;

import com.example.javaquizhub.exception.custom_exceptions.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({TokenException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView handleTokenExpiredException(HttpServletRequest request,TokenException e ){
        return getModelAndView(request, HttpStatus.UNAUTHORIZED,"401-error-page",e);
    }

    public ModelAndView getModelAndView(HttpServletRequest request,HttpStatus status,
                                        String viewName,Exception e){

        log.error("Exception raised = {} :: URL = {} :: Exception Type = {}",
                e.getMessage(), request.getRequestURL(),e.getClass().getSimpleName());

        ModelAndView mav = new ModelAndView(viewName,status);
        mav.addObject("code", status.value() + " / " + status.getReasonPhrase());
        mav.addObject("message", e.getMessage());

        return mav;
    }

}
