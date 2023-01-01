package com.informatics.CSCB869.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Insurance;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.idclasses.InsuranceId;

@DataJpaTest
public class InsuranceRepositoryTest {
    
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired 
    private DoctorRepository doctorRepository;


    Patient createPatient(){
        Patient patient = new Patient();
        patient.setId(1);
        patient.setName("NewPatient");
        patient.setEgn("9803030054");
        return patient;
    }

    Insurance createInsurance(){
        Insurance insurance = new Insurance();
        LocalDate localDate = LocalDate.of(2022,03,03);
        insurance.setDate(localDate);
        insurance.setPatient(createPatient());
        return insurance;
    }

    @Test
    void findById(){
        Patient patient = createPatient();
        Insurance insurance = createInsurance();
        testEntityManager.persistAndFlush(patient);
        testEntityManager.persistAndFlush(insurance);
        assertThat(insuranceRepository.findById(new InsuranceId(insurance.getDate(), patient.getId())).isEmpty()==false);
    }

    @Test 
    void create(){
        Patient patient = createPatient();
        Insurance insurance = createInsurance();
        testEntityManager.persistAndFlush(patient);
        testEntityManager.persistAndFlush(insurance);
        assertThat(insuranceRepository.findById(new InsuranceId(insurance.getDate(), patient.getId())).isEmpty()==false);
    }

    @Test
    void checkPatientLink(){
        Patient patient = createPatient();
        Insurance insurance = createInsurance();
        testEntityManager.persistAndFlush(patient);
        testEntityManager.persistAndFlush(insurance);
        assertThat(insuranceRepository.findById(new InsuranceId(insurance.getDate(), patient.getId())).get().getPatient().getId()==patient.getId());
    }
}
