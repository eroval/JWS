package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.repository.DiagnoseRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DiagnoseService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiagnoseServiceImpl implements DiagnoseService{
    private final DiagnoseRepository diagnoseRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<DiagnoseDTO> getDiagnoses(){
        return diagnoseRepository.findAll().stream()
                    .map(this::convertToDiagnoseDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<DiagnoseDTO> getDiagnosesPagination(Pageable pageable){
        List<DiagnoseDTO> diagnoseList = diagnoseRepository.findAll()
                .stream()
                .map(this::convertToDiagnoseDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstDiagnoseNumber = page * size;

        List<DiagnoseDTO> diagnosePageList;

        if (diagnoseList.size() < firstDiagnoseNumber) {
            diagnosePageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstDiagnoseNumber + size, diagnoseList.size());
            diagnosePageList = diagnoseList.subList(firstDiagnoseNumber, toIndex);
        }

        Page<DiagnoseDTO> pageOfDiagnoses
                = new PageImpl<DiagnoseDTO>(diagnosePageList, PageRequest.of(page, size), diagnoseList.size());

        return pageOfDiagnoses;

    }

    @Override
    public DiagnoseDTO getDiagnose(long id){
        return modelMapper.map(diagnoseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid diagnose id:" + id)), DiagnoseDTO.class);
    }

    @Override
    public DiagnoseDTO getDiagnose(String name){
        return modelMapper.map(diagnoseRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Invalid diagnose name:" + name)), DiagnoseDTO.class);
    }

    @Override
    public Diagnose create(CreateDiagnoseDTO diagnose){
        return diagnoseRepository.save(modelMapper.map(diagnose, Diagnose.class));
    }

    @Override
    public Diagnose update(long id, CreateDiagnoseDTO diagnose){
        Diagnose updatedDiagnose = modelMapper.map(diagnose, Diagnose.class);
        updatedDiagnose.setId(id);
        return diagnoseRepository.save(updatedDiagnose);
    }

    @Override
    public void delete(long id){
        diagnoseRepository.deleteById(id);
    }

    private DiagnoseDTO convertToDiagnoseDTO(Diagnose diagnose) {
        return modelMapper.map(diagnose, DiagnoseDTO.class);
    }
}
