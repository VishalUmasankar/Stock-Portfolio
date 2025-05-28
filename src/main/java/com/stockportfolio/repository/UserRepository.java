package com.stockportfolio.repository;

import com.stockportfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);
	static User findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
}

