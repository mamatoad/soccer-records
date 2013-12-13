package cz.muni.fi.pa165.mamatoad.soccerrecords;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * Base actionBean implementing the required methods for setting and getting context.
 *
 * @author Maros Klimovsky
 */
public abstract class BaseActionBean implements ActionBean {
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
