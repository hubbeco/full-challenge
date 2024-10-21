package kafka.com.br.ApiTest.exceptions;

import kafka.com.br.ApiTest.dto.ExceptionData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

@RestControllerAdvice
public class CustomizedException{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionData> customArgumentNotValidException(MethodArgumentNotValidException exception, ServletWebRequest request){
        var errors = exception.getFieldErrors();

        var error = exception.getBody();

        String detail = errors.getLast().getDefaultMessage();

        System.out.println(errors.getFirst().getObjectName());

        String type = error.getType().toString();
        String title = error.getTitle();
        String endpoint = request.getRequest().getRequestURI();
        ExceptionData returnException = new ExceptionData(type, title, detail, endpoint);

        return new ResponseEntity<ExceptionData>(returnException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionData> error500(Exception exception, ServletWebRequest request){
        String endpoint = request.getRequest().getRequestURI();
        ExceptionData returnException = new ExceptionData("about:blank", "InternalServerError", "Some generic error name.", endpoint);

        return new ResponseEntity<ExceptionData>(returnException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CaptchaIsNotCorrect.class)
    public ResponseEntity<ExceptionData> customCaptchaException(Exception exception, ServletWebRequest request){

        String type = "about:blank";
        String title = "UnauthorizedError";
        String detail = exception.getMessage();
        String endpoint = request.getRequest().getRequestURI();

        ExceptionData returnException = new ExceptionData(type, title, detail, endpoint);

        return new ResponseEntity<ExceptionData>(returnException, HttpStatus.BAD_REQUEST);
    }
}
