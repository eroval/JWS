package com.informatics.CSCB869.services;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.informatics.CSCB869.data.entity.Profession;
import com.informatics.CSCB869.data.repository.ProfessionRepository;
import com.informatics.CSCB869.dto.ProfessionDTO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProfessionServiceImplTest {
    @MockBean
    private ProfessionRepository professionRepository;

    @Autowired
    private ProfessionService professionService;

    @Test
    void getProfessionById() {
        long professionId = 1;

        Profession profession = new Profession();
        profession.setId(professionId);

        Mockito.when(professionRepository.findById(professionId))
                .thenReturn(Optional.of(profession));

        ProfessionDTO professionDTO = professionService.getProfession(professionId);

        assertThat(professionDTO.getId()).isEqualTo(profession.getId());
    }

}
