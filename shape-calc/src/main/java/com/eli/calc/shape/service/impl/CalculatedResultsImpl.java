package com.eli.calc.shape.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eli.calc.shape.domain.CalculationRequest;
import com.eli.calc.shape.domain.CalculationResult;
import com.eli.calc.shape.service.CalculatedResults;

@Component
public class CalculatedResultsImpl implements CalculatedResults {

	@Override
	public void deleteAllResults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putResult(CalculationResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeResult(CalculationResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsRequest(CalculationRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CalculationResult> listResults() {
		// TODO Auto-generated method stub
		return null;
	}

}
