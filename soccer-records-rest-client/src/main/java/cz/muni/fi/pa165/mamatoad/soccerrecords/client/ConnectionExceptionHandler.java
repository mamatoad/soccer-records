package cz.muni.fi.pa165.mamatoad.soccerrecords.client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ProcessingException;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;

/**
 *
 * @author Tomas Livora
 */
public class ConnectionExceptionHandler extends DefaultExceptionHandler {

    public Resolution handleDatabaseException(ProcessingException exception, HttpServletRequest request, 
            HttpServletResponse response) {
        return new ForwardResolution("/server.jsp");
    }

}
