package org.blade.msg;

public class TokenMessage extends  DataMessage{


    public TokenMessage(int status, String description, String token, Object data) {
        super(status, description, data);
        this.token = token;
    }

    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
