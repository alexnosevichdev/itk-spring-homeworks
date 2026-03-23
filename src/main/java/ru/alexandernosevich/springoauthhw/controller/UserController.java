package ru.alexandernosevich.springoauthhw.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    //ЛК Пользователя
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal,
                       Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("login", principal.getAttribute("login"));
        model.addAttribute("email", principal.getAttribute("email"));
        return "user";
    }
}
