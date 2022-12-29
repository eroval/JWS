package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.Visit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long>{
    List<Visit> findByDate(LocalDate date);
    List<Visit> findByDoctor(Doctor doctor);
    List<Visit> findByPatient(Patient patient);
}
