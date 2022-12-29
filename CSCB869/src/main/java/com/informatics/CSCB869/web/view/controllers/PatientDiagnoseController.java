package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DiagnoseService;
import com.informatics.CSCB869.services.PatientDiagnoseService;
import com.informatics.CSCB869.services.PatientService;
import com.informatics.CSCB869.web.view.model.PatientDiagnoseViewModel;

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
@RequestMapping("/patientdiagnoses")
public class PatientDiagnoseController {
    
    private PatientDiagnoseService patientDiagnoseService;
    private DiagnoseService diagnoseService;
    private PatientService patientService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getPatientDiagnose(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<PatientDiagnoseViewModel>>() {}.getType();
        final Page<PatientDiagnoseViewModel> pageOfPatientdiagnoses =
                modelMapper.map(patientDiagnoseService.getPatientDiagnosePagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfPatientdiagnoses", pageOfPatientdiagnoses);
        int totalPages = pageOfPatientdiagnoses.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/patientdiagnoses/patientdiagnoses";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("patientdiagnose", new PatientDiagnoseViewModel());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("diagnoses", diagnoseService.getDiagnoses());
        return "/patientdiagnoses/create-patientdiagnose.html";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("patientdiagnose") PatientDiagnoseViewModel patientDiagnose,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("patientdiagnose", new PatientDiagnoseViewModel());
            model.addAttribute("patients", patientService.getPatients());
            model.addAttribute("diagnoses", diagnoseService.getDiagnoses());
            return "/patientdiagnoses/create-patientdiagnose.html";
        }
        patientDiagnoseService.create(modelMapper.map(patientDiagnose, PatientDiagnoseDTO.class));
        return "redirect:/patientdiagnoses/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{pId}-{dId}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long pId, @PathVariable Long dId) {
        patientDiagnoseService.delete(pId, dId);
        return "redirect:/patientdiagnoses/" + page + "/" + size;
    }
    
    private PatientDiagnoseViewModel convertToPatientDiagnoseViewModel(PatientDiagnoseDTO patientDiagnose) {
        return modelMapper.map(patientDiagnose, PatientDiagnoseViewModel.class);
    }
}
