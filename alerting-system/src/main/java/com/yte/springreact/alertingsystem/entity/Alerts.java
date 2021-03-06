package com.yte.springreact.alertingsystem.entity;


import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity(name = "Alerts" )
@Table(name="alerts" , schema = "public")
public class Alerts {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="name")
	@NotEmpty
	private String name;

	@Column(name="url")
	@NotEmpty
	@URL
	private String url;

	@Column(name="http_method")
	@NotEmpty
	private String http_method;

	@Column(name="period")
	@Min(value = 1000, message = "Period is formatted in ms. Please enter 1000 or multiples of 1000.")
	private int period;

	@Column(name="createdBy")
	private String createdBy;

	@OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinColumn(name = "alerts_id")
	private List<Results> results;

	public Alerts() {
		this.results = new ArrayList<Results>();

	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

	public Alerts(String name, String url, String http_method) {
		this.name = name;
		this.url = url;
		this.http_method = http_method;

		this.results = new ArrayList<Results>();
	}

	public Alerts(String name, String url, String http_method, int period) {
		this.name = name;
		this.url = url;
		this.http_method = http_method;
		this.period = period;
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





	@Override
	public String toString() {
		return "Alerts [id=" + id + ", name=" + name + ", url=" + url + ", http_method=" + http_method + ", period="
				+ period + "]";
	}


}
