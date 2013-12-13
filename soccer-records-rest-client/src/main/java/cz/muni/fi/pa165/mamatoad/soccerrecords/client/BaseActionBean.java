package cz.muni.fi.pa165.mamatoad.soccerrecords.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * Base actionBean implementing the required methods for setting and getting context.
 *
 * @author Tomas Livora
 */
public abstract class BaseActionBean implements ActionBean {
    
    protected static final String MEDIA_TYPE = MediaType.TEXT_XML;
    
    protected WebTarget webTarget = ClientBuilder.newClient().target("http://localhost:8080/pa165/rest");
    
    private ActionBeanContext context;

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

}