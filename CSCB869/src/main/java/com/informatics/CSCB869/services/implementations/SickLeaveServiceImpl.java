package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.SickLeave;
import com.informatics.CSCB869.data.repository.SickLeaveRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.SickLeaveService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SickLeaveServiceImpl implements SickLeaveService{
    private final SickLeaveRepository sickLeaveRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<SickLeaveDTO> getSickLeaves(){
        return sickLeaveRepository.findAll().stream()
                    .map(this::convertToSickLeaveDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<SickLeaveDTO> getSickLeavesPagination(Pageable pageable){
        List<SickLeaveDTO> sickleaveList = sickLeaveRepository.findAll()
                .stream()
                .map(this::convertToSickLeaveDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstSickLeaveNumber = page * size;

        List<SickLeaveDTO> sickleavePageList;

        if (sickleaveList.size() < firstSickLeaveNumber) {
            sickleavePageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstSickLeaveNumber + size, sickleaveList.size());
            sickleavePageList = sickleaveList.subList(firstSickLeaveNumber, toIndex);
        }

        Page<SickLeaveDTO> pageOfSickLeaves
                = new PageImpl<SickLeaveDTO>(sickleavePageList, PageRequest.of(page, size), sickleaveList.size());

        return pageOfSickLeaves;

    }

    @Override
    public SickLeaveDTO getSickLeave(long id){
        return modelMapper.map(sickLeaveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sickleave id:" + id)), SickLeaveDTO.class);
    }

    @Override
    public List<SickLeaveDTO> getSickLeavesByPatient(Patient patient){
        List<SickLeaveDTO> sickLeavesList = sickLeaveRepository.findAllByPatient(patient).stream()
                                                     .map(this::convertToSickLeaveDTO)
                                                     .collect(Collectors.toList());
         
        if(sickLeavesList.isEmpty()){
            throw new IllegalArgumentException("Invalid patient.");
        }
        
        return sickLeavesList;
    }

    @Override
    public SickLeave create(CreateSickLeaveDTO sickleave){
        return sickLeaveRepository.save(modelMapper.map(sickleave, SickLeave.class));
    }

    @Override
    public SickLeave update(long id, CreateSickLeaveDTO sickleave){
        SickLeave updatedSickLeave = modelMapper.map(sickleave, SickLeave.class);
        updatedSickLeave.setId(id);
        return sickLeaveRepository.save(updatedSickLeave);
    }

    @Override
    public void delete(long id){
        sickLeaveRepository.deleteById(id);
    }

    private SickLeaveDTO convertToSickLeaveDTO(SickLeave sickleave) {
        return modelMapper.map(sickleave, SickLeaveDTO.class);
    }
}
