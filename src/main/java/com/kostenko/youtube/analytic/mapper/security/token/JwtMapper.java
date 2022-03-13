package com.kostenko.youtube.analytic.mapper.security.token;

import com.kostenko.youtube.analytic.dto.security.YoutubeAnalyticJwtDto;
import com.kostenko.youtube.analytic.model.security.YoutubeAnalyticJwt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {
    YoutubeAnalyticJwtDto jwtToJwtDto(YoutubeAnalyticJwt jwt);
}
