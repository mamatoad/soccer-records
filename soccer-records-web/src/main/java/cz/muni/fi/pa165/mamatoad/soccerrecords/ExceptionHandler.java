package cz.muni.fi.pa165.mamatoad.soccerrecords;

import java.security.AccessControlException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;

/**
 *
 * @author Maros Klimovsky
 */
public class ExceptionHandler extends DefaultExceptionHandler {
    public Resolution handleDatabaseException(AccessControlException exc, HttpServletRequest request, HttpServletResponse response) {
        // do something to handle SQL exceptions
        if ("Not logged in.".equals(exc.getMessage())) {
            return new ForwardResolution("/user/login.jsp");
        }
        return new ForwardResolution("/user/insufficientRights.jsp");
        
    }
    
}