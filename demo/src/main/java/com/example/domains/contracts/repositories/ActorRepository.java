package com.example.domains.contracts.repositories;

import java.util.List;
import java.sql.Timestamp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domains.core.repositories.contracts.RepositoryWithProjections;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.domains.entities.dtos.ActorShort;

@RepositoryRestResource(path="actores", itemResourceRel="actor", collectionResourceRel="actores")
public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor> {
	List<Actor> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp fecha);
	List<Actor> findByActorIdGreaterThanEqual(int idInicial, Sort orden);

	@RestResource(path = "nuevos")
	@Query("SELECT a FROM Actor a WHERE a.actorId > ?1")
	List<Actor> findNovedadesJPQL(int idInicial);

	@Query(value = "SELECT * FROM actor a WHERE a.actor_id > ?1", nativeQuery = true)
	List<Actor> findNovedadesSQL(int idInicial);

	@Query("SELECT a FROM Actor a WHERE a.actorId > ?1")
	List<ActorDTO> findActorDto(int idInicial);
	@Query("SELECT a FROM Actor a WHERE a.actorId > ?1")
	List<ActorShort> findActorShort(int idInicial);
	
	@Override
	@RestResource(exported = false)
	void deleteById(Integer id);

}
