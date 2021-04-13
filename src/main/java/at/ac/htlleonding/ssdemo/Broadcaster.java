package at.ac.htlleonding.ssdemo;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@Path("/broadcast")
public class Broadcaster {
    @Inject
    Sse sse;

    SseBroadcaster broadcaster;

    @PostConstruct
    public void initBroadcaster() {
        broadcaster = sse.newBroadcaster();
    }

    @Path("/connect")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void connect(@Context SseEventSink eventSink) {
        // Neue "Verbindung" beim Broadcaster registrieren
        broadcaster.register(eventSink);
    }

    @Path("/sendMessage")
    public void sendMessage(@QueryParam("message") String message, @QueryParam("clientId") String clientId) {
        var msgEvent = sse.newEvent(message);
        //an alle schicken
        broadcaster.broadcast(msgEvent);
    }
}
