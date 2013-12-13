package cz.muni.fi.pa165.mamatoad.soccerrecords;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.GoalService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.TeamService;
import java.util.List;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.taglibs.standard.functions.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

/**
 * Stripes ActionBean for handling team operations.
 *
 * @author Tomas Livora
 */
@UrlBinding("/teams/{$event}/{team.teamId}")
public class TeamActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(TeamActionBean.class);

    @SpringBean
    protected TeamService teamService;

    @SpringBean
    protected PlayerService playerService;
    
    @SpringBean
    protected GoalService goalService;

    // --- list teams ---
    
    private List<TeamTO> teams;

    @DefaultHandler
    public Resolution list() {
        log.debug("list()");
        teams = teamService.getAllTeams();
        return new ForwardResolution("/team/list.jsp");
    }

    public List<TeamTO> getTeams() {
        return teams;
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
        log.debug("add() team={}", team);
        teamService.add(team);
        showMessage("team.add.message");
        return new RedirectResolution(this.getClass(), "list");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        teams = teamService.getAllTeams();
       
        return null;
    }

    // --- delete team ---

    public Resolution delete() {
        log.debug("delete({})", team.getTeamId());
        team = teamService.getTeamById(team.getTeamId());
        try {
            teamService.remove(team);
        } catch (DataAccessException ex) {
            showMessage("team.delete.dependency");
            return new RedirectResolution(this.getClass(), "list");
        }
        
        showMessage("team.delete.message");
        return new RedirectResolution(this.getClass(), "list");
    }

    // --- edit team ----

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit","detail"})
    public void loadTeamFromDatabase() {
        String ids = getContext().getRequest().getParameter("team.teamId");
        if (ids == null) return;
        team = teamService.getTeamById(Long.parseLong(ids));
    }

    public Resolution edit() {
        log.debug("edit() team={}", team);
        return new ForwardResolution("/team/edit.jsp");
    }

    public Resolution save() {
        log.debug("save() team={}", team);
        teamService.update(team);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    // --- team detail ---
    
    private List<PlayerTO> players;

    public List<PlayerTO> getPlayers() {
        return players;
    }
    
    public Resolution detail() {
        log.debug("detail({})", team.getTeamId());
        players = playerService.getPlayersByTeamId(team.getTeamId());
        return new ForwardResolution("/team/detail.jsp");
    }
    
    private void showMessage(String messageKey) {
        getContext().getMessages().add(new LocalizableMessage(messageKey, Functions.escapeXml(team.getTeamName())));
    }

}
