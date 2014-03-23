package io.pivotal.pso.dummy;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN)
public class AuthenticateException extends RuntimeException{

}
