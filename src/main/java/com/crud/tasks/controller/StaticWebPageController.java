package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class StaticWebPageController {
    @RequestMapping("/")
    public String index(Map<String, Object> model) {
        model.put("kodilla_url","https://kodilla.com/en");
        model.put("github_url","https://github.com/leszrom/tasks");
        model.put("frontend_url","https://leszrom.github.io/");
        model.put("swagger_url","https://infinite-atoll-21692.herokuapp.com/swagger-ui.html#/");
        return "index";
    }
}
