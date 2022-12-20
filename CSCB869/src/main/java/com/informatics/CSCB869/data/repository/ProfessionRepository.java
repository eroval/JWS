package com.informatics.CSCB869.data.repository;
import com.informatics.CSCB869.data.entity.Profession;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ProfessionRepository extends JpaRepository<Profession, Long>{
    Profession findByName(String name);
}
