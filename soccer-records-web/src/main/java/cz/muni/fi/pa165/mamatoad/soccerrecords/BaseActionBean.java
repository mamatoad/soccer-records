package cz.muni.fi.pa165.mamatoad.soccerrecords;

import cz.muni.fi.pa165.mamatoad.soccerrecords.dto.UserTO;
import cz.muni.fi.pa165.mamatoad.soccerrecords.security.SecurityFacade;
import java.security.AccessControlException;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * Base actionBean implementing the required methods for setting and getting context.
 *
 * @author Maros Klimovsky
 */
public abstract class BaseActionBean implements ActionBean {
    private ActionBeanContext context;

    @SpringBean
    protected SecurityFacade securityFacade;
    
    public void loadUser()
    {
        securityFacade.setUser((UserTO)getContext().getRequest().getSession().getAttribute("user"));
    }
    
    @Before(stages = LifecycleStage.EventHandling)
    protected void isUserAuthorized() throws NoSuchMethodException{
        loadUser();
        if (securityFacade.authorize(this.getClass().getDeclaredMethod(getContext().getEventName()))) {
            return;
        }
        if (securityFacade.getCurrentLoggedInUser() == null) {
            throw new AccessControlException("Not logged in.");
        }
        
        throw new AccessControlException("Unauthorized");
    }

    
    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

}