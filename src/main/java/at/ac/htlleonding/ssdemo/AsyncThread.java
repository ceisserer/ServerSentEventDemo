package at.ac.htlleonding.ssdemo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@Path("/asyncthread")
public class AsyncThread {
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Path("/connect")
    public void asyncThread(@Context SseEventSink eventSink, @Context final Sse sse) {
        Thread thr = new Thread() {
            public void run() {
                while(!eventSink.isClosed()) {
                    OutboundSseEvent sseEvent = sse.newEvent("Today is "+ new java.util.Date().toString());
                    eventSink.send(sseEvent);
                    System.out.println("Sending message...");

                    try {
                        Thread.sleep(1000);
                    } catch (Exception ex) {
                    }
                }
            }
        };
        thr.start();
    }
}
