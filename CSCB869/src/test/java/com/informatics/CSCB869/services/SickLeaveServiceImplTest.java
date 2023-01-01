package com.informatics.CSCB869.services;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.informatics.CSCB869.data.entity.SickLeave;
import com.informatics.CSCB869.data.repository.SickLeaveRepository;
import com.informatics.CSCB869.dto.SickLeaveDTO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SickLeaveServiceImplTest {
    @MockBean
    private SickLeaveRepository sickleaveRepository;

    @Autowired
    private SickLeaveService sickleaveService;

    @Test
    void getSickLeaveById() {
        long sickleaveId = 1;

        SickLeave sickleave = new SickLeave();
        sickleave.setId(sickleaveId);

        Mockito.when(sickleaveRepository.findById(sickleaveId))
                .thenReturn(Optional.of(sickleave));

        SickLeaveDTO sickleaveDTO = sickleaveService.getSickLeave(sickleaveId);

        assertThat(sickleaveDTO.getId()).isEqualTo(sickleave.getId());
    }

}
