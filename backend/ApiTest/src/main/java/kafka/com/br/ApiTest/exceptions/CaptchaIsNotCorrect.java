package kafka.com.br.ApiTest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CaptchaIsNotCorrect extends RuntimeException{

    public CaptchaIsNotCorrect(String e){
        super(e);
    }
}
