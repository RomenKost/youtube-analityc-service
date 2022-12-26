package com.kostenko.youtube.analytic.exception.mapper;

import com.kostenko.youtube.analytic.exception.response.AuthorizationExceptionResponse;
import com.kostenko.youtube.analytic.exception.response.AnalyticExceptionResponse;
import com.kostenko.youtube.analytic.exception.response.JwtExceptionResponse;
import com.kostenko.youtube.analytic.exception.security.AccountUnavailableException;
import com.kostenko.youtube.analytic.exception.security.IncorrectJwtException;
import com.kostenko.youtube.analytic.exception.security.InvalidUserCredentialsException;
import com.kostenko.youtube.analytic.exception.security.UserNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.ChannelNotFoundException;
import com.kostenko.youtube.analytic.exception.youtube.VideosNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExceptionResponseMapper {
    @Mapping(target = "exceptionClassName", expression = "java(IncorrectJwtException.class.getSimpleName())")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    JwtExceptionResponse mapExceptionToExceptionResponse(IncorrectJwtException exception);

    @Mapping(target = "exceptionClassName", expression = "java(ChannelNotFoundException.class.getSimpleName())")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    @Mapping(target = "channelId", source = "id")
    AnalyticExceptionResponse mapExceptionToExceptionResponse(ChannelNotFoundException exception);

    @Mapping(target = "exceptionClassName", expression = "java(VideosNotFoundException.class.getSimpleName())")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    @Mapping(target = "channelId", source = "id")
    AnalyticExceptionResponse mapExceptionToExceptionResponse(VideosNotFoundException exception);

    @Mapping(target = "exceptionClassName", expression = "java(AccountUnavailableException.class.getSimpleName())")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    AuthorizationExceptionResponse mapExceptionToExceptionResponse(AccountUnavailableException exception);

    @Mapping(target = "exceptionClassName", expression = "java(InvalidUserCredentialsException.class.getSimpleName())")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    @Mapping(target = "reason", ignore = true)
    AuthorizationExceptionResponse mapExceptionToExceptionResponse(InvalidUserCredentialsException exception);

    @Mapping(target = "exceptionClassName", expression = "java(UserNotFoundException.class.getSimpleName())")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    @Mapping(target = "reason", ignore = true)
    AuthorizationExceptionResponse mapExceptionToExceptionResponse(UserNotFoundException exception);
}
