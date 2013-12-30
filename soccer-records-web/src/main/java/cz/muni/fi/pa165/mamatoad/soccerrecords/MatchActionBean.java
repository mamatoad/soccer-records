package cz.muni.fi.pa165.mamatoad.soccerrecords;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.GoalTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.MatchTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.TeamTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.GoalService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.MatchService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.PlayerService;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.TeamService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.taglibs.standard.functions.Functions;

/**
 *
 * @author Maros Klimovsky
 */
@UrlBinding("/matches/{$event}/{match.id}")
public class MatchActionBean extends BaseActionBean {

   final static Logger log = LoggerFactory.getLogger(MatchActionBean.class);
   
    private final DateTimeFormatter dtf = DateTimeFormat.forPattern("yy-MM-dd");
    private final DateTimeFormatter fdtf = DateTimeFormat.forPattern("dd.MM.yyyy");
    
    @SpringBean("matchService")
    protected MatchService matchService;
    
    @SpringBean("teamService")
    protected TeamService teamService;
    
    @SpringBean("goalService")
    protected GoalService goalService;
    
    @SpringBean("playerService")
    protected PlayerService playerService;
    
    private List<MatchTO> matches;
    
    private List<TeamTO> teams;
    
    @Validate(on = {"create","save"}, required = true)
    private String date;
    
    private Date currentDate;

