package cz.muni.fi.pa165.mamatoad.soccerrecords.client;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.taglibs.standard.functions.Functions;

/**
 * Stripes ActionBean for team operations handling.
 *
 * @author Tomas Livora
 */
@UrlBinding("/teams/{$event}/{team.teamId}")
public class TeamActionBean extends BaseActionBean implements ValidationErrorHandler {
    
    private final WebTarget teamWebTarget = webTarget.path("team");
    
    // --- rest calls ---
    
    private List<TeamTO> retrieveAllTeams() {
        Response response = teamWebTarget.request(MEDIA_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<List<TeamTO>>() {});
        }
        return new ArrayList<>();
    }
    
    private TeamTO retrieveTeamById(long teamId) {
        WebTarget getTeamWebTarget = teamWebTarget.path("detail").queryParam("id", teamId);
        Response response = getTeamWebTarget.request(MEDIA_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(TeamTO.class);
        }
        return null;
    }
    
    private List<PlayerTO> retrievePlayersByTeam(long teamId) {
        WebTarget playersWebTarget = webTarget.path("player").path("byTeam").queryParam("id", teamId);
        Response response = playersWebTarget.request(MEDIA_TYPE).get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.readEntity(new GenericType<List<PlayerTO>>() {});
        }
        return new ArrayList<>();
    }
    
    private void addTeam(TeamTO team) {
        Entity<TeamTO> teamEntity = Entity.entity(team, MEDIA_TYPE);
        Response response = teamWebTarget.request(MEDIA_TYPE).post(teamEntity);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            showMessage("team.add.ok", team.getTeamName());
        } else {
            showMessage("team.add.cannot", team.getTeamName());
        }
    }
    
    private void updateTeam(TeamTO team) {
        Entity<TeamTO> teamEntity = Entity.entity(team, MEDIA_TYPE);
        Response response = teamWebTarget.request(MEDIA_TYPE).put(teamEntity);
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            showMessage("team.update.ok", team.getTeamName());
        } else {
            showMessage("team.update.cannot", team.getTeamName());
        }
    }
    
    private void removeTeam(TeamTO team) {
        WebTarget deleteTeamWebTarget = teamWebTarget.path("delete").queryParam("id", team.getTeamId());
        Response response = deleteTeamWebTarget.request(MEDIA_TYPE).delete();
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            showMessage("team.remove.ok");
        } else {
            showMessage("team.remove.cannot");
        }
    }

    // --- list teams ---
    
    private List<TeamTO> teams;

    public List<TeamTO> getTeams() {
        return teams;
    }

    @DefaultHandler
    public Resolution list() {
        teams = retrieveAllTeams();
        return new ForwardResolution("/team/list.jsp");
    }
    
    // --- add team ---
    
    @ValidateNestedProperties(value = {
            @Validate(on = {"add", "save"}, field = "teamName", required = true)
    })
    private TeamTO team;

    public TeamTO getTeam() {
        return team;
    }

    public void setTeam(TeamTO team) {
        this.team = team;
    }
    
    public Resolution create() {
        return new ForwardResolution("/team/new.jsp");
    }

    public Resolution add() {
        addTeam(team);
        return new RedirectResolution(this.getClass(), "list");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors ve) throws Exception {
        retrieveAllTeams();
        return null;
    }

    // --- delete team ---

    public Resolution delete() {
        removeTeam(team);
        return new RedirectResolution(this.getClass(), "list");
    }

    // --- edit team ----

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "detail"})
    public void loadTeamFromDatabase() {
        String ids = getContext().getRequest().getParameter("team.teamId");
        if (ids == null) return;
        team = retrieveTeamById(Long.parseLong(ids));
    }

    public Resolution edit() {
        return new ForwardResolution("/team/edit.jsp");
    }

    public Resolution save() {
        updateTeam(team);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    // --- team detail ---
    
    private List<PlayerTO> players;

    public List<PlayerTO> getPlayers() {
        return players;
    }
    
    public Resolution detail() {
        players = retrievePlayersByTeam(team.getTeamId());
        return new ForwardResolution("/team/detail.jsp");
    }
    
    private void showMessage(String messageKey) {
        getContext().getMessages().add(new LocalizableMessage(messageKey));
    }
    
    private void showMessage(String messageKey, String parameter) {
        getContext().getMessages().add(new LocalizableMessage(messageKey, Functions.escapeXml(parameter)));
    }

}
