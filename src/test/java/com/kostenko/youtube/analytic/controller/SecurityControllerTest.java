package com.kostenko.youtube.analytic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.controller.dto.security.JwtDto;
import com.kostenko.youtube.analytic.exception.response.AuthorizationExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.AccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.InvalidUserCredentialsException;
import com.kostenko.youtube.analytic.exception.security.UserNotFoundException;
import com.kostenko.youtube.analytic.model.security.user.UserStatus;
import com.kostenko.youtube.analytic.service.jwt.mapper.JwtMapper;
import com.kostenko.youtube.analytic.service.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.kostenko.youtube.analytic.exception.response.MockedExceptionResponseProvider.*;
import static com.kostenko.youtube.analytic.controller.dto.security.MockedSecurityDtoProvider.*;
import static com.kostenko.youtube.analytic.model.security.MockedSecurityModelsProvider.*;
import static com.kostenko.youtube.analytic.util.security.TestSecurityConstants.*;
import static com.kostenko.youtube.analytic.util.url.YoutubeAnalyticUrls.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class SecurityControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private JwtMapper jwtMapper;
    @MockBean
    private JwtService jwtProcessor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getJwtTest() throws Exception {
        JwtDto expected = getMockedJwtDto();

        when(jwtProcessor.getJwt(TEST_USERNAME, TEST_PASSWORD))
                .thenReturn(getMockedJwt());
        when(jwtMapper.jwtToJwtDto(getMockedJwt()))
                .thenReturn(expected);

        String credentials = objectMapper.writeValueAsString(getMockedUserCredentialsDto());
        String response = mockMvc.perform(post(LOGIN).content(credentials).contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JwtDto actual = objectMapper.readValue(response, JwtDto.class);

        assertEquals(expected, actual);
    }

    @Test
    void handleInvalidUserCredentialsExceptionTest() throws Exception {
        AuthorizationExceptionResponse expected = getMockedInvalidUserCredentialsExceptionResponse();

        when(jwtProcessor.getJwt(TEST_USERNAME, TEST_PASSWORD))
                .thenThrow(new InvalidUserCredentialsException(TEST_USERNAME));

        String credentials = objectMapper.writeValueAsString(getMockedUserCredentialsDto());
        String response = mockMvc.perform(post(LOGIN).content(credentials).contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthorizationExceptionResponse actual = objectMapper.readValue(response, AuthorizationExceptionResponse.class);

        assertEquals(expected, actual);
    }

    @Test
    void handleUserNotFoundExceptionTest() throws Exception {
        AuthorizationExceptionResponse expected = getMockedUserNotFoundExceptionResponse();

        when(jwtProcessor.getJwt(TEST_USERNAME, TEST_PASSWORD))
                .thenThrow(new UserNotFoundException(TEST_USERNAME));

        String credentials = objectMapper.writeValueAsString(getMockedUserCredentialsDto());
        String response = mockMvc.perform(post(LOGIN).content(credentials).contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthorizationExceptionResponse actual = objectMapper.readValue(response, AuthorizationExceptionResponse.class);

        assertEquals(expected, actual);
    }

    @Test
    void handleAccountUnavailableExceptionTest() throws Exception {
        AuthorizationExceptionResponse expected = getMockedAccountUnavailableExceptionResponse();

        UserStatus mockedUserStatus = mock(UserStatus.class);
        when(mockedUserStatus.isAccountExpired()).thenReturn(true);
        AccountUnavailableException accountUnavailableException = new AccountUnavailableException(TEST_USERNAME, mockedUserStatus);
        when(jwtProcessor.getJwt(TEST_USERNAME, TEST_PASSWORD))
                .thenThrow(accountUnavailableException);

        String credentials = objectMapper.writeValueAsString(getMockedUserCredentialsDto());
        String response = mockMvc.perform(post(LOGIN).content(credentials).contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthorizationExceptionResponse actual = objectMapper.readValue(response, AuthorizationExceptionResponse.class);

        assertEquals(expected, actual);
    }
}
