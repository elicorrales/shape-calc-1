package com.eli.calc.shape.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.eli.calc.shape.domain.CalculationRequest;
import com.eli.calc.shape.service.PendingRequests;

@Component
public class PendingRequestsImpl implements PendingRequests {

	private final Set<CalculationRequest> requests = new HashSet<CalculationRequest>();

	@Override
	public List<CalculationRequest> getRequests() {
		
		return new ArrayList<CalculationRequest>(requests);
	}

	@Override
	public void putRequest(CalculationRequest request) {
		requests.add(request);
	}

	@Override
	public void removeRequest(CalculationRequest request) {
		requests.remove(request);
	}

	@Override
	public long getNumRequests() {
		return requests.size();
	}

	@Override
	public void deleteAllRequests() {
		requests.clear();
	}

}
