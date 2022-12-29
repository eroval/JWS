package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.SickLeave;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SickLeaveRepository extends JpaRepository<SickLeave, Long>{
    List<SickLeave> findAllByPatient(Patient patient);
}
