package com.example.domains.entities.dtos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.example.domains.entities.Actor;

@Projection(name = "ActorCorto", types = Actor.class)
public interface ActorShort {
	int getActorId();
	
	@Value("#{target.firstName + ' ' + target.lastName}")
	String getNombreCompleto();
}
