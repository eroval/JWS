package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long>{
    Optional<Patient> findByEgn(String egn);
    Optional<Patient> findByName(String name);
}
