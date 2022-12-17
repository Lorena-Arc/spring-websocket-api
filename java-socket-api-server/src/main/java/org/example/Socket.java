package org.example;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Data
class Message {
    String subject;
    String content;
}

@Slf4j
@Component
@ServerEndpoint(
        value = "/websocket",
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class
)
public class Socket {
    private Session session;
    public static Set<Socket> listeners = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        listeners.add(this);
        log.debug("client" + session + "connected");
    }

    @OnMessage //Allows the client to send message to the socket.
    public void onMessage(Message message, Session session) {
        broadcast(message, session);
    }

    @OnClose
    public void onClose(Session session) {
        listeners.remove(this);
        log.debug("client" + session + "disconnected");
    }

    public static void broadcast(Message message, Session session) {
        for (Socket listener : listeners) {
            if (listener.session == session) {
                continue;
            }
            listener.sendMessage(message);
        }
    }

    private void sendMessage(Message message) {
        try {
            this.session.getBasicRemote().sendObject(message);
        } catch (Exception e) {
            log.error("Caught exception while sending message to Session" + this.session.getId() + " " + e.getMessage());
        }
    }
}


