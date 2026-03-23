package ru.alexandernosevich.springoauthhw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alexandernosevich.springoauthhw.repository.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping
    public String adminPanel(@AuthenticationPrincipal OAuth2User principal, Model model) {

        //отдать логи админа в макет/шаблон
        model.addAttribute("adminLogin", principal.getAttribute("login"));

        //отдаем всех юзеров из БД
        model.addAttribute("users", userRepository.findAll());

        return "admin";
    }
}
