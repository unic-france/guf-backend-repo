package com.unic.fr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Partnercontact;

public interface PartnercontactRepository extends JpaRepository<Partnercontact, Long> {

	Partnercontact getByEmailpartner(String emailpartner);
	
	Partnercontact getByEmailgroup(String emailgroup);
}
