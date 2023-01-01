package com.informatics.CSCB869.web.view.controllers;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.informatics.CSCB869.dto.CreateDiagnoseDTO;
import com.informatics.CSCB869.services.DiagnoseService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DiagnoseController.class)
class DiagnoseControllerTest {
    
    @MockBean
    private DiagnoseService diagnoseService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getDiagnoses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/diagnoses/1/10"))
                .andExpect(status().isOk());
    }
}
