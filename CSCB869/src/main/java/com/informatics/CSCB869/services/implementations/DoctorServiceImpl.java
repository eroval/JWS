package com.informatics.CSCB869.services.implementations;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.repository.DoctorRepository;
import com.informatics.CSCB869.dto.*;
import com.informatics.CSCB869.services.DoctorService;
import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService{
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Override 
    public List<DoctorDTO> getDoctors(){
        return doctorRepository.findAll().stream()
                    .map(this::convertToDoctorDTO)
                    .collect(Collectors.toList());
    }

    @Override
    public Page<DoctorDTO> getDoctorsPagination(Pageable pageable){
        List<DoctorDTO> doctorList = doctorRepository.findAll()
                .stream()
                .map(this::convertToDoctorDTO)
                .collect(Collectors.toList());
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int firstDoctorNumber = page * size;

        List<DoctorDTO> doctorPageList;

        if (doctorList.size() < firstDoctorNumber) {
            doctorPageList = Collections.emptyList();
        } else {
            int toIndex = Math.min(firstDoctorNumber + size, doctorList.size());
            doctorPageList = doctorList.subList(firstDoctorNumber, toIndex);
        }

        Page<DoctorDTO> pageOfDoctors
                = new PageImpl<DoctorDTO>(doctorPageList, PageRequest.of(page, size), doctorList.size());

        return pageOfDoctors;

    }

    @Override
    public DoctorDTO getDoctor(long id){
        return modelMapper.map(doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor id:" + id)), DoctorDTO.class);
    }

    @Override
    public DoctorDTO getDoctor(String name){
        return modelMapper.map(doctorRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor name:" + name)), DoctorDTO.class);
    }

    @Override
    public Doctor create(CreateDoctorDTO doctor){
        return doctorRepository.save(modelMapper.map(doctor, Doctor.class));
    }

    @Override
    public Doctor update(long id, CreateDoctorDTO doctor){
        Doctor updatedDoctor = modelMapper.map(doctor, Doctor.class);
        updatedDoctor.setId(id);
        return doctorRepository.save(updatedDoctor);
    }

    @Override
    public void delete(long id){
        doctorRepository.deleteById(id);
    }

    private DoctorDTO convertToDoctorDTO(Doctor doctor) {

        return modelMapper.map(doctor, DoctorDTO.class);
    }
}
