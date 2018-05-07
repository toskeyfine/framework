package com.toskey.framework.core.base.entity;

import javax.websocket.Session;

public class WebSocket {

    public WebSocket(Session session) {
        this.session = session;
    }

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void sendMessage(String message){
        this.session.getAsyncRemote().sendText(message);
    }
}
