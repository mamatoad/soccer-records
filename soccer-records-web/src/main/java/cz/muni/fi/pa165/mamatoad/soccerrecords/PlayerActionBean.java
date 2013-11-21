package cz.muni.fi.pa165.mamatoad.soccerrecords;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.TeamService;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 *
 * @author Matus Nemec
 */
@UrlBinding("/players/{$event}/{player.playerId}")
public class PlayerActionBean extends BaseActionBean implements ValidationErrorHandler {

    @SpringBean("playerService")
    protected PlayerService playerService;
    @SpringBean("teamService")
    protected TeamService teamService;
    
    private List<PlayerTO> players;

    public List<PlayerTO> getPlayers() {
        return players;
    }
    
    @DefaultHandler
    public Resolution list() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        if (ids == null) {
            players = playerService.getAllPlayers();
        } else {
            players = playerService.getPlayersByTeamId(Long.parseLong(ids));
        }
        return new ForwardResolution("/player/list.jsp");
    }
    
    public Resolution detail() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        player = playerService.getPlayerById(Long.parseLong(ids));
        if (player == null) return new RedirectResolution(this.getClass(), "error");
        return new ForwardResolution("/player/detail.jsp");
    }
        
	@ValidateNestedProperties(value = {
            @Validate(on = {"add","save"}, field = "playerName", required = true)
    })
    private PlayerTO player;
		
	public PlayerTO getPlayer() {
        return player;
    }
	
    public void setPlayer(PlayerTO player) {
        this.player = player;
    }
    
    public List<TeamTO> getTeams() {
        return teamService.getAllTeams();
    }
    
    public Resolution create() {
        return new ForwardResolution("/player/new.jsp");
    }
    
	public Resolution add() {
        playerService.add(player);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution edit() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        if (ids == null) return new ForwardResolution("/player/list.jsp");
        player = playerService.getPlayerById(Long.parseLong(ids));
        if (player == null) return new ForwardResolution("/player/list.jsp");
        return new ForwardResolution("/player/edit.jsp");
    }    
    
    public Resolution save() {
        playerService.update(player);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    public Resolution cancel() {
        return new RedirectResolution(this.getClass(), "list");
    }
    
    private String searchPlayerName;
    private Long searchTeamId;

    public void setSearchPlayerName(String searchPlayerName) {
        this.searchPlayerName = searchPlayerName;
    }

    public String getSearchPlayerName() {
        return searchPlayerName;
    }

    public void setSearchTeamId(Long searchTeamId) {
        this.searchTeamId = searchTeamId;
    }
    
    public Long getSearchTeamId() {
        return searchTeamId;
    }
    
    public Resolution search() {
        if (searchPlayerName != null) {
            players = playerService.getPlayersByName(searchPlayerName);
            return new ForwardResolution("/player/list.jsp");
        }
        
        if (searchTeamId != null) {
            return new RedirectResolution("/players/list/" + searchTeamId);
        }
        return new RedirectResolution(this.getClass(), "error");
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        players = playerService.getAllPlayers();
        //return null to let the event handling continue
        return null;
    }

}
