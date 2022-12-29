package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.PatientDiagnose;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientDiagnoseRepository extends JpaRepository<PatientDiagnose, Serializable>{
    List<PatientDiagnose> findByPatient(Patient patient);
    List<PatientDiagnose> findByDiagnose(Diagnose diagnose);
    // Optional<PatientDiagnose> findByPatientDiagnose(Patient patient, Diagnose diagnose);
}
