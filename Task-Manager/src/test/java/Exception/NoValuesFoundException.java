package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Required values not found")
public class NoValuesFoundException extends RuntimeException{
	
	private static final long serialVersionUID =3935230281455340069L;

}
