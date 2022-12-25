package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{
    Optional<Doctor> findByName(String name);
}
