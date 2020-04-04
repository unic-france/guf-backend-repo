package com.unic.fr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Refworkingday;

public interface RefworkingdaysRepository extends JpaRepository<Refworkingday, Long> {

	Refworkingday getOneByMonthAndYear(String month, String year);

}
