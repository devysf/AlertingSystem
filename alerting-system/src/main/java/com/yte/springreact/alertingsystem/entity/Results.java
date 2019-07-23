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
import java.util.Calendar;
import java.util.Date;
@Entity(name = "Results")
@Table(name="results" , schema = "public")
public class Results {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="status")
	private int status;

	@Column(name="date")
	private String date;

	public Results() {
		
	}

	public Results(int status) {
		super();
		this.status = status;
		//this.alerts_id = alertsId;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
