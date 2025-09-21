package com.ecodoobiz.hlbudi;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404"; // Refers to 404.html in src/main/resources/templates
            }
            // You can add more error handling here for other status codes
            // For example, for 500 errors:
            // if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            //     return "500"; // Refers to 500.html
            // }
        }
        return "error"; // Default error page if no specific handling
    }

    public String getErrorPath() {
        return null;
    }
}