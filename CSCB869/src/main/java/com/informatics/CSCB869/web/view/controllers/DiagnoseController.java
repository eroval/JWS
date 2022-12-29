package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.repository.DiagnoseRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DiagnoseService;
import com.informatics.CSCB869.web.view.model.CreateDiagnoseViewModel;
import com.informatics.CSCB869.web.view.model.DiagnoseViewModel;

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
@RequestMapping("/diagnoses")
public class DiagnoseController {
    
    private DiagnoseService diagnoseService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getDiagnoses(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<DiagnoseViewModel>>() {}.getType();
        final Page<DiagnoseViewModel> pageOfDiagnoses =
                modelMapper.map(diagnoseService.getDiagnosesPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfDiagnoses", pageOfDiagnoses);
        int totalPages = pageOfDiagnoses.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/diagnoses/diagnoses";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("diagnose", new DiagnoseViewModel());
        return "/diagnoses/create-diagnose.html";
    }
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("diagnose") CreateDiagnoseViewModel diagnose,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("diagnose", new CreateDiagnoseViewModel());
            return "/diagnoses/create-diagnose.html";
        }
        try{
            diagnoseService.create(modelMapper.map(diagnose, CreateDiagnoseDTO.class));}
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/diagnoses/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        try{
            diagnoseService.delete(id);
        }
        catch(Exception e){
            model.addAttribute("message", "Cannot delete diagnoses which are assigned to someone. Remove assignments first.");
            return "error-template";
        }
        return "redirect:/diagnoses/" + page + "/" + size;
    }

    @GetMapping("/{page}/{size}/edit/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        model.addAttribute("diagnose", modelMapper.map(diagnoseService.getDiagnose(id),
        CreateDiagnoseViewModel.class));
        return "/diagnoses/edit-diagnose";
    }

    @PostMapping("/{page}/{size}/update/{id}")
    public String update (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, 
    @Valid @ModelAttribute("diagnose") CreateDiagnoseViewModel diagnose, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/diagnoses/"+page+"/"+size+"/edit/"+id;
        }
        try{
            diagnoseService.update(id, modelMapper.map(diagnose,CreateDiagnoseDTO.class));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/diagnoses/"+page+"/"+size;
    }
    
    private DiagnoseViewModel convertToDiagnoseViewModel(DiagnoseDTO diagnose) {
        return modelMapper.map(diagnose, DiagnoseViewModel.class);
    }
}
