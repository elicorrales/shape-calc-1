package com.eli.calc.shape.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.eli.calc.shape.ShapeCalculationsFactory;
import com.eli.calc.shape.domain.CalculationRequest;
import com.eli.calc.shape.domain.CalculationResult;
import com.eli.calc.shape.model.CalcType;
import com.eli.calc.shape.model.ShapeName;
import com.eli.calc.shape.service.CalculatedResults;
import com.eli.calc.shape.service.PendingRequests;
import com.eli.calc.shape.service.ShapeCalculatorService;

@Service
public class ShapeCalculatorServiceImpl implements ShapeCalculatorService {

	private static final Logger logger = LoggerFactory.getLogger(ShapeCalculatorServiceImpl.class);
	
	@Autowired
	private ExecutorService executor;
	
	@Autowired
	private ShapeCalculationsFactory shapeFactory;

	@Autowired
	private PendingRequests pendingRequests;
	
	@Autowired
	private CalculatedResults calculatedResults;
	
	@Override
	public void deleteAllPendingRequests() {
		pendingRequests.deleteAllRequests();
	}

	@Override
	public void deleteAllResults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void queueCalculationRequest(ShapeName shapeName, CalcType calcType, double dimension) {

		if (null==shapeName) { throw new IllegalArgumentException("ShapeName can not be null"); }
		if (null==calcType) { throw new IllegalArgumentException("CalcType can not be null"); }
		if (0>dimension) { throw new IllegalArgumentException("dimension must be zero or positive"); }
		
		CalculationRequest request = new CalculationRequest(shapeName,calcType,dimension);
		
		if (calculatedResults.containsRequest(request)) { return; }
		
		pendingRequests.putRequest(request);
	}

	@Override
	public List<CalculationRequest> getAllPendingRequests() {
		return pendingRequests.getRequests();
	}

	@Override
	public List<CalculationResult> getAllCalculationResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int runAllPendingRequestsStopOnError() {
		return runAllPendingRequests(true);
	}

	@Override
	public int runAllPendingRequestsNoStopOnError() {
		return runAllPendingRequests(false);
	}

	private int runAllPendingRequests(boolean stopOnError) {
		
		int numRun = 0;

		if (pendingRequests.getNumRequests()<1) {
			logger.warn("NO calculations run - there are no pending requests");
			numRun = 0;
			return numRun;
		}
	
		List<Future<CalculationResult>> futureResults = null;

		for (CalculationRequest cr :  pendingRequests.getRequests()) {
			CalculatorSingleTask task = new CalculatorSingleTask(shapeFactory,cr);
			Future<CalculationResult> futureResult = executor.submit(task);
			futureResults.add(futureResult);
		}

		return numRun;
	}
}
