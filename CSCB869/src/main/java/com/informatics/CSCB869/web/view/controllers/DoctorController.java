package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.repository.DoctorRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DoctorService;
import com.informatics.CSCB869.services.ProfessionService;
import com.informatics.CSCB869.web.view.model.CreateDoctorViewModel;
import com.informatics.CSCB869.web.view.model.DoctorViewModel;

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
@RequestMapping("/doctors")
public class DoctorController {
    
    private DoctorService doctorService;
    private ProfessionService professionService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getDoctors(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<DoctorViewModel>>() {}.getType();
        final Page<DoctorViewModel> pageOfDoctors =
                modelMapper.map(doctorService.getDoctorsPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfDoctors", pageOfDoctors);
        int totalPages = pageOfDoctors.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/doctors/doctors";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("doctor", new CreateDoctorViewModel());
        model.addAttribute("professions", professionService.getProfessions());
        return "/doctors/create-doctor.html";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("doctor") CreateDoctorViewModel doctor,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctor", new CreateDoctorViewModel());
            model.addAttribute("professions", professionService.getProfessions());
            return "/doctors/create-doctor.html";
        }
        doctorService.create(modelMapper.map(doctor, CreateDoctorDTO.class));
        return "redirect:/doctors/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        try{
            doctorService.delete(id);
        }
        catch(Exception e){
            model.addAttribute("message", "Cannot delete doctor when assigned as GP. Delete clients first.");
            return "error-template";
        }
        return "redirect:/doctors/" + page + "/" + size;
    }

    @GetMapping("/{page}/{size}/edit/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        model.addAttribute("doctor", modelMapper.map(doctorService.getDoctor(id),
        CreateDoctorViewModel.class));
        model.addAttribute("professions", professionService.getProfessions());
        return "/doctors/edit-doctor";
    }

    @PostMapping("/{page}/{size}/update/{id}")
    public String update (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, 
    @Valid @ModelAttribute("doctor") CreateDoctorViewModel doctor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/doctors/"+page+"/"+size+"/edit/"+id;
        }
        try{
            doctorService.update(id, modelMapper.map(doctor,CreateDoctorDTO.class));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/doctors/"+page+"/"+size;
    }
    
    private DoctorViewModel convertToDoctorViewModel(DoctorDTO doctor) {
        return modelMapper.map(doctor, DoctorViewModel.class);
    }
}
