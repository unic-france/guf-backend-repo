package com.unic.fr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Assignment;
import com.guf.batch.data.entity.Partner;

public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

	List<Assignment> getAssignmentListByPartner1(Partner partner1);
	
	Assignment getAssignmentByAssignmentreferencenumber (String assignmentreferencenumber);
	
}
