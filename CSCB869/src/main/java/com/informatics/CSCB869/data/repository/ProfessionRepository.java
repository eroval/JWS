package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfessionRepository extends JpaRepository<Profession, Long>{
    Optional<Profession> findByName(String name);
}
