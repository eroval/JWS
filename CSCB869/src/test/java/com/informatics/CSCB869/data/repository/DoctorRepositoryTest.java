package com.informatics.CSCB869.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Profession;

@DataJpaTest
public class DoctorRepositoryTest {
    
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private DoctorRepository professionRepository;

    @Autowired 
    private DoctorRepository doctorRepository;

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
        Doctor doctor = createDoctor();
        testEntityManager.persistAndFlush(doctor);
        assertThat(doctorRepository.findById(doctor.getId()).isEmpty()==false);
    }

    @Test 
    void create(){
        Doctor doctor = createDoctor();
        testEntityManager.persistAndFlush(doctor);
        assertThat(doctorRepository.findById(doctor.getId()).isEmpty()==false);
    }

    @Test 
    void update(){
        Doctor doctor = createDoctor();
        testEntityManager.persistAndFlush(doctor);
        String newName="NotNewAnymore";
        doctor.setName(newName);
        testEntityManager.persistAndFlush(doctor);
        assertThat(doctorRepository.findById(doctor.getId()).get().getName()==newName);
    }

    @Test
    void checkProfessionLink(){
        Profession profession = createProfession();
        Doctor doctor = createDoctor();
        testEntityManager.persistAndFlush(profession);
        testEntityManager.persistAndFlush(doctor);
        assertThat(doctorRepository.findById(doctor.getId()).get().getProfession()==profession);
    }
}
