package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Insurance;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.dto.InsuranceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface InsuranceService {
    List<InsuranceDTO> getInsurances();
    Page<InsuranceDTO> getInsurancesPagination(Pageable pageable);
    List<InsuranceDTO> getInsurancesByPatient(Patient patient);
    List<InsuranceDTO> getInsurancesByDate(LocalDate date);
    InsuranceDTO getInsurance(LocalDate date, long patient_id);

    Insurance create(InsuranceDTO insurance);
    void delete(LocalDate date, long patient_id);
}
