package cz.muni.fi.pa165.mamatoad.soccerrecords.client;

import static cz.muni.fi.pa165.mamatoad.soccerrecords.client.BaseActionBean.MEDIA_TYPE;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.taglibs.standard.functions.Functions;

/**
 * Stripes ActionBean for player operations handling.
 *
 * @author Tomas Livora
 */
@UrlBinding("/players/{$event}/{player.playerId}")
public class PlayerActionBean extends BaseActionBean implements ValidationErrorHandler {
    
    private final WebTarget playerWebTarget = webTarget.path("player");
    
    private List<PlayerTO> players;

    public List<PlayerTO> getPlayers() {
        return players;
    }
    
    @ValidateNestedProperties(value = {
        @Validate(on = {"add", "save"}, field = "playerName", required = true)
    })
    private PlayerTO player;

    public PlayerTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerTO player) {
        this.player = player;
    }

    public List<TeamTO> getTeams() {
        WebTarget teamWebTarget = webTarget.path("team");
        Response response = teamWebTarget.request(MEDIA_TYPE).get();
        return response.readEntity(new GenericType<List<TeamTO>>() {});
    }
    
    // REST calls
    
    private List<PlayerTO> retrieveAllPlayers() {
        Response response = playerWebTarget.request(MEDIA_TYPE).get();
        return response.readEntity(new GenericType<List<PlayerTO>>() {});
    }
    
    private List<PlayerTO> retrievePlayersByTeamId(long teamId) {
        WebTarget playersByTeamWebTarget = playerWebTarget.path("byTeam").queryParam("id", teamId);
        Response response = playersByTeamWebTarget.request(MEDIA_TYPE).get();
        return response.readEntity(new GenericType<List<PlayerTO>>() {});
    }
    
    private PlayerTO retrievePlayerById(long playerId) {
        WebTarget playersByTeamWebTarget = playerWebTarget.path("detail").queryParam("id", playerId);
        Response response = playersByTeamWebTarget.request(MEDIA_TYPE).get();
        return response.readEntity(PlayerTO.class);
    }
    
    private void addPlayer(PlayerTO player) {
        Entity<PlayerTO> playerEntity = Entity.entity(player, MEDIA_TYPE);
        Response response = playerWebTarget.request(MEDIA_TYPE).post(playerEntity);
    }
    
    private void updatePlayer(PlayerTO player) {
        Entity<PlayerTO> playerEntity = Entity.entity(player, MEDIA_TYPE);
        Response response = playerWebTarget.request(MEDIA_TYPE).put(playerEntity);
    }
    
    private void removePlayer(Long playerId) {
        WebTarget deletePlayerWebTarget = playerWebTarget.path("delete").queryParam("id", playerId);
        Response response = deletePlayerWebTarget.request(MEDIA_TYPE).delete();
    }

    // displaying methods
    
    @DefaultHandler
    public Resolution list() {
        String ids = getContext().getRequest().getParameter("player.playerId");
                
        if (ids != null) {
            players = retrievePlayersByTeamId(Long.parseLong(ids));
            if (players.isEmpty()) {
                addMessageToContext("player.list.teamFilterNoPlayers", null);
                return new RedirectResolution(this.getClass());
            } else {
                addMessageToContext("player.list.teamFilterOk", null);
            }
        } else {
            players = retrieveAllPlayers();
        }
        return new ForwardResolution("/player/list.jsp");
    }

    public Resolution detail() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        
        if (ids == null) {
            addMessageToContext("player.detail.noId", null);
            return new RedirectResolution(this.getClass());
        }
        player = retrievePlayerById(Long.parseLong(ids));
        if (player == null) {
            addMessageToContext("player.detail.notFound", null);
            return new RedirectResolution(this.getClass());
        }
        return new ForwardResolution("/player/detail.jsp");
    }
    
    // inserting methods

    public Resolution create() {
        return new ForwardResolution("/player/new.jsp");
    }

    public Resolution add() {
        addPlayer(player);
        addMessageToContext("player.add.ok", player.getPlayerName());
        return new RedirectResolution(this.getClass(), "list");
    }

    // editing methods
    
    public Resolution edit() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        if (ids == null) {
            addMessageToContext("player.edit.noId", null);
            return new RedirectResolution(this.getClass());
        }
        player = retrievePlayerById(Long.parseLong(ids));
        if (player == null) {
            addMessageToContext("player.edit.notFound", null);
            return new RedirectResolution(this.getClass());
        }
        return new ForwardResolution("/player/edit.jsp");
    }

    public Resolution save() {
        updatePlayer(player);
        addMessageToContext("player.save.ok", player.getPlayerName());
        return new RedirectResolution(this.getClass(), "list");
    }
    
    // deletion methods

    public Resolution delete() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        if (ids == null) {
            addMessageToContext("player.delete.noId", null);
            return new RedirectResolution(this.getClass());
        }
        player = retrievePlayerById(Long.parseLong(ids));
        if (player == null) {
            addMessageToContext("player.delete.notFound", null);
            return new RedirectResolution(this.getClass());
        }
        return new ForwardResolution("/player/delete.jsp");
    }

    public Resolution remove() {
        player = retrievePlayerById(player.getPlayerId());
        if (player.getPlayerGoalsScored() != 0) {
            addMessageToContext("player.delete.constraints", player.getPlayerName());
            return new RedirectResolution(this.getClass());
        }
        addMessageToContext("player.remove.ok", player.getPlayerName());
        removePlayer(player.getPlayerId());
        return new RedirectResolution(this.getClass(), "list");
    }
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        players = retrieveAllPlayers();
        //return null to let the event handling continue
        return null;
    }

    private void addMessageToContext(String action, String params) {
        getContext().getMessages().add(new LocalizableMessage(action, Functions.escapeXml(params)));
    }

}
