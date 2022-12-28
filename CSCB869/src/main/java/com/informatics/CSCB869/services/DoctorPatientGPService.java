package com.informatics.CSCB869.services;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.DoctorPatientGP;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.dto.DoctorPatientGPDTO;
import com.informatics.CSCB869.dto.UpdateDoctorPatientGPDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorPatientGPService {
    List<DoctorPatientGPDTO> getDoctorPatientGPs();
    Page<DoctorPatientGPDTO> getDoctorPatientGPsPagination(Pageable pageable);
    List<DoctorPatientGPDTO> getDoctorPatientGPDTO(Doctor doctor);
    DoctorPatientGPDTO getDoctorPatientGPDTO(Patient patient);
    DoctorPatientGPDTO getDoctorPatientGPDTO(long patientId);

    DoctorPatientGP create(DoctorPatientGPDTO doctorPatientGPDTO);
    DoctorPatientGP update(long patientId, UpdateDoctorPatientGPDTO object);
    void delete(long patientId);
}
