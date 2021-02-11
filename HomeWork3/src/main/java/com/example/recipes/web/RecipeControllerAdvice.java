package com.example.recipes.web;

import com.example.recipes.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.FileSystemException;

@ControllerAdvice
@Slf4j
public class RecipeControllerAdvice {
    @ExceptionHandler({MaxUploadSizeExceededException.class, FileSystemException.class})
    @Order(1)
    public ModelAndView handleUploadExceptions(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("errors");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler({com.example.recipes.exception.UnauthorisedModificationException.class, com.example.recipes.exception.UnexistingEntityException.class, com.example.recipes.exception.InvalidEntityDataException.class})
    @Order(2)
    public ModelAndView handle(Exception ex) {
        log.error("Article Controller Error:", ex);
        ModelAndView modelAndView = new ModelAndView("errors");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("continueUrl", "/recipes");
        return modelAndView;

    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser(Authentication authentication) {
        if (authentication != null) {
            User loggedId = (User) authentication.getPrincipal();
            return loggedId;
        } else {
            return null;
        }
    }

    @ModelAttribute("loggedUserName")
    public String getLoggedUserNames(Authentication authentication) {
        if (authentication != null) {
            User loggedId = (User) authentication.getPrincipal();
            return loggedId.getName();
        } else {
            return null;
        }
    }

}
