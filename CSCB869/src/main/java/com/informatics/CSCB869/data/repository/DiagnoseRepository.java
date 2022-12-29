package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Diagnose;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DiagnoseRepository extends JpaRepository<Diagnose, Long>{
    Optional<Diagnose> findByName(String name);
}
