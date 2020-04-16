package com.unic.fr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Assignment;
import com.guf.batch.data.entity.Partner;
import com.unic.fr.exception.AssignmentNotFoundException;

public interface PartnerRepository extends JpaRepository<Partner, Long>{
	
	Partner getOne(Long Uuid);
	Partner getByPuid(String Uuid);
	
	List<Assignment> getListAssignmentByIdpartner(Integer idpartner) throws AssignmentNotFoundException;
	
	Partner getPartnerByPartnerprofilePartnercontactEmailpartner(String emailpartner);


}
