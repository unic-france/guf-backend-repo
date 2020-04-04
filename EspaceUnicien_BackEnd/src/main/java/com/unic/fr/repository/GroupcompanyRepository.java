package com.unic.fr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Groupcompany;

public interface GroupcompanyRepository extends JpaRepository<Groupcompany, Long> {
	
	public Groupcompany findByActiveTrue();

}
