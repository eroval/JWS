package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Profession;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProfessionService {
    List<Profession> getProfessions();
    Profession getProfession(long id);
    Profession getProfession(String name);

    Profession createProfession(Profession profession);
    Profession updateProfession(long id, Profession profession);
    void saveProfession(Profession profession);
    void deleteProfession(long id);
}
