package com.informatics.CSCB869.services;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.informatics.CSCB869.data.entity.Insurance;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.repository.InsuranceRepository;
import com.informatics.CSCB869.dto.InsuranceDTO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class InsuranceServiceImplTest {
    @MockBean
    private InsuranceRepository insuranceRepository;

    @Autowired
    private InsuranceService insuranceService;

    @Test
    void getInsuranceById() {
        long insuranceId = 1;

        Patient patient = new Patient();
        patient.setId(1);
        Insurance insurance = new Insurance();
        insurance.setPatientId(insuranceId);;

        Mockito.when(insuranceRepository.findById(insuranceId))
                .thenReturn(Optional.of(insurance));
                
        assertThat(insurance.getPatientId()==patient.getId());
    }

}
