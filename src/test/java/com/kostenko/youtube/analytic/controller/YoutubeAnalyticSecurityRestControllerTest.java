package com.kostenko.youtube.analytic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.dto.security.YoutubeAnalyticJwtDto;
import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticDTOs;
import com.kostenko.youtube.analytic.mapper.security.token.JwtMapper;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;
import com.kostenko.youtube.analytic.service.security.jwt.processor.YoutubeAnalyticJwtProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = YoutubeAnalyticSecurityRestController.class)
public class YoutubeAnalyticSecurityRestControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private JwtMapper jwtMapper;
    @MockBean
    private YoutubeAnalyticJwtProcessor jwtProcessor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getJwtTest() throws Exception {
        YoutubeAnalyticJwtDto expected = YoutubeAnalyticDTOs.getJwtDto(ROLE_CUSTOMER);
        YoutubeAnalyticJwt jwtModel = Models.getJwt(ROLE_CUSTOMER);
        when(jwtProcessor.getJwt("any username", "any password"))
                .thenReturn(jwtModel);
        when(jwtMapper.jwtToJwtDto(jwtModel))
                .thenReturn(expected);
        String credentials = objectMapper
                .writeValueAsString(YoutubeAnalyticDTOs.getCredentialsDto());

        String response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/youtube/analytic/v1/login")
                                .content(credentials)
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        YoutubeAnalyticJwtDto actual = objectMapper.readValue(response, YoutubeAnalyticJwtDto.class);

        assertEquals(expected, actual);
    }
}
