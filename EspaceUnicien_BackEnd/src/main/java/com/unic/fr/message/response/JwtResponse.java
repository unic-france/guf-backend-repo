package com.unic.fr.message.response;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private boolean oneTimePassword;

    public JwtResponse(String accessToken, boolean oneTimePassword) {
        this.token = accessToken;
        this.oneTimePassword = oneTimePassword;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
    
    public boolean getOneTimePassword() {
    	return this.oneTimePassword;
    }
    
    public void setOneTimePassword(boolean oneTimePassword) {
    	this.oneTimePassword = oneTimePassword;
    }
}