package com.informatics.CSCB869.web.view.controllers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {
    
    @GetMapping
    public String getIndex(Model model){
        model.addAttribute("welcome", "Welcome");
        return "index";
    }
}
