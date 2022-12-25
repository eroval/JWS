package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.data.repository.ProfessionRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.ProfessionService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfessionServiceImpl implements ProfessionService{
    private final ProfessionRepository professionRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<ProfessionDTO> getProfessions(){
        return professionRepository.findAll().stream()
                    .map(this::convertToProfessionDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<ProfessionDTO> getProfessionsPagination(Pageable pageable){
        List<ProfessionDTO> professionList = professionRepository.findAll()
                .stream()
                .map(this::convertToProfessionDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstProfessionNumber = page * size;

        List<ProfessionDTO> professionPageList;

        if (professionList.size() < firstProfessionNumber) {
            professionPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstProfessionNumber + size, professionList.size());
            professionPageList = professionList.subList(firstProfessionNumber, toIndex);
        }

        Page<ProfessionDTO> pageOfProfessions
                = new PageImpl<ProfessionDTO>(professionPageList, PageRequest.of(page, size), professionList.size());

        return pageOfProfessions;

    }

    @Override
    public ProfessionDTO getProfession(long id){
        return modelMapper.map(professionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid profession id:" + id)), ProfessionDTO.class);
    }

    @Override
    public ProfessionDTO getProfession(String name){
        return modelMapper.map(professionRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Invalid profession name:" + name)), ProfessionDTO.class);
    }

    @Override
    public Profession create(CreateProfessionDTO profession){
        return professionRepository.save(modelMapper.map(profession, Profession.class));
    }

    @Override
    public Profession update(long id, CreateProfessionDTO profession){
        Profession updatedProfession = modelMapper.map(profession, Profession.class);
        updatedProfession.setId(id);
        return professionRepository.save(updatedProfession);
    }

    @Override
    public void delete(long id){
        professionRepository.deleteById(id);
    }

    private ProfessionDTO convertToProfessionDTO(Profession profession) {
        return modelMapper.map(profession, ProfessionDTO.class);
    }
}
