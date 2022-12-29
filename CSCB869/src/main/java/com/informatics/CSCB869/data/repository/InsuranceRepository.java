package com.informatics.CSCB869.data.repository;

import com.informatics.CSCB869.data.entity.Insurance;
import com.informatics.CSCB869.data.entity.Patient;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Serializable>{
    List<Insurance> findAllByPatient(Patient patient);
    List<Insurance> findAllByDate(LocalDate date);
}
