package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.Visit;
import com.informatics.CSCB869.data.repository.VisitRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.VisitService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisitServiceImpl implements VisitService{
    private final VisitRepository visitRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<VisitDTO> getVisits(){
        return visitRepository.findAll().stream()
                    .map(this::convertToVisitDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<VisitDTO> getVisitsPagination(Pageable pageable){
        List<VisitDTO> visitList = visitRepository.findAll()
                .stream()
                .map(this::convertToVisitDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstVisitNumber = page * size;

        List<VisitDTO> visitPageList;

        if (visitList.size() < firstVisitNumber) {
            visitPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstVisitNumber + size, visitList.size());
            visitPageList = visitList.subList(firstVisitNumber, toIndex);
        }

        Page<VisitDTO> pageOfVisits
                = new PageImpl<VisitDTO>(visitPageList, PageRequest.of(page, size), visitList.size());

        return pageOfVisits;

    }

    @Override
    public VisitDTO getVisit(long id){
        return modelMapper.map(visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid visit id:" + id)), VisitDTO.class);
    }

    @Override
    public List<VisitDTO> getVisits(Patient patient){
        List<VisitDTO> visits = visitRepository.findByPatient(patient).stream()
                        .map(this::convertToVisitDTO)
                        .collect(Collectors.toList()); 
        
        if(visits.isEmpty()){
            throw new IllegalArgumentException("No such associated patient.");
        }
        return visits;
    }

    @Override
    public List<VisitDTO> getVisits(Doctor doctor){
        List<VisitDTO> visits = visitRepository.findByDoctor(doctor).stream()
                        .map(this::convertToVisitDTO)
                        .collect(Collectors.toList()); 
        
        if(visits.isEmpty()){
            throw new IllegalArgumentException("No such associated doctor.");
        }
        return visits;
    }

    @Override
    public List<VisitDTO> getVisits(LocalDate date){
        List<VisitDTO> visits = visitRepository.findByDate(date).stream()
                        .map(this::convertToVisitDTO)
                        .collect(Collectors.toList()); 
        
        if(visits.isEmpty()){
            throw new IllegalArgumentException("No such associated date.");
        }
        return visits;
    }

    @Override
    public Visit create(CreateVisitDTO visit){
        return visitRepository.save(modelMapper.map(visit, Visit.class));
    }

    @Override
    public void delete(long id){
        visitRepository.deleteById(id);
    }

    private VisitDTO convertToVisitDTO(Visit visit) {
        return modelMapper.map(visit, VisitDTO.class);
    }
}
