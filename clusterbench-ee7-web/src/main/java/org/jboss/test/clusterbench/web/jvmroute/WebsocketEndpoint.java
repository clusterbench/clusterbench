package org.jboss.test.clusterbench.web.jvmroute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.test.clusterbench.common.jvmroute.CommonJvmRoute;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Michal Karm Babacek
 */
@ServerEndpoint(value = "/websocket")
public class WebsocketEndpoint {

    private static final Log log = LogFactory.getLog(WebsocketEndpoint.class);

    private static final String GUEST_PREFIX = "client";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final Map<String, WebsocketEndpoint> connections = new ConcurrentHashMap<>();

    private final String nickname;
    private Session session;
    private final CommonJvmRoute commonJvmRoute = new CommonJvmRoute();

    public WebsocketEndpoint() {
        nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
    }

    @OnOpen
    public void start(Session session) {
        this.session = session;
        connections.put(nickname, this);
        String message = String.format("Client:%s Action:%s Server:%s", nickname, "has joined", commonJvmRoute.jvmRoute());
        sendMsg(message, nickname);
    }

    @OnClose
    public void end() {
        connections.remove(nickname);
        String message = String.format("Client:%s Action:%s", nickname, "has disconnected.");
        log.debug("Chat end: message: " + message + ", nickname:" + nickname);
        if (connections.containsKey(nickname)) {
            sendMsg(message, nickname);
        }
    }

    @OnMessage
    public void incoming(String message) {
        String filteredMessage = String.format("Client:%s Msg:\"%s\" Server:%s Counter:%d", nickname, message, commonJvmRoute.jvmRoute(), counter.getAndIncrement());
        sendMsg(filteredMessage, nickname);
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("Chat Error: " + t.toString(), t);
    }

    private static void sendMsg(String msg, String nickname) {
        WebsocketEndpoint client = connections.get(nickname);
        try {
            synchronized (client) {
                client.session.getBasicRemote().sendText(msg);
            }
        } catch (IOException e) {
            log.debug("Chat Error: Failed to send message to client, nickname: " + nickname, e);
            connections.remove(client);
            try {
                client.session.close();
            } catch (IOException e1) {
                // Ignore
            }
            String message = String.format("Client:%s Action:%s", client.nickname, "has been disconnected.");
            sendMsg(message, nickname);
        }
    }

}
