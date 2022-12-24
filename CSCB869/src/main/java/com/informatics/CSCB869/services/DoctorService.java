package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.dto.CreateDoctorDTO;
import com.informatics.CSCB869.dto.DoctorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorService {
    List<DoctorDTO> getDoctors();
    Page<DoctorDTO> getDoctorsPagination(Pageable pageable);
    DoctorDTO getDoctor(long id);
    DoctorDTO getDoctor(String name);

    Doctor create(CreateDoctorDTO doctor);
    Doctor update(long id, CreateDoctorDTO doctor);
    void delete(long id);
}