    private String formattedDate;

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }
    
    private MatchTO match;
    
    private Long homeTeamId;
    
    private Long visitingTeamId;
    
    private List<GoalTO> goals;
    
    private Long searchTeamId;
    
    public MatchTO getMatch() {
        return match;
    }

    public Long getSerchTeamId(){
        return searchTeamId;
    }
    
    public void setSearchTeamId(Long searchTeamId){
            this.searchTeamId = searchTeamId;
        }
        
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setMatch(MatchTO match) {
        this.match = match;
    }
    
    
    public List<MatchTO> getMatches() {
        return matches;
    }
    
    public List<TeamTO> getTeams() {
        return teamService.getAllTeams();
    }
    
    public List<GoalTO> getGoals() {
        return goals;
    }
     
     public void setCurrentDate(Date currentDate) {
         this.currentDate = currentDate;
     }
     
     public Date getCurrentDate() {
         return currentDate;
     }
     
     public Long getHomeTeamId(){
         return homeTeamId;
     }
     
     public Long getVisitingTeamId(){
         return visitingTeamId;
     }
    
    @DefaultHandler
    @Acl(Role.USER)
    public Resolution list() {
        log.debug("list()");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    currentDate = new Date();
	    date = sdf.format(currentDate);
        String id = getContext().getRequest().getParameter("match.id");
        if (id == null) {
            matches = matchService.getAllMatches();
        }
        else {
                
        TeamTO team = teamService.getTeamById(Long.parseLong(id));
        if (team == null){
            matches = matchService.getAllMatches();
        }
        else {
            matches = matchService.getMatchesByTeamId(team.getTeamId());
        }
                }
        
        
        return new ForwardResolution("/match/list.jsp");
    }
    
    public Resolution filter(){
        log.debug("filter()");
         if (searchTeamId != null) {
            return new RedirectResolution("/matches/list/" + searchTeamId);
        }
        return new RedirectResolution(this.getClass(), "list");
    }
    
   
    @Acl(Role.USER)
    public Resolution detail() {
        log.debug("detail()");
        String id = getContext().getRequest().getParameter("match.id");
        if (id == null) return new RedirectResolution("/match/list.jsp");
        match = matchService.getMatchById(Long.parseLong(id));
        goals = goalService.getGoalsByMatchId(match.getMatchId());
        if (match == null) return new RedirectResolution("/match/list.jsp");
        formattedDate = match.getEventDate().toString(fdtf);
        return new ForwardResolution("/match/detail.jsp");
    }
    @Acl(Role.ADMIN)
    public Resolution add() {
        log.debug("add()");
        
        return new ForwardResolution("/match/add.jsp");
    }
    
    public Resolution create() {
        log.debug("create()");
        if (match.getHomeTeamId().equals(match.getVisitingTeamId())) {
         ValidationErrors errors = new ValidationErrors();
        errors.add( "Same teams", new LocalizableError("match.sameTeamsError") );
        getContext().setValidationErrors(errors);
        return getContext().getSourcePageResolution();   
        }
        LocalDate eventDate = null;
        
        
        eventDate = dtf.parseLocalDate(date);
        match.setEventDate(eventDate);
        matchService.add(match);
         getContext().getMessages().add(new LocalizableMessage("match.add.message"
                 ,Functions.escapeXml(match.getEventDate().toString(dtf)),Functions.escapeXml(
                 teamService.getTeamById(match.getHomeTeamId()).getTeamName())
                 ,Functions.escapeXml(teamService.getTeamById(match.getVisitingTeamId()).getTeamName())));
        return new RedirectResolution(this.getClass(), "list");
    }
   
    
    @Before(stages = LifecycleStage.BindingAndValidation, on = {"edit"})
    public void loadMatchFromDatabase() {
        String id = getContext().getRequest().getParameter("match.id");
        if (id == null) return;
        match = matchService.getMatchById(Long.parseLong(id));
    }
    @Acl(Role.ADMIN)
    public Resolution edit() {
        log.debug("edit()");
        String ids = getContext().getRequest().getParameter("match.id");
        if (ids == null) return new ForwardResolution("/match/list.jsp");
        match = matchService.getMatchById(Long.parseLong(ids));
        if (match == null) return new ForwardResolution("/match/list.jsp");
        date = match.getEventDate().toString(dtf);
        homeTeamId = match.getHomeTeamId();
        visitingTeamId = match.getVisitingTeamId();
        return new ForwardResolution("/match/edit.jsp");
          }

    public Resolution save() {
        log.debug("save()");
        if (match.getHomeTeamId().equals(match.getVisitingTeamId())) {
         ValidationErrors errors = new ValidationErrors();
        errors.add( "Same teams", new LocalizableError("match.sameTeamsError") );
        getContext().setValidationErrors(errors);
        return getContext().getSourcePageResolution();   
        }
        match.setEventDate(dtf.parseLocalDate(date));
        matchService.update(match);
            
         getContext().getMessages().add(new LocalizableMessage("match.edit.message"
                 ,Functions.escapeXml(match.getEventDate().toString(dtf)),Functions.escapeXml(
                 teamService.getTeamById(match.getHomeTeamId()).getTeamName())
                 ,Functions.escapeXml(teamService.getTeamById(match.getVisitingTeamId()).getTeamName())));
       return new RedirectResolution(this.getClass(), "list");
       
    }
     @Acl(Role.ADMIN)
     public Resolution delete() {
        log.debug("delete");
        String id = getContext().getRequest().getParameter("match.id");
         if (id == null) return new RedirectResolution(this.getClass(), "list");
         match = matchService.getMatchById(Long.parseLong(id));
         if (match == null) return new RedirectResolution(this.getClass(), "list");
         if(!goalService.getGoalsByMatchId(match.getMatchId()).isEmpty()){
             getContext().getMessages().add(new LocalizableMessage("match.delete.dependency.goal"));
        return new RedirectResolution(this.getClass(), "list");
         }
         matchService.remove(match);
      
         getContext().getMessages().add(new LocalizableMessage("match.delete.message"
                 ,Functions.escapeXml(match.getEventDate().toString(dtf)),Functions.escapeXml(
                 teamService.getTeamById(match.getHomeTeamId()).getTeamName())
                 ,Functions.escapeXml(teamService.getTeamById(match.getVisitingTeamId()).getTeamName())));
        return new RedirectResolution(this.getClass(), "list");
    }
}
