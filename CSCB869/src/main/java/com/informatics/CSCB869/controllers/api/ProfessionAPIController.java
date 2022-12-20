package com.informatics.CSCB869.controllers.api;

import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.services.ProfessionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/profession")
@AllArgsConstructor
public class ProfessionAPIController {

    private final ProfessionService professionService;

    @GetMapping
    public List<Profession> getProfessions(){
        return professionService.getProfessions();
    }

    @RequestMapping("/professions/{id}")
    public Profession getProfession(@PathVariable("id") long id){
        return professionService.getProfession(id);
    }

    @RequestMapping("/professions/{name}")
    public Profession getProfession(@PathVariable("name") String name){
        return professionService.getProfession(name);
    }

    @PostMapping
    public Profession createProfession(@RequestBody Profession profession){
        return professionService.createProfession(profession);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Profession updateProfession(@PathVariable("id") long id, @RequestBody Profession profession) {
        return professionService.updateProfession(id, profession);
    }
    
    @DeleteMapping(value="/{id}")
    public void deleteProfession(@PathVariable("id") long id){
        professionService.deleteProfession(id);
    }
}
