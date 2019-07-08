package com.yte.springreact.alertingsystem.service;

import java.util.List;

import com.yte.springreact.alertingsystem.entity.Results;


public interface ResultsService {
	
	public List<Results> findAll();

	public Results findById(int resultId);

	public Results save(Results results);

	public void deleteById(int resultsId);

		
}

