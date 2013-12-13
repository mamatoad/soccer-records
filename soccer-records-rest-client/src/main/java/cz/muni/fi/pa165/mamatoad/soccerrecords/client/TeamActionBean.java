package cz.muni.fi.pa165.mamatoad.soccerrecords.client;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
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

    // --- list teams ---
    
    private List<TeamTO> teams;

    public List<TeamTO> getTeams() {
        return teams;
    }
    
    private void retrieveAllTeams() {
        Response response = teamWebTarget.request(MEDIA_TYPE).get();
        teams = response.readEntity(new GenericType<List<TeamTO>>() {});
    }

    @DefaultHandler
    public Resolution list() {
        retrieveAllTeams();
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
        Entity<TeamTO> teamEntity = Entity.entity(team, MEDIA_TYPE);
        Response response = teamWebTarget.request(MEDIA_TYPE).post(teamEntity);
//        response.getStatus()
        showMessage("team.add.message");
        return new RedirectResolution(this.getClass(), "list");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors ve) throws Exception {
        retrieveAllTeams();
        return null;
    }

    // --- delete team ---

    public Resolution delete() {
        WebTarget deleteTeamWebTarget = teamWebTarget.path("delete").queryParam("id", team.getTeamId());
        Response response = deleteTeamWebTarget.request(MEDIA_TYPE).delete();
//        response.getStatus()
        showMessage("team.delete.message");
        return new RedirectResolution(this.getClass(), "list");
    }

    // --- edit team ----

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save", "detail"})
    public void loadTeamFromDatabase() {
        String ids = getContext().getRequest().getParameter("team.teamId");
        if (ids == null) return;
        
        WebTarget getTeamWebTarget = teamWebTarget.path("detail").queryParam("id", ids);
        Response response = getTeamWebTarget.request(MEDIA_TYPE).get();
        team = response.readEntity(TeamTO.class);
//        response.getStatus()
    }

    public Resolution edit() {
        return new ForwardResolution("/team/edit.jsp");
    }

    public Resolution save() {
        Entity<TeamTO> teamEntity = Entity.entity(team, MEDIA_TYPE);
        Response response = teamWebTarget.request(MEDIA_TYPE).put(teamEntity);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    // --- team detail ---
    
    private List<PlayerTO> players;

    public List<PlayerTO> getPlayers() {
        return players;
    }
    
    public Resolution detail() {
        WebTarget playersWebTarget = webTarget.path("player").path("byTeam").queryParam("id", team.getTeamId());
        Response response = playersWebTarget.request(MEDIA_TYPE).get();
        players = response.readEntity(new GenericType<List<PlayerTO>>() {});
        return new ForwardResolution("/team/detail.jsp");
    }
    
    private void showError(String messageKey) {
        getContext().getMessages().add(new LocalizableMessage(messageKey));
    }
    
    private void showMessage(String messageKey) {
        getContext().getMessages().add(new LocalizableMessage(messageKey, Functions.escapeXml(team.getTeamName())));
    }

}
