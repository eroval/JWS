package com.informatics.CSCB869.services;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.repository.DoctorRepository;
import com.informatics.CSCB869.dto.DoctorDTO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DoctorServiceImplTest {
    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorService doctorService;

    @Test
    void getDoctorById() {
        long doctorId = 1;

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        Mockito.when(doctorRepository.findById(doctorId))
                .thenReturn(Optional.of(doctor));

        DoctorDTO doctorDTO = doctorService.getDoctor(doctorId);

        assertThat(doctorDTO.getId()).isEqualTo(doctor.getId());
    }

}
