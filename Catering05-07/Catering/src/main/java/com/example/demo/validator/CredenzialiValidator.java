package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Credenziali;
import com.example.demo.model.Utente;
import com.example.demo.service.CredenzialiService;


@Component //Lo annoto con component così lo posso usare con autowired nel controller
public class CredenzialiValidator implements Validator {

    @Autowired
    private CredenzialiService credenzialiService; //ci serve per utilizzare la repository
    
    final Integer MAX_USERNAME_LENGTH = 20;
    final Integer MIN_USERNAME_LENGTH = 4;
    final Integer MAX_PASSWORD_LENGTH = 20;
    final Integer MIN_PASSWORD_LENGTH = 6;

	@Override
	public void validate(Object target, Errors errors) {
        Credenziali credentials = (Credenziali) target;
        String username = credentials.getUsername().trim();//trim restituisce la stringa senza spazi
        String password = credentials.getPassword().trim();

        if (username.isEmpty())
            errors.rejectValue("username", "required");//rejectValue(CAMPO,MESSAGGIORisposta)
        else if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
            errors.rejectValue("username", "size");
        else if (this.credenzialiService.getCredenziali(username) != null)
            errors.rejectValue("username", "duplicate");

        if (password.isEmpty())
            errors.rejectValue("password", "required");
        else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            errors.rejectValue("password", "size");
		
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Utente.class.equals(clazz);
	}
    
}
