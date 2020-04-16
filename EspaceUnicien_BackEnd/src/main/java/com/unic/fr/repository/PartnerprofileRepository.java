package com.unic.fr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Partnercontact;
import com.guf.batch.data.entity.Partnerprofile;

public interface PartnerprofileRepository extends JpaRepository<Partnerprofile, Long> {

	Partnerprofile getByPartnercontact(Partnercontact partnercontact);
}
