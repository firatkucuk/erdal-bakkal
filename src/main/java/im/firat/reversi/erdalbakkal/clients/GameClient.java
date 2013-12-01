
package im.firat.reversi.erdalbakkal.clients;


import im.firat.reversi.domain.Authorization;
import im.firat.reversi.domain.Game;
import javax.ws.rs.*;



/**
 * This is main service class. This can provide restful web service for external applications.
 */
@Path("/")
public interface GameClient {



    //~ --- [METHODS] --------------------------------------------------------------------------------------------------

    @DELETE
    @Path("/cancel/{cancellationCode}")
    @Produces("application/json")
    public Game cancel(@PathParam("cancellationCode") final String cancellationCode);



    //~ ----------------------------------------------------------------------------------------------------------------

    @PUT
    @Path("/move/{authCode}/{piece}")
    @Produces("application/json")
    public Game move(@PathParam("authCode") final String authCode,
            @PathParam("piece") final String piece);



    //~ ----------------------------------------------------------------------------------------------------------------

    @POST
    @Path("/start")
    @Produces("application/json")
    public Authorization start();



    //~ ----------------------------------------------------------------------------------------------------------------

    @GET
    @Path("/status")
    @Produces("application/json")
    public Game status();
}
