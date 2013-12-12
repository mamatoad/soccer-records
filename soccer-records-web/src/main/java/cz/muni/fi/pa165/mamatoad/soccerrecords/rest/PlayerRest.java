package cz.muni.fi.pa165.mamatoad.soccerrecords.rest;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.SecurityFacade;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import java.util.List;


import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        
    @Autowired
    private SecurityFacade securityFacade;

    @GET
    @Produces(MediaType.TEXT_XML)
    public List<PlayerTO> getAllPlayers() {
        securityFacade.setUser(null);
        securityFacade.login("rest", "rest"); // to enable full access to services via rest
        return playerService.getAllPlayers();
    }
    
    @GET
    @Path("teamId/{id}")
    @Produces(MediaType.TEXT_XML)
    public List<PlayerTO> getPlayersByTeamId(@PathParam("id") Long id) {
        securityFacade.setUser(null);
        securityFacade.login("rest", "rest"); // to enable full access to services via rest
        return playerService.getPlayersByTeamId(id);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_XML)
    public PlayerTO getPlayerById(@PathParam("id") Long id) {
        securityFacade.setUser(null);
        securityFacade.login("rest", "rest"); // to enable full access to services via rest
        return playerService.getPlayerById(id);
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    public Response create(PlayerTO playerTO) {
        securityFacade.setUser(null);
        securityFacade.login("rest", "rest"); // to enable full access to services via rest
        playerService.add(playerTO);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.TEXT_XML)
    public Response update(PlayerTO playerTO) {
        securityFacade.setUser(null);
        securityFacade.login("rest", "rest"); // to enable full access to services via rest
        playerService.update(playerTO);
        return Response.ok().build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_XML)
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
        securityFacade.setUser(null);
        securityFacade.login("rest", "rest"); // to enable full access to services via rest
        playerService.remove(playerService.getPlayerById(id));
        return Response.ok().build();
    }
}