package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.Visit;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.repository.DoctorRepository;
import com.informatics.CSCB869.data.repository.VisitRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DoctorService;
import com.informatics.CSCB869.services.PatientService;
import com.informatics.CSCB869.services.VisitService;
import com.informatics.CSCB869.web.view.model.CreateVisitViewModel;
import com.informatics.CSCB869.web.view.model.VisitViewModel;

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
@RequestMapping("/visits")
public class VisitController {
    
    private VisitService visitService;
    private DoctorService doctorService;
    private DoctorRepository doctorRepository;
    private PatientService patientService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getVisits(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<VisitViewModel>>() {}.getType();
        final Page<VisitViewModel> pageOfVisits =
                modelMapper.map(visitService.getVisitsPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfVisits", pageOfVisits);
        int totalPages = pageOfVisits.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/visits/visits";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("visit", new CreateVisitViewModel());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("doctors", doctorService.getDoctors());
        return "/visits/create-visit.html";
    }

    @GetMapping("/doctor/{id}")
    public String getByDiagnoseId(Model model, @PathVariable long id){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if(doctor.isEmpty()){
            return "redirect:/doctors/1/10";
        }
        try{
            final List<VisitViewModel> visits = visitService.getVisits(doctor.get())
                    .stream()
                    .map(this::convertToVisitViewModel)
                    .collect(Collectors.toList());
            model.addAttribute("visits", visits);
            return "/visits/visits-list";
        }
        catch(Exception e){
            return "redirect:/doctors/1/10";
        }
    }
    
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("visit") CreateVisitViewModel visit,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("visit", new CreateVisitViewModel());
            model.addAttribute("patients", patientService.getPatients());
            model.addAttribute("doctors", doctorService.getDoctors());
            return "/visits/create-visit.html";
        }
        try{
            visitService.create(modelMapper.map(visit, CreateVisitDTO.class));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/visits/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        try{
            visitService.delete(id);
        }
        catch(Exception e){
            model.addAttribute("message", "Couldn't delete visit.");
            return "error-template";
        }
        return "redirect:/visits/" + page + "/" + size;
    }
    
    private VisitViewModel convertToVisitViewModel(VisitDTO visit) {
        return modelMapper.map(visit, VisitViewModel.class);
    }
}
