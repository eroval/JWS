package com.informatics.CSCB869.web.view.controllers;

import com.informatics.CSCB869.data.entity.DoctorPatientGP;
import com.informatics.CSCB869.data.repository.DoctorPatientGPRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DoctorPatientGPService;
import com.informatics.CSCB869.services.DoctorService;
import com.informatics.CSCB869.services.PatientService;
import com.informatics.CSCB869.services.ProfessionService;
import com.informatics.CSCB869.web.view.model.DoctorPatientGPViewModel;
import com.informatics.CSCB869.web.view.model.UpdateDoctorPatientGPViewModel;

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
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/doctorpatientgps")
public class DoctorPatientGPController {
    
    private DoctorPatientGPService doctorpatientgpService;
    private DoctorService doctorService;
    private PatientService patientService;
    private final ModelMapper modelMapper;

    @GetMapping ("/{page}/{size}")
    public String getDoctorPatientGPs(Model model, @PathVariable int page, @PathVariable int size){
        Type pageType = new TypeToken<Page<DoctorPatientGPViewModel>>() {}.getType();
        final Page<DoctorPatientGPViewModel> pageOfDoctorPatientGPs =
                modelMapper.map(doctorpatientgpService.getDoctorPatientGPsPagination(PageRequest.of(page - 1, size)), pageType);

        model.addAttribute("pageOfDoctorpatientgps", pageOfDoctorPatientGPs);
        int totalPages = pageOfDoctorPatientGPs.getTotalPages();
        if (totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", page);
        return "/doctorpatientgps/doctorpatientgps";
    }


    @GetMapping("/create-form")
    public String createForm(Model model){
        model.addAttribute("doctorpatientgp", new DoctorPatientGPViewModel());
        model.addAttribute("patients", patientService.getPatients());
        model.addAttribute("doctors", doctorService.getDoctors());
        return "/doctorpatientgps/create-doctorpatientgp.html";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("doctorpatientgp") DoctorPatientGPViewModel doctorpatientgp,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("doctorpatientgp", new DoctorPatientGPViewModel());
            model.addAttribute("patients", patientService.getPatients());
            model.addAttribute("doctors", doctorService.getDoctors());
            return "/doctorpatientgps/create-doctorpatientgp.html";
        }
        doctorpatientgpService.create(modelMapper.map(doctorpatientgp, DoctorPatientGPDTO.class));
        return "redirect:/doctorpatientgps/1/10";
    }

    @GetMapping("/{page}/{size}/delete/{id}")
    public String delete (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        try{
            doctorpatientgpService.delete(id);
        }
        catch(Exception e){
            model.addAttribute("message", "Couldn't delete GP.");
            return "error-template";
        }
        return "redirect:/doctorpatientgps/" + page + "/" + size;
    }

    @GetMapping("/{page}/{size}/edit/{id}")
    public String edit (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id, Model model) {
        model.addAttribute("doctorpatientgp", modelMapper.map(doctorpatientgpService.getDoctorPatientGPDTO(id),
        UpdateDoctorPatientGPViewModel.class));
        model.addAttribute("id", id);
        model.addAttribute("doctors", doctorService.getDoctors());
        return "/doctorpatientgps/edit-doctorpatientgp";
    }

    @PostMapping("/{page}/{size}/update/{id}")
    public String update (@PathVariable Long page, @PathVariable Long size, @PathVariable Long id,
    @Valid @ModelAttribute("doctorpatientgp") UpdateDoctorPatientGPViewModel doctorpatientgp, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/doctorpatientgps/"+page+"/"+size+"/edit/"+id;
        }
        try{
            doctorpatientgpService.update(id, modelMapper.map(doctorpatientgp,UpdateDoctorPatientGPDTO.class));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "redirect:/doctorpatientgps/"+page+"/"+size;
    }
    
    private DoctorPatientGPViewModel convertToDoctorPatientGPViewModel(DoctorPatientGPDTO doctorpatientgp) {
        return modelMapper.map(doctorpatientgp, DoctorPatientGPViewModel.class);
    }
}
