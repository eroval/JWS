package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.repository.PatientRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.PatientService;
import com.informatics.CSCB869.web.view.model.CreatePatientViewModel;
import com.informatics.CSCB869.web.view.model.PatientViewModel;

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
@RequestMapping("/patients")
public class PatientController {
    
    private PatientService patientService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getPatients(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<PatientViewModel>>() {}.getType();
        final Page<PatientViewModel> pageOfPatients =
                modelMapper.map(patientService.getPatientsPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfPatients", pageOfPatients);
        int totalPages = pageOfPatients.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/patients/patients";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("patient", new PatientViewModel());
        return "/patients/create-patient.html";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CreatePatientDTO patient) {
        try{
            patientService.create(patient);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/patients/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id) {
        patientService.delete(id);
        return "redirect:/patients/" + page + "/" + size;
    }

    @GetMapping("/{page}/{size}/edit/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        model.addAttribute("patient", modelMapper.map(patientService.getPatient(id),
        CreatePatientViewModel.class));
        return "/patients/edit-patient";
    }

    @PostMapping("/{page}/{size}/update/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, 
    @Valid @ModelAttribute("patient") CreatePatientViewModel patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/patients/"+page+"/"+size+"/update/"+id;
        }
        try{
            patientService.update(id, modelMapper.map(patient,CreatePatientDTO.class));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/patients/"+page+"/"+size;
    }
    
    private PatientViewModel convertToPatientViewModel(PatientDTO patient) {
        return modelMapper.map(patient, PatientViewModel.class);
    }
}
