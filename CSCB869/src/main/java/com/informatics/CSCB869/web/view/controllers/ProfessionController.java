package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.data.repository.ProfessionRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.ProfessionService;
import com.informatics.CSCB869.web.view.model.CreateProfessionViewModel;
import com.informatics.CSCB869.web.view.model.ProfessionViewModel;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import org.modelmapper.TypeToken;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/professions")
public class ProfessionController {
    
    private ProfessionService professionService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getProfessions(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<ProfessionViewModel>>() {}.getType();
        final Page<ProfessionViewModel> pageOfProfessions =
                modelMapper.map(professionService.getProfessionsPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfProfessions", pageOfProfessions);
        int totalPages = pageOfProfessions.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/professions/professions";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("profession", new ProfessionViewModel());
        return "/professions/create-profession.html";
    }
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("profession") CreateProfessionViewModel profession,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("profession", new CreateProfessionViewModel());
            return "/professions/create-profession.html";
        }
        professionService.create(modelMapper.map(profession, CreateProfessionDTO.class));
        return "redirect:/professions/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id) {
        professionService.delete(id);
        return "redirect:/professions/" + page + "/" + size;
    }

    @GetMapping("/{page}/{size}/edit/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        model.addAttribute("profession", modelMapper.map(professionService.getProfession(id),
        CreateProfessionViewModel.class));
        return "/professions/edit-profession";
    }

    @PostMapping("/{page}/{size}/update/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, 
    @Valid @ModelAttribute("profession") CreateProfessionViewModel profession, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/professions/"+page+"/"+size+"/edit/"+id;
        }
        try{
            professionService.update(id, modelMapper.map(profession,CreateProfessionDTO.class));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/professions/"+page+"/"+size;
    }
    
    private ProfessionViewModel convertToProfessionViewModel(ProfessionDTO profession) {
        return modelMapper.map(profession, ProfessionViewModel.class);
    }
}
