/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *  @author Ahasan Habib
 */

@SpringBootApplication
public class ClientServerRunner {
    
    public static void main(String[] args) {
        SpringApplication.run(ClientServerRunner.class, args);
        System.out.println("Spring boot oauth client started...!");
    }
    
    
}
