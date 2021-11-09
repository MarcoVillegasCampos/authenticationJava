package com.codingdojo.events.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.events.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	 User findByEmail(String email);
}
