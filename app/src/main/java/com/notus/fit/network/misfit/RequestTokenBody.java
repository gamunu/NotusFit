package com.notus.fit.network.misfit;


public class RequestTokenBody {
    final String client_id;
    final String client_secret;
    String code;
    final String grant_type;
    final String redirect_uri;

    public RequestTokenBody(String code) {
        this.grant_type = "authorization_code";
        this.client_id = MisfitClient.MISFIT_APP_KEY;
        this.client_secret = MisfitClient.MISFIT_APP_SECRET;
        this.redirect_uri = MisfitClient.REDIRECTION_URI;
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
