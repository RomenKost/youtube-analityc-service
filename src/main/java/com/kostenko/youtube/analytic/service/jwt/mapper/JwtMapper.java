package com.kostenko.youtube.analytic.service.jwt.mapper;

import com.kostenko.youtube.analytic.controller.dto.security.JwtDto;
import com.kostenko.youtube.analytic.model.security.jwt.Jwt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {
    JwtDto jwtToJwtDto(Jwt jwt);
}
