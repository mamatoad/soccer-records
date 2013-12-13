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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * REST interface for manipulation with player
 *
 * @author Maros Klimovsky
 */
@Service
@Path("player")
public class PlayerRest {

    @Autowired
    private PlayerService playerService;

    @GET
    @Produces(MediaType.TEXT_XML)
    public Response getAllPlayers() {
        List<PlayerTO> players;
        try {
            players = playerService.getAllPlayers();
        } catch (DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (players.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(new GenericEntity<List<PlayerTO>>(players) {
        }).build();
    }

    @GET
    @Path("byTeam")
    @Produces(MediaType.TEXT_XML)
    public Response getPlayersByTeamId(@QueryParam("id") Long id) {
        List<PlayerTO> players;
        try {
            players = playerService.getPlayersByTeamId(id);
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (players.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(new GenericEntity<List<PlayerTO>>(players) {
        }).build();
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("detail")
    public Response getById(@QueryParam("id") Long id) {
        PlayerTO player;
        try {
            player = playerService.getPlayerById(id);
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (player == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(player).build();
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    public Response create(PlayerTO playerTO) {
        try {
            playerService.add(playerTO);
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Consumes(MediaType.TEXT_XML)
    public Response update(PlayerTO playerTO) {
        try {
            playerService.update(playerTO);
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_XML)
    @Path("delete")
    public Response delete(@QueryParam("id") long id) {
        try {
            playerService.remove(playerService.getPlayerById(id));
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } 
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}