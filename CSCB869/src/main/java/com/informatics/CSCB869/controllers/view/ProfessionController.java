package com.informatics.CSCB869.controllers.view;

import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.services.ProfessionService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/professions")
public class ProfessionController {
    
    private ProfessionService professionService;

    @GetMapping
    public String getProfessions(Model model){
        final List<Profession> professions = professionService.getProfessions();
        model.addAttribute("professions", professions);
        return "/professions/professions.html";
    }

    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("profession", new Profession());
        return "/professions/create-profession.html";
    }

    @PostMapping("/create")
    public String createProfession(@ModelAttribute Profession profession) {
        professionService.createProfession(profession);
        return "redirect:/professions";
    }
}
