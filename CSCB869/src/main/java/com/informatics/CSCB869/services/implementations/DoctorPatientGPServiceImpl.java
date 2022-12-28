package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.DoctorPatientGP;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.repository.DoctorPatientGPRepository;
import com.informatics.CSCB869.data.repository.DoctorRepository;
import com.informatics.CSCB869.data.repository.PatientRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DoctorPatientGPService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorPatientGPServiceImpl implements DoctorPatientGPService{
    private final DoctorPatientGPRepository doctorpatientgpRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<DoctorPatientGPDTO> getDoctorPatientGPs(){
        return doctorpatientgpRepository.findAll().stream()
                    .map(this::convertToDoctorPatientGPDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<DoctorPatientGPDTO> getDoctorPatientGPsPagination(Pageable pageable){
        List<DoctorPatientGPDTO> doctorpatientgpList = doctorpatientgpRepository.findAll()
                .stream()
                .map(this::convertToDoctorPatientGPDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstDoctorPatientGPNumber = page * size;

        List<DoctorPatientGPDTO> doctorpatientgpPageList;

        if (doctorpatientgpList.size() < firstDoctorPatientGPNumber) {
            doctorpatientgpPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstDoctorPatientGPNumber + size, doctorpatientgpList.size());
            doctorpatientgpPageList = doctorpatientgpList.subList(firstDoctorPatientGPNumber, toIndex);
        }

        Page<DoctorPatientGPDTO> pageOfDoctorPatientGPs
                = new PageImpl<DoctorPatientGPDTO>(doctorpatientgpPageList, PageRequest.of(page, size), doctorpatientgpList.size());

        return pageOfDoctorPatientGPs;

    }

    @Override
    public List<DoctorPatientGPDTO> getDoctorPatientGPDTO(Doctor doctor){
        List<DoctorPatientGPDTO> doctorPatientList = doctorpatientgpRepository.findByDoctor(doctor).stream()
        .map(this::convertToDoctorPatientGPDTO)
        .collect(Collectors.toList());

        if(doctorPatientList.isEmpty()){
        throw new IllegalArgumentException("Invalid patient.");
        }
        return doctorPatientList;
    }

    @Override
    public DoctorPatientGPDTO getDoctorPatientGPDTO(Patient patient){
        return modelMapper.map(doctorpatientgpRepository.findByPatient(patient)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient id: " + patient.getId())), DoctorPatientGPDTO.class);
    }

    @Override
    public DoctorPatientGPDTO getDoctorPatientGPDTO(long patientId){
        Optional<Patient> patient = patientRepository.findById(patientId);
        return getDoctorPatientGPDTO(patient.get());
    }

    @Override
    public DoctorPatientGP create(DoctorPatientGPDTO doctorPatientGPDTO){
        // DoctorPatientGP newRecord = modelMapper.map(doctorpatientgp, DoctorPatientGP.class);
        if(doctorpatientgpRepository.findByPatient(doctorPatientGPDTO.getPatient()).isEmpty()){
            return doctorpatientgpRepository.save(modelMapper.map(doctorPatientGPDTO, DoctorPatientGP.class));
        }
        return null;
    }

    @Override
    public DoctorPatientGP update(long patientId, UpdateDoctorPatientGPDTO doctorPatientGPDTO){
        Optional<Patient> patient = patientRepository.findById(patientId);
        if(patient.isEmpty()){
            throw new IllegalArgumentException("Invalid patient id: "+ patient.get().getId());
        }
        Optional<DoctorPatientGP> doctorPatientGp = doctorpatientgpRepository.findByPatient(patient.get());
        if (doctorPatientGp.isEmpty()){
            throw new IllegalArgumentException("No such sick leave id");
        }
        doctorpatientgpRepository.delete(doctorPatientGp.get());
        DoctorPatientGPDTO updatedDoctorPatientGP = new DoctorPatientGPDTO(doctorPatientGPDTO.getDoctor(), patient.get());
        return doctorpatientgpRepository.save(modelMapper.map(updatedDoctorPatientGP, DoctorPatientGP.class));
    }

    @Override
    public void delete(long patientId){
        Optional<Patient> patient = patientRepository.findById(patientId);
        if(patient.isEmpty()){
            throw new IllegalArgumentException("Invalid patient id: "+ patient.get().getId());
        }
        Optional<DoctorPatientGP> doctorPatientGp = doctorpatientgpRepository.findByPatient(patient.get());
        if (doctorPatientGp.isEmpty()){
            throw new IllegalArgumentException("No such sick leave id");
        }
        doctorpatientgpRepository.delete(doctorPatientGp.get());
    }

    private DoctorPatientGPDTO convertToDoctorPatientGPDTO(DoctorPatientGP doctorpatientgp) {

        return modelMapper.map(doctorpatientgp, DoctorPatientGPDTO.class);
    }
}
