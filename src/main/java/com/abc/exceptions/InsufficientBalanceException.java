package com.abc.exceptions;

import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Insufficient loyalty point balance")
@StandardException
public class InsufficientBalanceException extends RuntimeException{

}
