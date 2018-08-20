package courseapp.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidLoginException extends RuntimeException{
	public InvalidLoginException(String exception) {
	    super(exception);
	  }
}
