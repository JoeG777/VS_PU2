package com.example.VS_PU02;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;

@ServerEndpoint(value = "/election", encoders=DataEncoder.class)
public class ElectionService {
    Session session;
    @OnOpen
    public void open(Session session) {
        /*SensorSimulator sensor = new SensorSimulator(session);
        session.getUserProperties().put("sensor", sensor);
        sensor.start();
        */
        this.session = session;
        System.out.println("Hallo ich bin da: " + this.session.toString());
        BallotBox local_box = BallotBox.getInstance();
        local_box.addObserver(this);
        try {
            this.notify(local_box);
        } catch(EncodeException e){
            System.out.println("Encoding failed");
        } catch( IOException i){
            System.out.println("IO failed");
        }
    }

    @SuppressWarnings("deprecation")
    @OnClose
    public void close(Session session) {
        System.out.println("Hallo ich gehe: " + this.session.toString());
        com.example.VS_PU02.BallotBox.getInstance().removeObserver(this);

    }

    public void notify(com.example.VS_PU02.BallotBox zwischenstand) throws IOException, EncodeException {
        System.out.println("Called notify");
        RemoteEndpoint.Basic client = this.session.getBasicRemote();
        if(session.isOpen()) {
            try {
                System.out.println("Sending something");
                System.out.println(Json.createObjectBuilder().add("votes", "" + zwischenstand.countVotes()).build().toString());
                client.sendObject(zwischenstand);
            } catch (EncodeException e) {
                e.printStackTrace(System.err);
            }
        } else {
            System.out.println("Session is closed and cannot be used.");
        }
    }


}
