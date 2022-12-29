package com.informatics.CSCB869.services.implementations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.informatics.CSCB869.data.entity.Insurance;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.idclasses.InsuranceId;
import com.informatics.CSCB869.data.repository.InsuranceRepository;
import com.informatics.CSCB869.dto.InsuranceDTO;
import com.informatics.CSCB869.services.InsuranceService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InsuranceServiceImpl implements InsuranceService{
    private final InsuranceRepository insuranceRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<InsuranceDTO> getInsurances(){
        return insuranceRepository.findAll().stream()
                    .map(this::convertToInsuranceDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<InsuranceDTO> getInsurancesPagination(Pageable pageable){
        List<InsuranceDTO> insuranceList = insuranceRepository.findAll()
                .stream()
                .map(this::convertToInsuranceDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstInsuranceNumber = page * size;

        List<InsuranceDTO> insurancePageList;

        if (insuranceList.size() < firstInsuranceNumber) {
            insurancePageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstInsuranceNumber + size, insuranceList.size());
            insurancePageList = insuranceList.subList(firstInsuranceNumber, toIndex);
        }

        Page<InsuranceDTO> pageOfInsurances
                = new PageImpl<InsuranceDTO>(insurancePageList, PageRequest.of(page, size), insuranceList.size());

        return pageOfInsurances;

    }
    @Override
    public List<InsuranceDTO> getInsurancesByPatient(Patient patient){
        List<InsuranceDTO> insurancesList = insuranceRepository.findAllByPatient(patient).stream()
                                                     .map(this::convertToInsuranceDTO)
                                                     .collect(Collectors.toList());
         
        if(insurancesList.isEmpty()){
            throw new IllegalArgumentException("Invalid patient.");
        }
        
        return insurancesList;
    }

    @Override
    public List<InsuranceDTO> getInsurancesByDate(LocalDate date){
        List<InsuranceDTO> insurancesList = insuranceRepository.findAllByDate(date).stream()
                                                     .map(this::convertToInsuranceDTO)
                                                     .collect(Collectors.toList());
         
        if(insurancesList.isEmpty()){
            throw new IllegalArgumentException("Invalid date.");
        }
        
        return insurancesList;
    }

    @Override
    public InsuranceDTO getInsurance(LocalDate date, long patient_id){
        return modelMapper.map(insuranceRepository.findById(new InsuranceId(date, patient_id))
        .orElseThrow(() -> new IllegalArgumentException("Invalid composite key: " + date.toString() + patient_id)),
        InsuranceDTO.class);
    }


    @Override
    public Insurance create(InsuranceDTO insurance){
        return insuranceRepository.save(modelMapper.map(insurance, Insurance.class));
    }

    @Override
    public void delete(LocalDate date, long patient_id){
        Insurance insurance = modelMapper.map(insuranceRepository.findById(new InsuranceId(date, patient_id))
        .orElseThrow(() -> new IllegalArgumentException("Invalid composite key: " + date.toString() + patient_id)),
        Insurance.class);
        insuranceRepository.delete(insurance);
    }

    private InsuranceDTO convertToInsuranceDTO(Insurance insurance) {
        return modelMapper.map(insurance, InsuranceDTO.class);
    }

}
