package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.PatientDiagnose;
import com.informatics.CSCB869.dto.PatientDiagnoseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientDiagnoseService {
    List<PatientDiagnoseDTO> getPatientDiagnose();
    List<PatientDiagnoseDTO> getPatientDiagnose(Diagnose diagnose);
    List<PatientDiagnoseDTO> getPatientDiagnose(Patient patient);
    Page<PatientDiagnoseDTO> getPatientDiagnosePagination(Pageable pageable);
    PatientDiagnoseDTO getPatientDiagnoseDTO(long patientId, long diagnoseId);

    PatientDiagnose create(PatientDiagnoseDTO patientdiagnoseDTO);
    void delete(long patientId, long diagnoseId);
}
