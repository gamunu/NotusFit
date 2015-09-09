package com.notus.fit.network.misfit;


public class RequestTokenBody {

    final String client_id = "ngfYaXSjJorI1dA4";
    final String client_secret = "SYhk7fVPsFnGSVhVBjUeC9DcDldfMagq";
    final String grant_type = "authorization_code";
    final String redirect_uri = "http://gabilheri.com/fithub?";
    String code;

    public RequestTokenBody(String s) {
        code = s;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String s) {
        code = s;
    }
}
