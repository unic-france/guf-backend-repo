package com.unic.fr.controller;

import com.unic.fr.message.response.JwtResponse;

public class ResponseAuthEntity {
	
	boolean oneTimePassword;
	
	JwtResponse jwt;
	
	public ResponseAuthEntity(String jwt, boolean oneTimePassword){
		
		this.jwt = new JwtResponse(jwt, oneTimePassword);
		this.oneTimePassword = oneTimePassword;
		
	}

}
