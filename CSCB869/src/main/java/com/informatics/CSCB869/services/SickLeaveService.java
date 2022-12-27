package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.SickLeave;
import com.informatics.CSCB869.dto.CreateSickLeaveDTO;
import com.informatics.CSCB869.dto.SickLeaveDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SickLeaveService {
    List<SickLeaveDTO> getSickLeaves();
    Page<SickLeaveDTO> getSickLeavesPagination(Pageable pageable);
    SickLeaveDTO getSickLeave(long id);
    List<SickLeaveDTO> getSickLeavesByPatient(Patient patient);

    SickLeave create(CreateSickLeaveDTO sickLeave);
    SickLeave update(long id, CreateSickLeaveDTO sickLeave);
    void delete(long id);
}
