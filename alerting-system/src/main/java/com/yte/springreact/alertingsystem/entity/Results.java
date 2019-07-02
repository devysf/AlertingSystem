package com.yte.springreact.alertingsystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Results")
@Table(name="results")
public class Results {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	
	@Column(name="alerts_id")
	private int alerts_id; 
	
	public int getAlerts_id() {
		return alerts_id;
	}

	public void setAlerts_id(int alerts_id) {
		this.alerts_id = alerts_id;
	}
	

	@Column(name="status")
	private int status;
	
	public Results() {
		
	}

	public Results(int status) {
		super();
		this.status = status;
		//this.alerts_id = alertsId;
	}
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	

	@Override
	public String toString() {
		return "Results [id=" + id + ", alerts_id=" + ", status=" + status + "]";
	} 

	
}
