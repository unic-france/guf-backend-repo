package com.unic.fr.repository.implementation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.guf.batch.data.entity.Assignment;
import com.guf.batch.data.entity.Partner;
import com.guf.batch.data.entity.Partnercontact;
import com.guf.batch.data.entity.Partnerprofile;
import com.unic.fr.exception.AssignmentNotFoundException;
import com.unic.fr.repository.PartnerRepository;
import com.unic.fr.repository.PartnercontactRepository;
import com.unic.fr.repository.PartnerprofileRepository;

public class PartnerImplementation implements PartnerRepository {

	@PersistenceContext
	EntityManager em;
	
    @Autowired
    PartnercontactRepository partnercontactRepository;
    
    @Autowired
    PartnerprofileRepository partnerprofileRepository;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getListAssignmentByIdpartner(Integer idpartner) throws AssignmentNotFoundException{
		
		List<Assignment> assignList = null;
		
		Query query = em.createQuery("SELECT a FROM ASSIGNMENT a WHERE a.idpartner = :idpartner ", Assignment.class);
		query.setParameter(1, idpartner);
		
		try {
			assignList = query.getResultList();
		
		}catch(Exception e) {
			
			throw new AssignmentNotFoundException(e.getMessage());
			
		}
		return assignList;
	}
	
	@Override
	public Partner getPartnerByPartnerprofilePartnercontactEmailpartner(String emailpartner) {
		
		Partnercontact partnercontact = partnercontactRepository.getByEmailpartner(emailpartner);
		
		System.out.println("partner contact "+partnercontact.getEmailpartner());
		
		Partnerprofile partnerprofile = null;
		
		Partner partner = null;
    	
    	if (null != partnercontact) {
    	
    		partnerprofile = partnerprofileRepository.getByPartnercontact(partnercontact);
    		
    		System.out.println("partner profile "+partnerprofile.getIdpartnerprofile());
    		
    		int idpartner = partnerprofile.getIdpartnerprofile();
    		
    		Query query = em.createQuery("SELECT p FROM PARTNER p WHERE p.partnerprofile = :idpartnerprofile ", Partner.class);
    		query.setParameter(1, idpartner);
    		
    		try {
    			partner = (Partner) query.getSingleResult();
    		
    		}catch(Exception e) {
    			
    			System.out.println("Partner not found");
    			
    		}
    	}
    	
		return partner;
	}
	
	@Override
	public List<Partner> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Partner> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Partner> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Partner> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Partner> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteInBatch(Iterable<Partner> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public Partner getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Partner> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Partner> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Partner> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Partner> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Partner> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Partner entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Partner> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Partner> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Partner> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Partner> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Partner> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Partner getByPuid(String Uuid) {
		// TODO Auto-generated method stub
		return null;
	}



}
