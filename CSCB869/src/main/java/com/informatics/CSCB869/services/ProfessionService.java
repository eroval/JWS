package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.dto.CreateProfessionDTO;
import com.informatics.CSCB869.dto.ProfessionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfessionService {
    List<ProfessionDTO> getProfessions();
    Page<ProfessionDTO> getProfessionsPagination(Pageable pageable);
    ProfessionDTO getProfession(long id);
    ProfessionDTO getProfession(String name);

    Profession create(CreateProfessionDTO profession);
    Profession update(long id, CreateProfessionDTO profession);
    void delete(long id);
}
