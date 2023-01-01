package com.informatics.CSCB869.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.DoctorPatientGP;
import com.informatics.CSCB869.data.entity.Patient;
import com.informatics.CSCB869.data.entity.Profession;

@DataJpaTest
public class PatientRepositoryTest {
    
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PatientRepository patientRepository;


    Patient createPatient(){
        Patient patient = new Patient();
        patient.setId(1);
        patient.setName("NewPatient");
        patient.setEgn("9803030054");
        return patient;
    }
    
    Profession createProfession(){
        Profession profession = new Profession();
        profession.setId(1);
        profession.setName("Newprofession");
        return profession;
    }

    Doctor createDoctor(){
        Doctor doctor = new Doctor();
        doctor.setId(1);
        doctor.setName("CoolDude");
        doctor.setProfession(createProfession());
        return doctor;
    }

    @Test
    void findById(){
        Patient patient = createPatient();
        testEntityManager.persistAndFlush(patient);
        assertThat(patientRepository.findById(patient.getId()).isEmpty()==false);
    }

    @Test 
    void create(){
        Patient patient = createPatient();
        testEntityManager.persistAndFlush(patient);
        assertThat(patientRepository.findById(patient.getId()).isEmpty()==false);
    }

    @Test 
    void update(){
        Patient patient = createPatient();
        testEntityManager.persistAndFlush(patient);
        String newName="NotNewAnymore";
        patient.setName(newName);
        testEntityManager.persistAndFlush(patient);
        assertThat(patientRepository.findById(patient.getId()).get().getName()==newName);
    }

    @Test
    void checkDoctorLink(){
        Patient patient = createPatient();
        Doctor doctor = createDoctor();
        DoctorPatientGP doctorGP = new DoctorPatientGP();
        doctorGP.setPatient(patient);
        doctorGP.setDoctor(doctor);
        testEntityManager.persistAndFlush(patient);
        testEntityManager.persistAndFlush(doctor);
        testEntityManager.persistAndFlush(doctorGP);
        assertThat(patientRepository.findById(patient.getId()).get().getGp().getDoctor().getId()==1);
    }
}
