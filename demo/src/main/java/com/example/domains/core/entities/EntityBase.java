package com.example.domains.core.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class EntityBase<E> {
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Transient
	@JsonIgnore
	public Set<ConstraintViolation<E>> getErrors() {
		return validator.validate((E) this);
	}

	@JsonIgnore
	@Transient
	public String getErrorsString() {
		Set<ConstraintViolation<E>> lst = getErrors();
		if (lst.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder("ERRORES:");
		lst.stream().sorted((a,b)->a.getPropertyPath().toString().compareTo(b.getPropertyPath().toString()))
			.forEach(item -> sb.append(" " + item.getPropertyPath() + ": " + item.getMessage() + "."));
		return sb.toString();
	}

	@JsonIgnore
	@Transient
	public Map<String, String> getErrorsFields() {
		Set<ConstraintViolation<E>> lst = getErrors();
		if (lst.isEmpty())
			return null;
		Map<String, String> errors = new HashMap<>();
		lst.stream().sorted((a,b)->a.getPropertyPath().toString().compareTo(b.getPropertyPath().toString()))
			.forEach(item -> errors.put(item.getPropertyPath().toString(), 
					(errors.containsKey(item.getPropertyPath().toString()) ? errors.get(item.getPropertyPath().toString()) + ", " : "") 
					+ item.getMessage()));
		return errors;
	}

	@Transient
	@JsonIgnore
	public boolean isValid() {
		return getErrors().size() == 0;
	}

	@Transient
	@JsonIgnore
	public boolean isInvalid() {
		return !isValid();
	}
	
}