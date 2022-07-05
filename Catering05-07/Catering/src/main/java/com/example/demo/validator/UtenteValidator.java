package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Utente;

@Component
public class UtenteValidator implements Validator{
	
	/*In questo caso non utilizziamo il service poichè possono esistere duplicati di nome e cognome(non ci serve parlare con la repository*/
	
	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;

	@Override
	public void validate(Object target, Errors errors) {
        Utente utente = (Utente) target;
        String nome = utente.getNome().trim();
        String cognome = utente.getCognome().trim();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Utente.class.equals(clazz);
	}
}
