package cz.muni.fi.pa165.mamatoad.soccerrecords;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.TeamService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.taglibs.standard.functions.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stripes ActionBean for handling player operations.
 * 
 * @author Matus Nemec
 */
@UrlBinding("/players/{$event}/{player.playerId}")
public class PlayerActionBean extends BaseActionBean implements ValidationErrorHandler {

    @SpringBean("playerService")
    protected PlayerService playerService;
    @SpringBean("teamService")
    protected TeamService teamService;
    final static Logger logger = LoggerFactory.getLogger(PlayerActionBean.class);
    
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
        return teamService.getAllTeams();
    }

    // displaying methods
    
    @DefaultHandler
    @Acl(Role.USER)
    public Resolution list() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        
        logger.debug("list() " + ids);
        
        if (ids != null) {
            players = playerService.getPlayersByTeamId(Long.parseLong(ids));
            if (players.isEmpty()) {
                addMessageToContext("player.list.teamFilterNoPlayers", null);
                return new RedirectResolution(this.getClass());
            } else {
                addMessageToContext("player.list.teamFilterOk", null);
            }
        } else {
            players = playerService.getAllPlayers();
        }
        
        return new ForwardResolution("/player/list.jsp");
    }

    @Acl(Role.USER)
    public Resolution detail() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        
        logger.debug("detail() " + ids);
        
        if (ids == null) {
            logger.warn("detail called without id");
            addMessageToContext("player.detail.noId", null);
            return new RedirectResolution(this.getClass());
        }
        player = playerService.getPlayerById(Long.parseLong(ids));
        if (player == null) {
            logger.warn("detail called for nonexisting player");
            addMessageToContext("player.detail.notFound", null);
            return new RedirectResolution(this.getClass());
        }
        return new ForwardResolution("/player/detail.jsp");
    }
    
    // inserting methods
    @Acl(Role.ADMIN)
    public Resolution create() {
        logger.debug("create()");
        return new ForwardResolution("/player/new.jsp");
    }

    public Resolution add() {
        logger.debug("add()", player);
        
        playerService.add(player);
        addMessageToContext("player.add.ok", player.getPlayerName());
        return new RedirectResolution(this.getClass(), "list");
    }

    // editing methods
    
    @Acl(Role.ADMIN)
    public Resolution edit() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        logger.debug("edit() " + ids);
        if (ids == null) {
            logger.warn("edit called without id");
            addMessageToContext("player.edit.noId", null);
            return new RedirectResolution(this.getClass());
        }
        player = playerService.getPlayerById(Long.parseLong(ids));
        if (player == null) {
            logger.warn("edit called for not existing player");
            addMessageToContext("player.edit.notFound", null);
            return new RedirectResolution(this.getClass());
        }
        return new ForwardResolution("/player/edit.jsp");
    }

    public Resolution save() {
        logger.debug("save() ", player);
        playerService.update(player);
        addMessageToContext("player.save.ok", player.getPlayerName());
        return new RedirectResolution(this.getClass(), "list");
    }

    // cancel method
    
    public Resolution cancel() {
        logger.debug("cancel()");
        addMessageToContext("player.canceled", null);
        return new RedirectResolution(this.getClass());
    }
    
    // deletion methods
    @Acl(Role.ADMIN)
    public Resolution delete() {
        String ids = getContext().getRequest().getParameter("player.playerId");
        logger.debug("delete() " + ids);
        if (ids == null) {
            logger.warn("delete called without id");
            addMessageToContext("player.delete.noId", null);
            return new RedirectResolution(this.getClass());
        }
        player = playerService.getPlayerById(Long.parseLong(ids));
        if (player == null) {
            logger.warn("delete called with invalid ");
            addMessageToContext("player.delete.notFound", null);
            return new RedirectResolution(this.getClass());
        }
        return new ForwardResolution("/player/delete.jsp");
    }

    public Resolution remove() {
        player = playerService.getPlayerById(player.getPlayerId());
        logger.debug("remove()", player);
        if (player.getPlayerGoalsScored() != 0) {
            addMessageToContext("player.delete.constraints", player.getPlayerName());
            return new RedirectResolution(this.getClass());
        }
        addMessageToContext("player.remove.ok", player.getPlayerName());
        playerService.remove(player);
        return new RedirectResolution(this.getClass(), "list");
    }
    
    // searching
    
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

    @Acl(Role.USER)
    public Resolution search() {
        if (searchPlayerName != null) {
            players = playerService.getPlayersByName(searchPlayerName);
            if (players.isEmpty()) {
                addMessageToContext("player.search.nameFilterNoPlayers", null);
                return new RedirectResolution(this.getClass());
            } else {
                addMessageToContext("player.search.nameFilterOk", null);
            }
            return new ForwardResolution("/player/list.jsp");
        }

        if (searchTeamId != null) {
            return new RedirectResolution("/players/list/" + searchTeamId);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
 
    private String term;
 
    public void setTerm(String term) {
        this.term = term;
    }
 
    public Resolution find() {
        final List<Map> data = new ArrayList<>();
        for(PlayerTO p : playerService.getFilteredPlayers(term)) {
            HashMap<String, Object> m = new HashMap<>();
            m.put("label", p.getPlayerName() + " (" + p.getTeamName() + ")");
            m.put("value", p.getPlayerName());
            m.put("player", p);
            data.add(m);
        }
        return new Resolution() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), data);
            }
        };
    }
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) throws Exception {
        //fill up the data for the table if validation errors occured
        players = playerService.getAllPlayers();
        //return null to let the event handling continue
        return null;
    }

    private void addMessageToContext(String action, String params) {
        getContext().getMessages().add(new LocalizableMessage(action, Functions.escapeXml(params)));
    }
}
