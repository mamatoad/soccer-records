package cz.muni.fi.pa165.mamatoad.soccerrecords.rest;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.TeamService;
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
 *
 * @author Maros Klimovsky
 */
@Service
@Path("team")
public class TeamRest {

    @Autowired
    private TeamService teamService;
    
    
    @GET
    @Produces(MediaType.TEXT_XML)
    public List<TeamTO> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("{id}")
    public TeamTO getById(@PathParam("id") long id) {
        return teamService.getTeamById(id);
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    public Response create(TeamTO teamTO) {
        teamService.add(teamTO);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.TEXT_XML)
    public Response update(TeamTO teamTO) {
        teamService.update(teamTO);
        return Response.ok().build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_XML)
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
       teamService.remove(teamService.getTeamById(id));
       return Response.ok().build();
    }
}