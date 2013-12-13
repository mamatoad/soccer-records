package cz.muni.fi.pa165.mamatoad.soccerrecords.rest;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * REST interface for manipulation with player
 * @author Maros Klimovsky
 */
@Service
@Path("player")
public class PlayerRest {

    @Autowired
    private PlayerService playerService;
        
    @GET
    @Produces(MediaType.TEXT_XML)
    public List<PlayerTO> getAllPlayers() {
        return playerService.getAllPlayers();
    }
    
    @GET
    @Path("byTeam")
    @Produces(MediaType.TEXT_XML)
    public List<PlayerTO> getPlayersByTeamId(@QueryParam("id") Long id) {
        return playerService.getPlayersByTeamId(id);
    }

    @GET
    @Path("detail")
    @Produces(MediaType.TEXT_XML)
    public PlayerTO getPlayerById(@QueryParam("id") Long id) {
        return playerService.getPlayerById(id);
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    public Response create(PlayerTO playerTO) {
        playerService.add(playerTO);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.TEXT_XML)
    public Response update(PlayerTO playerTO) {
        playerService.update(playerTO);
        return Response.ok().build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_XML)
    @Path("delete")
    public Response delete(@QueryParam("id") long id) {
        playerService.remove(playerService.getPlayerById(id));
        return Response.ok().build();
    }
}