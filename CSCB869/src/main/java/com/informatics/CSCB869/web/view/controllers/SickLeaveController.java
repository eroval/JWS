package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.SickLeave;
import com.informatics.CSCB869.data.repository.PatientRepository;
import com.informatics.CSCB869.data.repository.SickLeaveRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.PatientService;
import com.informatics.CSCB869.services.SickLeaveService;
import com.informatics.CSCB869.web.view.model.CreateSickLeaveViewModel;
import com.informatics.CSCB869.web.view.model.SickLeaveViewModel;

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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/sick-leave")
public class SickLeaveController {
    
    private final SickLeaveService sickLeaveService;
    private final PatientRepository patientRepository;
    private final PatientService patientService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getSickLeaves(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<SickLeaveViewModel>>() {}.getType();
        final Page<SickLeaveViewModel> pageOfSickLeaves =
                modelMapper.map(sickLeaveService.getSickLeavesPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfSickleaves", pageOfSickLeaves);
        int totalPages = pageOfSickLeaves.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/sickleaves/sickleaves";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("sickleave", new SickLeaveViewModel());
        model.addAttribute("patients", patientService.getPatients());
        return "/sickleaves/create-sickleave.html";
    }
    
    @GetMapping("/patient/{id}")
    public String getSickLeaveByPatientId(Model model, @PathVariable long id){
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty()){
            return "redirect:/patients/1/10";
        }
        try{
            final List<SickLeaveViewModel> sickLeaves = sickLeaveService.getSickLeavesByPatient(patient.get())
                    .stream()
                    .map(this::convertToSickLeaveViewModel)
                    .collect(Collectors.toList());
            model.addAttribute("sickleaves", sickLeaves);
            return "/sickleaves/sickleaves-list";
        }
        catch(Exception e){
            return "redirect:/patients/1/10";
        }
    }
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("sickleave") CreateSickLeaveViewModel sickleave,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || (sickleave.getEndDate().compareTo(sickleave.getStartDate())<0)) {
            model.addAttribute("sickleave", new SickLeaveViewModel());
            model.addAttribute("patients", patientService.getPatients());
            return "/sickleaves/create-sickleave.html";
        }
        sickLeaveService.create(modelMapper.map(sickleave, CreateSickLeaveDTO.class));
        return "redirect:/sick-leave/1/10";
    }

    @GetMapping("/{page}/{size}/edit/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        model.addAttribute("sickleave", modelMapper.map(sickLeaveService.getSickLeave(id),
        CreateSickLeaveViewModel.class));
        model.addAttribute("patients", patientService.getPatients());
        return "/sickleaves/edit-sickleave";
    }
    
    @PostMapping("/{page}/{size}/update/{id}")
    public String update (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, 
    @Valid @ModelAttribute("sickleave") CreateSickLeaveViewModel sickleave, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || (sickleave.getEndDate().compareTo(sickleave.getStartDate())<0)) {
            return "redirect:/sick-leave/"+page+"/"+size+"/edit/"+id;
        }
        try{
            sickLeaveService.update(id, modelMapper.map(sickleave,CreateSickLeaveDTO.class));
        }
        catch(Exception e){
            model.addAttribute("message", "Couldn't update sick leave.");
            return "error-template";
        }
        return "redirect:/sick-leave/"+page+"/"+size;
    }

    @GetMapping("/edit/{id}")
    public String editList (@PathVariable Long id, Model model) {
        model.addAttribute("sickleave", modelMapper.map(sickLeaveService.getSickLeave(id),
        CreateSickLeaveViewModel.class));
        model.addAttribute("patients", patientService.getPatients());
        return "/sickleaves/edit-sickleave-list";
    }

    @PostMapping("/update/{id}")
    public String updateList (@PathVariable Long id, 
    @Valid @ModelAttribute("sickleave") CreateSickLeaveViewModel sickleave, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || (sickleave.getEndDate().compareTo(sickleave.getStartDate())<0)) {
            return "redirect:/sick-leave/edit/"+id;
        }
        try{
            long patientId = sickleave.getPatient().getId();
            sickLeaveService.update(id, modelMapper.map(sickleave,CreateSickLeaveDTO.class));
            return "redirect:/sick-leave/patient/"+patientId;
        }
        catch(Exception e){
            model.addAttribute("message", "Couldn't update sick leave.");
            return "error-template";
        }
    }

    @GetMapping("/{page}/{size}/delete/{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        try{
            sickLeaveService.delete(id);
        }
        catch(Exception e){
            model.addAttribute("message", "Couldn't delete sick leave.");
            return "error-template";
        }
        return "redirect:/sick-leave/" + page + "/" + size;
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id, Model model) {
        try{
            long patientId=sickLeaveService.getSickLeave(id).getPatient().getId();
            sickLeaveService.delete(id);
            return "redirect:/sick-leave/patient/"+patientId;
        }
        catch(Exception e){
            model.addAttribute("message", "Couldn't delete sick leave.");
            return "error-template";
        }
    }

    private SickLeaveViewModel convertToSickLeaveViewModel(SickLeaveDTO sickleave) {
        return modelMapper.map(sickleave, SickLeaveViewModel.class);
    }
}
