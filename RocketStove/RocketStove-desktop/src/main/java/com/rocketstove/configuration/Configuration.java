/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rocketstove.configuration;

/**
 *
 * @author bibek
 */
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.rocketstove")
@org.springframework.context.annotation.Configuration
public class Configuration {
    
    
}

