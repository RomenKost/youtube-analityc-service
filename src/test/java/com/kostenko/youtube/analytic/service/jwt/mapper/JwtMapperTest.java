package com.kostenko.youtube.analytic.service.jwt.mapper;

import com.kostenko.youtube.analytic.config.MockedDataSourceConfiguration;
import com.kostenko.youtube.analytic.controller.dto.security.JwtDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kostenko.youtube.analytic.controller.dto.security.MockedSecurityDtoProvider.*;
import static com.kostenko.youtube.analytic.model.security.MockedSecurityModelsProvider.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MockedDataSourceConfiguration.class)
class JwtMapperTest {
    @Autowired
    private JwtMapper jwtMapper;

    @Test
    void jwtToJwtDtoTest() {
        JwtDto expected = getMockedJwtDto();
        JwtDto actual = jwtMapper.jwtToJwtDto(getMockedJwt());

        assertEquals(expected, actual);
    }
}
