package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.DoctorPatientGP;
import com.informatics.CSCB869.data.entity.Patient;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorPatientGPRepository extends JpaRepository<DoctorPatientGP, Long>{
    List<DoctorPatientGP> findByDoctor(Doctor doctor);
    Optional<DoctorPatientGP> findByPatient(Patient patient);
}
