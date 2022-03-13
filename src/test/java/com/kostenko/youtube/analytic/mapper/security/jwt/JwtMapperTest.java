package com.kostenko.youtube.analytic.mapper.security.jwt;

import com.kostenko.youtube.analytic.dto.security.YoutubeAnalyticJwtDto;
import com.kostenko.youtube.analytic.dto.youtube.analytic.YoutubeAnalyticDTOs;
import com.kostenko.youtube.analytic.mapper.security.token.JwtMapper;
import com.kostenko.youtube.analytic.model.Models;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;
import com.kostenko.youtube.analytic.service.security.user.YoutubeAnalyticUserRole;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(classes = JwtMapperTest.class)
@ComponentScan(basePackageClasses = JwtMapper.class)
class JwtMapperTest {
    @Mock
    private YoutubeAnalyticUserRole userRole;

    @Autowired
    private JwtMapper jwtMapper;

    @Test
    void jwtToJwtDtoTest() {
        YoutubeAnalyticJwt jwt = Models.getJwt(userRole);
        YoutubeAnalyticJwtDto expected = YoutubeAnalyticDTOs.getJwtDto(userRole);

        YoutubeAnalyticJwtDto actual = jwtMapper.jwtToJwtDto(jwt);

        assertEquals(expected, actual);
    }

    @Test
    void jwtToJwtDtoWhenJwtIsNullReturnNullTest() {
        assertNull(jwtMapper.jwtToJwtDto(null));
    }
}
