package com.eli.calc.shape.service;

import java.util.List;

import com.eli.calc.shape.domain.CalculationRequest;

public interface PendingRequests {

	void deleteAllRequests();
	
	List<CalculationRequest> getRequests();
	
	void putRequest(CalculationRequest request);
	
	void removeRequest(CalculationRequest request);

	long getNumRequests();

}
