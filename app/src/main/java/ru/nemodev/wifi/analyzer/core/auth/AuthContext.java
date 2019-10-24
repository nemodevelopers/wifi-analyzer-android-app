package ru.nemodev.wifi.analyzer.core.auth;


import ru.nemodev.wifi.analyzer.core.network.dto.oauth.OAuthTokenDto;

public class AuthContext {

    public static OAuthTokenDto tokenDto;

    public static boolean isUserAuthComplete() {
        return tokenDto != null;
    }
}
