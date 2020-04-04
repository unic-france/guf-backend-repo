package com.unic.fr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guf.batch.data.entity.Partner;
import com.guf.batch.data.entity.Partnernetwork;

public interface PartnerNetworkRepository extends JpaRepository<Partnernetwork, Long>{

	List<Partnernetwork> findPartnernetworkByPartner(Partner partner);
}
