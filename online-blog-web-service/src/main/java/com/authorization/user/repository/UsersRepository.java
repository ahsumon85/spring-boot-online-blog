/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authorization.user.repository;

import com.authorization.user.model.entity.Users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 *  @author Ahasan Habib
 */
public interface UsersRepository extends JpaRepository<Users, Long> {
    
    public Users findByUsername(String username);

	public List<Users> findByEnabled(Boolean status);

	@Modifying
	@Query("UPDATE Users u set u.enabled = ?2 where u.id = ?1")
	public void approveIncativeUser(Long id, boolean status);
    
}
