package com.informatics.CSCB869.controllers.view;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class HomeController {
    
    @GetMapping
    public String getHome(){
        return "home.html";
    }
}
