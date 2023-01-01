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

import com.informatics.CSCB869.dto.SickLeaveDTO;
import com.informatics.CSCB869.services.SickLeaveService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SickLeaveController.class)
class SickLeaveControllerTest {
    
    @MockBean
    private SickLeaveService sickleaveService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSickLeaves() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sickleaves/1/10"))
                .andExpect(status().isOk());
    }
}
