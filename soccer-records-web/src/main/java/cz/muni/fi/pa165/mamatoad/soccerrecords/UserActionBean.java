package cz.muni.fi.pa165.mamatoad.soccerrecords;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.service.UserService;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 * Stripes ActionBean for user authentication.
 *
 * @author Matus Nemec
 */
@UrlBinding("/users/{$event}")
public class UserActionBean extends BaseActionBean {
      
    @ValidateNestedProperties(value = {
        @Validate(on = {"add", "doLogin"}, field = "login", required = true),
        @Validate(on = {"add", "doLogin"}, field = "password", required = true)
    })
    private UserTO userTO;
    
        
    @SpringBean
    protected UserService userService;
    
    public UserTO getUserTO() {
        return userTO;
    }
    private String loggedInUser;
    
         
    public String getLoggedInUser(){
       UserTO u = securityFacade.getCurrentLoggedInUser();
       if(u == null){
           return "none";
       }
          return u.getLogin();
    }
    
    public void setUserTO(UserTO userTO) {
        this.userTO = userTO;
    }
    
    @DefaultHandler
    public Resolution login() {
        return new ForwardResolution("/user/login.jsp");
    }

    public Resolution doLogin() {
        try {
            securityFacade.login(userTO.getLogin(), userTO.getPassword());
            saveUser();
        } catch (IllegalStateException ex) {
            return new ForwardResolution("/player/list.jsp");
        } catch (IllegalArgumentException ex) {
            return new ForwardResolution("/team/list.jsp");
        }
        return new ForwardResolution("/index.jsp");
    }

    public Resolution logout() {
        return new ForwardResolution("/user/logout.jsp");
    }

    public Resolution doLogout() {
        securityFacade.logout();
        saveUser();
        return new ForwardResolution("/index.jsp");
    }

    public void saveUser()
    {
        getContext().getRequest().getSession().setAttribute("user", securityFacade.getUser());
    }
}