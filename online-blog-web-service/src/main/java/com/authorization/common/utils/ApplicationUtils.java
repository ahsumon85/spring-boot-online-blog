/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.authorization.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Ahasan Habib
 */
public class ApplicationUtils {
	
	 

	public static Boolean isPasswordValid(String databasedPass, String givenPassword) {
		if (BCrypt.checkpw(databasedPass, givenPassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String convertDateToLocalDateTime(Date date) {
		try {
			/* output is 17-02-2019 05:33 PM */
			return new SimpleDateFormat("dd-MM-yyyy hh:mm aaa").format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	 
}
