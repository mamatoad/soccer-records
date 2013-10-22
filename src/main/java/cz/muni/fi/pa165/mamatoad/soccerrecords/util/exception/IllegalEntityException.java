package cz.muni.fi.pa165.mamatoad.soccerrecords.util.exception;

/**
 *
 * @author Matus Nemec
 */
public class IllegalEntityException extends RuntimeException {

    public IllegalEntityException() {}

    public IllegalEntityException(String message) {
        super(message);
    }

    public IllegalEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalEntityException(Throwable cause) {
        super(cause);
    }

}
