package Socket;

import Domain.Controllers.Broker;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;

@WebSocket
public class WebSocketHandler {

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        Broker.initMessage(session);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        Broker.removeCliente(user);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        Broker.broadcastMessage(session,message);
    }

    /*

    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnWebSocketConnect
    public void onConnect(Session session) throws Exception {
        sessions.add(session);
        //startService();
        message(session,"hola");
    }

    private void startService() {
        sessions.stream()//
                .filter(Session::isOpen) //
                .forEach(session -> {
                    try {
                        session.getRemote().sendString("Hola");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        /*
        Timer timer = new Timer("myTimer", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            private int counter = 0;

            @Override
            public void run() {
                broadcastMessage();
                counter++;

                if (counter >= MAX_COUNT) {
                    timer.cancel();
                    stopService();
                }

            }

        }, INITIAL_WAIT_TIME, POLLING_INTERVAL);

    }

    private void stopService() {
        sessions.stream().forEach(Session::close);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        sessions.remove(user);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        System.out.println("Got: " + message);   // Print message
        session.getRemote().sendString(message); // and send it back
    }

    private static void broadcastMessage() {
        sessions.stream()//
                .filter(Session::isOpen) //
                .forEach(session -> {
                    try {
                        session.getRemote().sendString(//
                                String.valueOf(new JSONObject()//
                                        .put("data", UUID.randomUUID())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

     */
}
