package at.ac.htlleonding.ssdemo;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("/selective")
public class SelectiveSender {

    @Inject
    Sse sse;

    Map<String, SseEventSink> sinkMap = new ConcurrentHashMap<>();

    @Path("/connect")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void mySSE(@QueryParam("clientId") String clientId, @Context SseEventSink eventSink) {
        sinkMap.put(clientId, eventSink);
    }

    @Path("/sendMessage")
    public void sendMessage(@QueryParam("message") String message, @QueryParam("clientId") String clientId) {
        var msgEvent = sse.newEvent(message);
        sinkMap.get(clientId).send(msgEvent);
    }

}
