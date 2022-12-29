package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.Insurance;
import com.informatics.CSCB869.data.repository.PatientRepository;
import com.informatics.CSCB869.data.repository.InsuranceRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.PatientService;
import com.informatics.CSCB869.services.InsuranceService;
import com.informatics.CSCB869.web.view.model.InsuranceViewModel;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.modelmapper.TypeToken;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/insurance")
public class InsuranceController {
    
    private final InsuranceService insuranceService;
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getInsurances(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<InsuranceViewModel>>() {}.getType();
        final Page<InsuranceViewModel> pageOfInsurances =
                modelMapper.map(insuranceService.getInsurancesPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfInsurances", pageOfInsurances);
        int totalPages = pageOfInsurances.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/insurances/insurances";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("insurance", new InsuranceViewModel());
        model.addAttribute("patients", patientService.getPatients());
        return "/insurances/create-insurance.html";
    }
    
    @GetMapping("/patient/{id}")
    public String getInsuranceByPatientId(Model model, @PathVariable long id){
        Optional<Patient> patient = patientRepository.findById(id);
        if(patient.isEmpty()){
            return "redirect:/patients/1/10";
        }
        try{
            final List<InsuranceViewModel> insurances = insuranceService.getInsurancesByPatient(patient.get())
                    .stream()
                    .map(this::convertToInsuranceViewModel)
                    .collect(Collectors.toList());
            model.addAttribute("insurances", insurances);
            return "/insurances/insurances-list";
        }
        catch(Exception e){
            return "redirect:/patients/1/10";
        }
    }
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("insurance") InsuranceViewModel insurance,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("insurance", new InsuranceViewModel());
            model.addAttribute("patients", patientService.getPatients());
            return "/insurances/create-insurance.html";
        }
        insurance.setDate(insurance.getDate().withDayOfMonth(1));
        insuranceService.create(modelMapper.map(insurance, InsuranceDTO.class));
        return "redirect:/insurance/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{date}-{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable String date, @PathVariable Long id, Model model) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = LocalDate.parse(date, formatter);
            insuranceService.delete(newDate, id);
        }
        catch(Exception e){
            model.addAttribute("message", "Couldn't delete insurance.");
            return "error-template";
        }
        return "redirect:/insurance/" + page + "/" + size;
    }
    
    private InsuranceViewModel convertToInsuranceViewModel(InsuranceDTO insurance) {
        return modelMapper.map(insurance, InsuranceViewModel.class);
    }
}
