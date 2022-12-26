package com.kostenko.youtube.analytic.exception.message;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public final String ANALYTIC_SERVICE_UNAVAILABLE = "Youtube analytic service is unavailable.";
    public final String CHANNEL_NOT_FOUND = "Channel wasn't found.";
    public final String VIDEOS_NOT_FOUND = "Videos weren't found.";

    public final String JWT_IS_INCORRECT = "JWT is incorrect (jwt = {}).";
    public final String JWT_IS_STITCHED = "JWT is stitched (expired {}).";

    public final String INVALID_CREDENTIALS = "Invalid credentials for username = {}.";
    public final String USER_NOT_FOUND = "User with username {} wasn't found.";
    public final String ACCOUNT_UNAVAILABLE = "Account with username = {} is unavailable: {}.";
}
