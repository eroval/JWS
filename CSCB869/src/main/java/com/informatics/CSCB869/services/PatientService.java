package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.dto.CreatePatientDTO;
import com.informatics.CSCB869.dto.PatientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    List<PatientDTO> getPatients();
    Page<PatientDTO> getPatientsPagination(Pageable pageable);
    PatientDTO getPatient(long id);
    PatientDTO getPatient(String name);
    PatientDTO getPatientByEgn(String egn);

    Patient create(CreatePatientDTO patient);
    Patient update(long id, CreatePatientDTO patient);
    void delete(long id);
}
