package com.rainbow;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorHandler  implements ErrorController {

    @Override
    public String getErrorPath() {
        System.out.println("checking ");

        return "/controller?page=noaccesspage";
    }
}
