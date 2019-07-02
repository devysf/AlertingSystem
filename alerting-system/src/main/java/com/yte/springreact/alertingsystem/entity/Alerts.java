package com.yte.springreact.alertingsystem.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity(name = "Alerts")
@Table(name="alerts")
public class Alerts {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;

	@Column(name="url")
	private String url;
	
	@Column(name="http_method")
	private String http_method;

	@Column(name="period")
	private int period;

	@Column(name="result")
	private String result;
	 
	 @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	 @JoinColumn(name = "alerts_id")
	 private List<Results> results;
	 
	public Alerts() {
		this.results = new ArrayList<Results>();

	}
			
	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

	public Alerts(String name, String url, String http_method, int period, String result) {
		this.name = name;
		this.url = url;
		this.http_method = http_method;
		this.period = period;
		this.result = result;
		this.results = new ArrayList<Results>();
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getHttp_method() {
		return http_method;
	}



	public void setHttp_method(String http_method) {
		this.http_method = http_method;
	}



	public int getPeriod() {
		return period;
	}



	public void setPeriod(int period) {
		this.period = period;
	}



	public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Alerts [id=" + id + ", name=" + name + ", url=" + url + ", http_method=" + http_method + ", period="
				+ period + ", result=" + result + "]";
	}
	
	
}
