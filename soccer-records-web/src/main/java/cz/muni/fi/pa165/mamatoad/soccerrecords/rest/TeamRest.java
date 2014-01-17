package cz.muni.fi.pa165.mamatoad.soccerrecords.rest;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.SecurityFacade;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.TeamService;
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
 * REST interface for manipulation with team
 *
 * @author Maros Klimovsky
 */
@Service
@Path("team")
public class TeamRest {

    @Autowired
    private TeamService teamService;

    @Autowired
    private SecurityFacade securityFacade;
    
    @GET
    @Produces(MediaType.TEXT_XML)
    public Response getAllTeams() {
        List<TeamTO> teams;
        
        restAuthentication();
        
        try {
            teams = teamService.getAllTeams();
        } catch (DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (teams.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok().entity(new GenericEntity<List<TeamTO>>(teams) {
        }).build();
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("detail")
    public Response getById(@QueryParam("id") Long id) {
        TeamTO team;
        
        restAuthentication();
        
        try {
            team = teamService.getTeamById(id);
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (team == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(team).build();
    }

    @POST
    @Consumes(MediaType.TEXT_XML)
    public Response create(TeamTO teamTO) {
        
        restAuthentication();
        
        try {
            teamService.add(teamTO);
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Consumes(MediaType.TEXT_XML)
    public Response update(TeamTO teamTO) {
        
        restAuthentication();
        try {
            teamService.update(teamTO);
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Consumes(MediaType.TEXT_XML)
    @Path("delete")
    public Response delete(@QueryParam("id") Long id) {
        
        restAuthentication();
        try {
            teamService.remove(teamService.getTeamById(id));
        } catch (IllegalArgumentException | DataAccessException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    private void restAuthentication() {
        securityFacade.setUser(null);
        securityFacade.login("rest", "rest");
        
    }
}