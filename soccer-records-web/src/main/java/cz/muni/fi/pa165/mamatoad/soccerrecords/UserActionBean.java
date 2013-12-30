package cz.muni.fi.pa165.mamatoad.soccerrecords;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Acl;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.Role;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.UserService;
import java.util.List;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.taglibs.standard.functions.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

/**
 * Stripes ActionBean for user authentication.
 *
 * @author Matus Nemec
 */
@UrlBinding("/users/{$event}/{user.id}")
public class UserActionBean extends BaseActionBean {
   
    @ValidateNestedProperties(value = {
        @Validate(on = {"add", "doLogin", "save"}, field = "login", required = true),
        @Validate(on = {"add", "doLogin"}, field = "password", required = true)
    })
    private UserTO user;
    
    private List<UserTO> users;

    final static Logger logger = LoggerFactory.getLogger(UserActionBean.class);
    
    private String passwordConfirmation;
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    private boolean isAdmin;

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    
    public List<UserTO> getUsers() {
        return users;
    }
    
    public String getOriginPage(){
        return getContext().getRequest().getHeader("Referer");
    }
        
    @SpringBean
    protected UserService userService;
    
    public UserTO getUser() {
        return user;
    }
    private String loggedInUser;
    
    public String getLoggedInUser(){
       loadUser();
       UserTO u = securityFacade.getCurrentLoggedInUser();
       if(u == null){
           return "none";
       }
          return u.getLogin();
    }
    
    public String getUserRole(){
       loadUser();
       UserTO u = securityFacade.getCurrentLoggedInUser();
       if(u == null){
           return "none";
       }
       if(u.getLogin().equals("admin") )
          return u.getLogin();
       return "user";
    }
    
    public void setUser(UserTO user) {
        this.user = user;
    }
    
    public Resolution list() {
        logger.debug("list() ");
        
        users = userService.getAllUsers();
        return new ForwardResolution("/user/list.jsp");
    }
    
    @DefaultHandler
    public Resolution login() {
        logger.debug("login() " );
        if (securityFacade.getCurrentLoggedInUser() != null) {
            return new RedirectResolution("/");
        }
        return new ForwardResolution("/user/login.jsp");
    }


    public Resolution doLogin() {
        logger.debug("doLogin() ");
         
        try {
            securityFacade.login(user.getLogin(), user.getPassword());
            saveUser();
        } catch (IllegalStateException | IllegalArgumentException ex) {
            ValidationErrors errors = new ValidationErrors();
            errors.add( "Same teams", new LocalizableError("user.invalid") );
            getContext().setValidationErrors(errors);
            return getContext().getSourcePageResolution();
        }
         String originalPage = getContext().getRequest().getHeader("Referer");
         if (originalPage.endsWith("doLogin")) {
            return new RedirectResolution("/");
        }
        return new RedirectResolution(originalPage, false);
    }

    @Acl(Role.USER)
    public Resolution logout() {
        logger.debug("logout() ");
        return new ForwardResolution("/user/logout.jsp");
    }

    public Resolution doLogout() {
        logger.debug("doLogout() ");
        securityFacade.logout();
        saveUser();
        return new ForwardResolution("/index.jsp");
    }

    public Resolution insufficientRights(){
        logger.debug("insufficientRights() ");
        while (!getContext().getMessages().isEmpty()) {
            getContext().getMessages().remove(0);
        }
        
        getContext().getMessages().add(new LocalizableMessage("user.insufficientRights"));
        return new ForwardResolution("/user/insufficientRights.jsp");
    }
    
    @Acl(Role.ADMIN)
    public Resolution add(){
        logger.debug("add() ");
        
        if (!user.getPassword().equals(passwordConfirmation)) {
         ValidationErrors errors = new ValidationErrors();
        errors.add( "Different passwords", new LocalizableError("user.notSamePass") );
        getContext().setValidationErrors(errors);
        return getContext().getSourcePageResolution();   
        }
        
        if(isAdmin){
            user.setRole(Role.ADMIN);
        }else{
            user.setRole(Role.USER);
        }
        user.setPassword(securityFacade.createHash(user.getPassword()));
        try
        {
        userService.add(user);
        }catch(DataAccessException ex)
        {
           addMessageToContext("user.added.wrong",user.getLogin());
            return new RedirectResolution("/users/list"); 
        }
        addMessageToContext("user.added",user.getLogin());
        return new RedirectResolution("/users/list");
    }

    
    @Acl(Role.USER)
    public Resolution delete() {
        user = userService.getById(Long.parseLong(getContext().getRequest().getParameter("user.id")));
        logger.debug("delete() "+user.getId().toString());
        userService.delete(user);
        addMessageToContext("user.deleted",user.getLogin());
        return new RedirectResolution("/users/list");
    }
    
    public void saveUser()
    {
        logger.debug("saveUser() ");
        getContext().getRequest().getSession().setAttribute("user", securityFacade.getUser());
    }
   
    @Acl(Role.USER)
    public Resolution edit() {
        
        String ids = getContext().getRequest().getParameter("user.id");
        logger.debug("edit() "+ids);
        user = userService.getById(Long.parseLong(ids));
        isAdmin = (user.getRole() == Role.ADMIN);
        
        return new ForwardResolution("/user/edit.jsp");
    }
    
    public Resolution save() {
        logger.debug("save() "+user.getId().toString());
        if(isAdmin){
            user.setRole(Role.ADMIN);
        }else{
            user.setRole(Role.USER);
        }
        if(passwordConfirmation==null && user.getPassword()==null  ||
                passwordConfirmation.equals("") && user.getPassword().equals(""))
        {
            UserTO userTO = userService.getById(Long.parseLong(getContext().getRequest().getParameter("user.id")));
            user.setPassword(userTO.getPassword());
        }else{
            user.setPassword(securityFacade.createHash(user.getPassword()));
        }
        try
        {
        userService.update(user);
        }catch(DataAccessException ex)
        {
            addMessageToContext("user.edited.wrong",user.getLogin());
            return new RedirectResolution("/users/list");
        }
        addMessageToContext("user.edited",user.getLogin());
        
        return new RedirectResolution("/users/list");
    }
    
     
    public Resolution cancel(){
        logger.debug("cancel() "+user.getId().toString());
        return new RedirectResolution("/users/list");
    }
    
    
    private void addMessageToContext(String action, String params) {
        getContext().getMessages().add(new LocalizableMessage(action, Functions.escapeXml(params)));
    }

}