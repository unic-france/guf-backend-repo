package com.unic.fr.dto;

import java.sql.Timestamp;

public class PartnernetworkDto {

	private Integer idpartnernetwork;

	private Boolean current;

	private Timestamp date;

	private NetworkDto network;
	
	public Integer getIdpartnernetwork() {
		return idpartnernetwork;
	}

	public void setIdpartnernetwork(Integer idpartnernetwork) {
		this.idpartnernetwork = idpartnernetwork;
	}

	public Boolean getCurrent() {
		return current;
	}

	public void setCurrent(Boolean current) {
		this.current = current;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public NetworkDto getNetwork() {
		return network;
	}

	public void setNetwork(NetworkDto network) {
		this.network = network;
	}

}
