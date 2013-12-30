/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.mamatoad.soccerrecords;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.GoalTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.MatchTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.PlayerTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.GoalService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.MatchService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.TeamService;
import java.util.ArrayList;
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
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.taglibs.standard.functions.Functions;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 *
 * @author Adriana
 */
@UrlBinding("/goals/{$event}/{goal.id}")
public class GoalActionBean extends BaseActionBean implements ValidationErrorHandler {

    final static Logger log = LoggerFactory.getLogger(GoalActionBean.class);
    
    @SpringBean
    protected GoalService goalService;
    
    @SpringBean
    protected TeamService teamService;
    
    @SpringBean
    protected PlayerService playerService;
    
    @SpringBean
    protected MatchService matchService;
   
    private List<GoalTO> goals;
    
    private GoalTO goal;
    
    private List<PlayerTO> players;
    
    private List<Integer> minutes = new ArrayList<>();
    
    private Integer minute;
    
    private List<Integer> hours = new ArrayList<>();
    
    private Integer hour;
    
    private String matchIdUrl;
    
    
    public GoalActionBean(){
        for(int i = 0; i<60;i++){
            minutes.add(i);
        }
        
        for(int i = 0; i<24;i++){
            hours.add(i);
        }
    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getMinutes() {
        return minutes;
    }

    public Integer getMinute() {
        return minute;
    }

    public Integer getHour() {
        return hour;
    }

    public List<PlayerTO> getPlayers() {
        return players;
    }

    public List<GoalTO> getGoals() {
        return goals;
    }
    
    public GoalTO getGoal() {
        return goal;
    }

    public String getMatchIdUrl() {
        return matchIdUrl;
    }

    public void setGoal(GoalTO goal) {
        this.goal = goal;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"list"})
    public void loadMatchId() {
        matchIdUrl = getContext().getRequest().getParameter("goal.id");
    }
    
    @DefaultHandler
    @Acl(Role.USER)
    public Resolution list() {
        log.debug("list()");
        matchIdUrl = getContext().getRequest().getParameter("goal.id");
        Long matchId = Long.parseLong(matchIdUrl);
        goals = goalService.getGoalsByMatchId(matchId);
        return new ForwardResolution("/goal/list.jsp");
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"list"})
    public void loadGoalInfoFromDatabase() {
        String id = getContext().getRequest().getParameter("goal.id");
        if (id == null) return;
        Long matchId = Long.parseLong(id);
        
        goal = new GoalTO();
        goal.setMatchId(matchId);
        MatchTO match = matchService.getMatchById(matchId);
        
        List<TeamTO> teams = new ArrayList<>();
        teams.add(teamService.getTeamById(match.getHomeTeamId()));
        teams.add(teamService.getTeamById(match.getVisitingTeamId()));
        
        players = new ArrayList<>();
        players.addAll(playerService.getPlayersByTeamId(match.getHomeTeamId()));
        players.addAll(playerService.getPlayersByTeamId(match.getVisitingTeamId()));
    }
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"confirm"})
    public void beforeConfirm() {
        matchIdUrl = getContext().getRequest().getParameter("goal.id");
    }
    public Resolution confirm(){
        PlayerTO player = playerService.getPlayerById(goal.getPlayerId());
        TeamTO team = teamService.getTeamById(player.getTeamId());
        goal.setTeamId(team.getTeamId());
        goal.setTeamName(team.getTeamName());
        goal.setPlayerName(player.getPlayerName());
        goal.setMatchId(Long.parseLong(matchIdUrl));
        LocalTime time = new LocalTime(hour,minute);
        goal.setTime(time);
        
        log.debug("add() goal={}", goal);
        goalService.add(goal);
        addMessageToContext("goal.add.ok", goal.getPlayerName());
        
        return new RedirectResolution("/goals/list/"+matchIdUrl);
    }
    
    public Resolution cancelEdit(){
        return new RedirectResolution("/goals/list/"+goal.getMatchId().toString());
    }
    
    
    @Override
    public Resolution handleValidationErrors(ValidationErrors ve) throws Exception {
        Long id = Long.parseLong(getContext().getRequest().getParameter("goal.id"));
        goals = goalService.getGoalsByMatchId(id);
        return null;
    }
     @Acl(Role.ADMIN)
     public Resolution delete() {
        Long id = Long.parseLong(getContext().getRequest().getParameter("goal.id"));
        goal = goalService.getGoalById(id);
        String urlId = goal.getMatchId().toString();
        
        log.debug("delete({})", goal.getGoalId());
        
        goalService.remove(goal);
        addMessageToContext("goal.remove.ok", goal.getPlayerName());
        
        return new RedirectResolution("/goals/list/"+urlId);
    }
     
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit", "save","cancelEdit"})
    public void loadGoalFromDatabase() {
        Long goalId = Long.parseLong(getContext().getRequest().getParameter("goal.id"));
        if (goalId == null) return;
        goal = goalService.getGoalById(goalId);
        MatchTO match = matchService.getMatchById(goal.getMatchId());
        
        List<TeamTO> teams = new ArrayList<>();
        teams.add(teamService.getTeamById(match.getHomeTeamId()));
        teams.add(teamService.getTeamById(match.getVisitingTeamId()));
        
        players = new ArrayList<>();
        players.addAll(playerService.getPlayersByTeamId(match.getHomeTeamId()));
        players.addAll(playerService.getPlayersByTeamId(match.getVisitingTeamId()));
        
        minute = goal.getTime().getMinuteOfHour();
        hour = goal.getTime().getHourOfDay();
    }
    @Acl(Role.ADMIN)
    public Resolution edit() {
        log.debug("edit() goal={}", goal);
        return new ForwardResolution("/goal/edit.jsp");
    }

    public Resolution save() {
        PlayerTO player = playerService.getPlayerById(goal.getPlayerId());
        TeamTO team = teamService.getTeamById(player.getTeamId());
        goal.setTeamId(team.getTeamId());
        goal.setTeamName(team.getTeamName());
        goal.setPlayerName(player.getPlayerName());
        LocalTime time = new LocalTime(hour,minute);
        goal.setGoalId(Long.parseLong(getContext().getRequest().getParameter("goal.id")));
        goal.setTime(time);
        
        log.debug("save() goal={}", goal);
        
        goalService.update(goal);
        addMessageToContext("goal.edit.ok", goal.getPlayerName());
        
        return new RedirectResolution("/goals/list/"+goal.getMatchId().toString());
    }
    
    private void addMessageToContext(String action, String params) {
        getContext().getMessages().add(new LocalizableMessage(action, Functions.escapeXml(params)));
    }
}
