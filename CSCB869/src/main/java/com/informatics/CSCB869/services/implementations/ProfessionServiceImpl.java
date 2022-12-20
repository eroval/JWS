package com.informatics.CSCB869.services.implementations;
import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.data.repository.ProfessionRepository;
import com.informatics.CSCB869.services.ProfessionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProfessionServiceImpl implements ProfessionService{
    private final ProfessionRepository professionRepository;

    @Override 
    public List<Profession> getProfessions(){
        return professionRepository.findAll();
    }

    @Override
    public Profession getProfession(long id){
        return professionRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid profession Id:" + id));
    }

    @Override
    public Profession getProfession(String name){
        return professionRepository.findByName(name);
    }

    @Override
    public Profession createProfession(Profession profession){
        return professionRepository.save(profession);
    }

    @Override
    public Profession updateProfession(long id, Profession profession){
        profession.setId(id);
        return professionRepository.save(profession);
    }

    @Override
    public void saveProfession(Profession profession){
        professionRepository.save(profession);
    }

    @Override
    public void deleteProfession(long id){
        professionRepository.deleteById(id);
    }
}
