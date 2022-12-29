package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.Visit;
import com.informatics.CSCB869.dto.CreateVisitDTO;
import com.informatics.CSCB869.dto.VisitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface VisitService {
    List<VisitDTO> getVisits();
    Page<VisitDTO> getVisitsPagination(Pageable pageable);
    List<VisitDTO> getVisits(LocalDate date);
    List<VisitDTO> getVisits(Doctor doctor);
    List<VisitDTO> getVisits(Patient patient);
    VisitDTO getVisit(long id);

    Visit create(CreateVisitDTO visit);
    void delete(long id);
}
