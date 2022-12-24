package com.informatics.CSCB869.web.api;

import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.dto.CreateProfessionDTO;
import com.informatics.CSCB869.dto.ProfessionDTO;
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
    public List<ProfessionDTO> getProfessions(){
        return professionService.getProfessions();
    }

    @RequestMapping("/professions/{id}")
    public ProfessionDTO getProfession(@PathVariable("id") long id){
        return professionService.getProfession(id);
    }

    @RequestMapping("/professions/{name}")
    public ProfessionDTO getProfession(@PathVariable("name") String name){
        return professionService.getProfession(name);
    }

    @PostMapping
    public Profession create(@RequestBody CreateProfessionDTO profession){
        return professionService.create(profession);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Profession update(@PathVariable("id") long id, @RequestBody CreateProfessionDTO profession) {
        return professionService.update(id, profession);
    }
    
    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable("id") long id){
        professionService.delete(id);
    }
}
