package com.informatics.CSCB869.services;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.informatics.CSCB869.data.entity.Diagnose;
import com.informatics.CSCB869.data.repository.DiagnoseRepository;
import com.informatics.CSCB869.dto.DiagnoseDTO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DiagnoseServiceImplTest {
    @MockBean
    private DiagnoseRepository diagnoseRepository;

    @Autowired
    private DiagnoseService diagnoseService;

    @Test
    void getDiagnoseById() {
        long diagnoseId = 1;

        Diagnose diagnose = new Diagnose();
        diagnose.setId(diagnoseId);

        Mockito.when(diagnoseRepository.findById(diagnoseId))
                .thenReturn(Optional.of(diagnose));

        DiagnoseDTO diagnoseDTO = diagnoseService.getDiagnose(diagnoseId);

        assertThat(diagnoseDTO.getId()).isEqualTo(diagnose.getId());
    }

}
