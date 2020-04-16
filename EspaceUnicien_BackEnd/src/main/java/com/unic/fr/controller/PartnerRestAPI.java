package com.unic.fr.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guf.batch.data.entity.Partner;
import com.guf.batch.data.entity.Partnernetwork;
import com.unic.fr.dto.PartnerDto;
import com.unic.fr.dto.PartnernetworkDto;
import com.unic.fr.model.User;
import com.unic.fr.repository.PartnerNetworkRepository;
import com.unic.fr.repository.PartnerRepository;
import com.unic.fr.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PartnerRestAPI {
	
	private static final Logger logger = LoggerFactory.getLogger(PartnerRestAPI.class);
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
	PartnerRepository partnerRepository;
	
	@Autowired
	PartnerNetworkRepository partnerNetworkRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public User userAccess() {
		
		User user = null;
		
		PartnerDto partnerDto = null;
		
		List<PartnernetworkDto> partnernetworkListDto = null;
		
		try {
			
			String username =  SecurityContextHolder.getContext().getAuthentication().getName();
			
			user =  userRepository.findByUsername(username).get();
			
			Partner partner = partnerRepository.getByPuid(user.getUuid());
			
			List<Partnernetwork> partnernetworks = partnerNetworkRepository.findPartnernetworkByPartner(partner);
			
			partnernetworkListDto = modelMapper.map(partnernetworks, 
												new TypeToken<List<PartnernetworkDto>>(){}.getType());

			partnerDto = modelMapper.map(partner, PartnerDto.class);
			
			partnerDto.setPartnernetworks(partnernetworkListDto);
	
		
		}catch(Exception e) {
			
			logger.info(e.getMessage());
		}
		return user;
	}
	
	@GetMapping("/api/partner")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public PartnerDto partenerDetail() {
		
		User user = null;
		
		PartnerDto partnerDto = null;
		
		List<PartnernetworkDto> partnernetworkListDto = null;
		
		try {
			
			String username =  SecurityContextHolder.getContext().getAuthentication().getName();
			
			user =  userRepository.findByUsername(username).get();
			
			Partner partner = partnerRepository.getByPuid(user.getUuid());
			
			List<Partnernetwork> partnernetworks = partnerNetworkRepository.findPartnernetworkByPartner(partner);
			
			partnernetworkListDto = modelMapper.map(partnernetworks, 
														new TypeToken<List<PartnernetworkDto>>(){}.getType());
		
			partnerDto = modelMapper.map(partner, PartnerDto.class);
			
			partnerDto.setPartnernetworks(partnernetworkListDto);
			
		
		}catch(Exception e) {
			
			logger.info(e.getMessage());
		}
		return partnerDto;
		
	}

	@GetMapping("/api/test/pm")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public String projectManagementAccess() {
		return ">>> Board Management Project";
	}
	
	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return ">>> Admin Contents";
	}
}