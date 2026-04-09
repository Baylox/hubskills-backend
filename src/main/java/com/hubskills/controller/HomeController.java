package com.hubskills.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Bienvenue sur l'API HubSkills ! Vous pouvez accéder aux compétences via /api/skills";
    }
}
