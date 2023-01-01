package com.informatics.CSCB869.data.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import com.informatics.CSCB869.data.entity.Doctor;
import com.informatics.CSCB869.data.entity.Profession;

@DataJpaTest
public class ProfessionRepositoryTest {
    
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProfessionRepository professionRepository;

    @Autowired 
    private DoctorRepository doctorRepository;

    Profession createProfession(){
        Profession profession = new Profession();
        profession.setId(1);
        profession.setName("NewProfession");
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
        Profession profession = createProfession();
        testEntityManager.persistAndFlush(profession);
        assertThat(professionRepository.findById(profession.getId()).isEmpty()==false);
    }

    @Test 
    void create(){
        Profession profession = createProfession();
        testEntityManager.persistAndFlush(profession);
        assertThat(professionRepository.findById(profession.getId()).isEmpty()==false);
    }

    @Test 
    void update(){
        Profession profession = createProfession();
        testEntityManager.persistAndFlush(profession);
        String newName="NotNewAnymore";
        profession.setName(newName);
        testEntityManager.persistAndFlush(profession);
        assertThat(professionRepository.findById(profession.getId()).get().getName()==newName);
    }

    @Test
    void checkDoctorLink(){
        Profession profession = createProfession();
        Doctor doctor = createDoctor();
        testEntityManager.persistAndFlush(profession);
        testEntityManager.persistAndFlush(doctor);
        assertThat(professionRepository.findById(profession.getId()).get().getDoctors().size()>0);
    }
}
