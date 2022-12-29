package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.repository.PatientRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.PatientService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService{
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<PatientDTO> getPatients(){
        return patientRepository.findAll().stream()
                    .map(this::convertToPatientDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<PatientDTO> getPatientsPagination(Pageable pageable){
        List<PatientDTO> patientList = patientRepository.findAll()
                .stream()
                .map(this::convertToPatientDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstPatientNumber = page * size;

        List<PatientDTO> patientPageList;

        if (patientList.size() < firstPatientNumber) {
            patientPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstPatientNumber + size, patientList.size());
            patientPageList = patientList.subList(firstPatientNumber, toIndex);
        }

        Page<PatientDTO> pageOfPatients
                = new PageImpl<PatientDTO>(patientPageList, PageRequest.of(page, size), patientList.size());

        return pageOfPatients;

    }

    @Override
    public PatientDTO getPatient(long id){
        return modelMapper.map(patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient id:" + id)), PatientDTO.class);
    }

    @Override
    public PatientDTO getPatient(String name){
        return modelMapper.map(patientRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient name:" + name)), PatientDTO.class);
    }

    @Override
    public PatientDTO getPatientByEgn(String egn){
        return modelMapper.map(patientRepository.findByEgn(egn)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient name:" + egn)), PatientDTO.class);
    }

    @Override
    public Patient create(CreatePatientDTO patient){
        return patientRepository.save(modelMapper.map(patient, Patient.class));
    }

    @Override
    public Patient update(long id, CreatePatientDTO patient){
        Patient updatedPatient = modelMapper.map(patient, Patient.class);
        updatedPatient.setId(id);
        return patientRepository.save(updatedPatient);
    }

    @Override
    public void delete(long id){
        patientRepository.deleteById(id);
    }

    private PatientDTO convertToPatientDTO(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }
}
