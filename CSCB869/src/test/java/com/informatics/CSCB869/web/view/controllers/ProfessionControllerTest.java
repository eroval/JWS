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

import com.informatics.CSCB869.dto.CreateProfessionDTO;
import com.informatics.CSCB869.services.ProfessionService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProfessionController.class)
class ProfessionControllerTest {
    
    @MockBean
    private ProfessionService professionService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProfessions() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/professions/1/10"))
                .andExpect(status().isOk());
    }
}
