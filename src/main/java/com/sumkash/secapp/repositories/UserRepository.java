package com.sumkash.secapp.repositories;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import com.sumkash.secapp.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByGithub(String github);

	Collection<User> findByName(String lastName);

}
