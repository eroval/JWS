package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.dto.CreateDiagnoseDTO;
import com.informatics.CSCB869.dto.DiagnoseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DiagnoseService {
    List<DiagnoseDTO> getDiagnoses();
    Page<DiagnoseDTO> getDiagnosesPagination(Pageable pageable);
    DiagnoseDTO getDiagnose(long id);
    DiagnoseDTO getDiagnose(String name);

    Diagnose create(CreateDiagnoseDTO diagnose);
    Diagnose update(long id, CreateDiagnoseDTO diagnose);
    void delete(long id);
}
