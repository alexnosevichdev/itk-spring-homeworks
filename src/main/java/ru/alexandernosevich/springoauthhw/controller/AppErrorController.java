package ru.alexandernosevich.springoauthhw.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if(statusCode != null){
            if (statusCode == HttpStatus.UNAUTHORIZED.value()){
                //401
                model.addAttribute("errorMessage", "Войди в систему");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                //403
                model.addAttribute("errorMessage", "нет досутпа");
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorMessage", "не найден");
            } else {
                model.addAttribute("errorMEssage", "Сервак упал");
            }
            model.addAttribute("statusCode", statusCode);
        }
        //в error.html
        return "error";
    }
}
