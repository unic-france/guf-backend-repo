package com.unic.fr.controller.finances;

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

import com.guf.batch.data.entity.Assignment;
import com.guf.batch.data.entity.Partner;
import com.unic.fr.dto.AssignmentDto;
import com.unic.fr.repository.AssignmentRepository;
import com.unic.fr.repository.PartnerRepository;
import com.unic.fr.security.services.UserPrinciple;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AssignmentRestApi {
	
	private static final Logger logger = LoggerFactory.getLogger(AssignmentRestApi.class);
	
	@Autowired
	PartnerRepository partnerRepository;
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@GetMapping("/api/assignementList/")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<AssignmentDto> getAssignmentList() {
		
		List<AssignmentDto> assignmentDtoList = null;
		
		try {
			
			UserPrinciple user =  (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Partner partner = partnerRepository.getByPuid(user.getUuid());
			
			List<Assignment> assignmentList= assignmentRepository.getAssignmentListByPartner1(partner);
			
			//Permet de récupérer une liste de DTO directement sans avoir à boucler sur chaque élément
			assignmentDtoList = modelMapper.map(assignmentList, new TypeToken<List<AssignmentDto>>(){}.getType());
		
		} catch(Exception e) {
			
			logger.info(e.getMessage());
			
		}
		return assignmentDtoList;

	}
	
}
