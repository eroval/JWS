package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.PatientDiagnose;
import com.informatics.CSCB869.data.idclasses.PatientDiagnoseId;
import com.informatics.CSCB869.data.repository.DiagnoseRepository;
import com.informatics.CSCB869.data.repository.PatientDiagnoseRepository;
import com.informatics.CSCB869.data.repository.PatientRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.PatientDiagnoseService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientDiagnoseServiceImpl implements PatientDiagnoseService{
    private final PatientRepository patientRepository;
    private final DiagnoseRepository diagnoseRepository;
    private final PatientDiagnoseRepository patientDiagnoseRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<PatientDiagnoseDTO> getPatientDiagnose(){
        return patientDiagnoseRepository.findAll().stream()
                    .map(this::convertToPatientDiagnoseDTO)
                    .collect(Collectors.toList());
    }

    @Override 
    public List<PatientDiagnoseDTO> getPatientDiagnose(Patient patient){
        return patientDiagnoseRepository.findByPatient(patient).stream()
                    .map(this::convertToPatientDiagnoseDTO)
                    .collect(Collectors.toList());
    }

    @Override 
    public List<PatientDiagnoseDTO> getPatientDiagnose(Diagnose diagnose){
        return patientDiagnoseRepository.findByDiagnose(diagnose).stream()
                    .map(this::convertToPatientDiagnoseDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<PatientDiagnoseDTO> getPatientDiagnosePagination(Pageable pageable){
        List<PatientDiagnoseDTO> PatientDiagnoseList = patientDiagnoseRepository.findAll()
                .stream()
                .map(this::convertToPatientDiagnoseDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstPatientDiagnoseNumber = page * size;

        List<PatientDiagnoseDTO> PatientDiagnosePageList;

        if (PatientDiagnoseList.size() < firstPatientDiagnoseNumber) {
            PatientDiagnosePageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstPatientDiagnoseNumber + size, PatientDiagnoseList.size());
            PatientDiagnosePageList = PatientDiagnoseList.subList(firstPatientDiagnoseNumber, toIndex);
        }

        Page<PatientDiagnoseDTO> pageOfPatientDiagnoses
                = new PageImpl<PatientDiagnoseDTO>(PatientDiagnosePageList, PageRequest.of(page, size), PatientDiagnoseList.size());

        return pageOfPatientDiagnoses;

    }


    
    @Override
    public PatientDiagnoseDTO getPatientDiagnoseDTO(long patientId, long diagnoseId){
        // Patient patient = modelMapper.map(patientRepository.findById(patientId)
        //         .orElseThrow(() -> new IllegalArgumentException("Invalid patient id: " + patientId)), Patient.class);
        // Diagnose diagnose = modelMapper.map(diagnoseRepository.findById(diagnoseId)
        //         .orElseThrow(() -> new IllegalArgumentException("Invalid diagnose id: " + diagnoseId)), Diagnose.class);

        return modelMapper.map(patientDiagnoseRepository.findById(new PatientDiagnoseId(patientId, diagnoseId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid composite key: " + patientId + diagnoseId)),
                PatientDiagnoseDTO.class);
    }

    @Override
    public PatientDiagnose create(PatientDiagnoseDTO patientDiagnoseDTO){
        // PatientDiagnoseGP newRecord = modelMapper.map(PatientDiagnosegp, PatientDiagnoseGP.class);
        if(patientDiagnoseRepository.findById(new PatientDiagnoseId(patientDiagnoseDTO.getPatient().getId(), patientDiagnoseDTO.getDiagnose().getId())).isEmpty()){
            return patientDiagnoseRepository.save(modelMapper.map(patientDiagnoseDTO, PatientDiagnose.class));
        }
        return null;
    }

    @Override
    public void delete(long patientId, long diagnoseId){
        // Patient patient = modelMapper.map(patientRepository.findById(patientId)
        // .orElseThrow(() -> new IllegalArgumentException("Invalid patient id: " + patientId)), Patient.class);
        // Diagnose diagnose = modelMapper.map(diagnoseRepository.findById(diagnoseId)
        // .orElseThrow(() -> new IllegalArgumentException("Invalid diagnose id: " + diagnoseId)), Diagnose.class);
        
        PatientDiagnose patientDiagnose = modelMapper.map(patientDiagnoseRepository.findById(new PatientDiagnoseId(patientId, diagnoseId))
        .orElseThrow(() -> new IllegalArgumentException("Invalid composite key: " + patientId + diagnoseId)),
        PatientDiagnose.class);
        patientDiagnoseRepository.delete(patientDiagnose);
    }

    private PatientDiagnoseDTO convertToPatientDiagnoseDTO(PatientDiagnose patientDiagnose) {
        return modelMapper.map(patientDiagnose, PatientDiagnoseDTO.class);
    }
}
