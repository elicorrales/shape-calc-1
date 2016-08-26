package com.eli.calc.shape.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eli.calc.shape.config.AppConfig;
import com.eli.calc.shape.domain.CalculationRequest;
import com.eli.calc.shape.domain.CalculationResult;
import com.eli.calc.shape.model.CalcType;
import com.eli.calc.shape.model.ShapeName;
import com.eli.calc.shape.service.ShapeCalculatorService;
import com.eli.calc.shape.service.impl.ShapeCalculatorServiceImpl;

public class JUnitTest {

	private ApplicationContext ctx;
	private ShapeCalculatorService calculator;
	
	@Before
	public void setUp() throws Exception {
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		((AbstractApplicationContext)ctx).registerShutdownHook();

		calculator = ctx.getBean(ShapeCalculatorService.class);     //by the interface
		
	}

	@After
	public void tearDown() throws Exception {
		((AbstractApplicationContext)ctx).close();
	}

	@Test
	public void testQueueRequestWithNullShapeName() {
		
		try {
			double dimension = 0;
			calculator.queueCalculationRequest(null, CalcType.CALC_AREA, dimension);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("Null ShapeName should have caused an exception");
	}

	@Test
	public void testQueueRequestWithNullCalcType() {
		
		try {
			double dimension = 0;
			calculator.queueCalculationRequest(ShapeName.CIRCLE, null, dimension);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("Null CalcType should have caused an exception");
	}

	@Test
	public void testQueueRequestWithNegativeDimension() {
		
		try {
			double dimension = -0.01;
			calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("Negative dimension should have caused an exception");
	}

	@Test
	public void testQueueRequestAndRetrievePendingRequest() {

		calculator.deleteAllPendingRequests();

		double dimension = 0;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		List<CalculationRequest> requests = calculator.getAllPendingRequests();
		
		assertNotNull(requests);
		assertEquals(1,requests.size());
		
		calculator.deleteAllPendingRequests();

		requests = calculator.getAllPendingRequests();

		assertNotNull(requests);
		assertEquals(0,requests.size());
	}


	@Test
	public void testQueueRequestAndRetrievePendingMultipleSameRequests() {

		calculator.deleteAllPendingRequests();

		double dimension = 0;

		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);

		List<CalculationRequest> requests = calculator.getAllPendingRequests();
		
		assertNotNull(requests);
		assertEquals(1,requests.size());
		
		calculator.deleteAllPendingRequests();

		requests = calculator.getAllPendingRequests();

		assertNotNull(requests);
		assertEquals(0,requests.size());
	}


	@Test
	public void testQueueRequestAndRetrievePendingMultipleDifferentRequests() {

		calculator.deleteAllPendingRequests();

		double dimension = 0.000;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		dimension = 0.001;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		dimension = 0.002;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);

		dimension = 0.000;
		calculator.queueCalculationRequest(ShapeName.SQUARE, CalcType.CALC_AREA, dimension);
		dimension = 0.001;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_VOLUME, dimension);
		dimension = 0.002;
		calculator.queueCalculationRequest(ShapeName.SPHERE, CalcType.CALC_AREA, dimension);


		List<CalculationRequest> requests = calculator.getAllPendingRequests();
		
		assertNotNull(requests);
		assertEquals(6,requests.size());
		
		calculator.deleteAllPendingRequests();

		requests = calculator.getAllPendingRequests();

		assertNotNull(requests);
		assertEquals(0,requests.size());
	}


	@Test
	public void testQueueRequestAndRunNoStop() {

		calculator.deleteAllPendingRequests();

		double dimension = 0.000;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		dimension = 0.001;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
		dimension = 0.002;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);

		dimension = 0.000;
		calculator.queueCalculationRequest(ShapeName.SQUARE, CalcType.CALC_AREA, dimension);
		dimension = 0.001;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_VOLUME, dimension);
		dimension = 0.002;
		calculator.queueCalculationRequest(ShapeName.SPHERE, CalcType.CALC_AREA, dimension);


		List<CalculationRequest> requests = calculator.getAllPendingRequests();
		assertNotNull(requests);
		assertEquals(6,requests.size());
	
		int numRun = calculator.runAllPendingRequestsNoStopOnError();
		assertEquals(6,numRun);

		requests = calculator.getAllPendingRequests();
		assertNotNull(requests);
		assertEquals(0,requests.size());
		
		List<CalculationResult> results = calculator.getAllCalculationResults();
		assertNotNull(results);
		assertEquals(6,results.size());

		calculator.deleteAllResults();
		assertNotNull(results);
		assertEquals(0,results.size());
	}


	@Test
	public void testDeleteAllResults() {
		calculator.deleteAllResults();
	}

	@Test
	public void testQueueCalculationRequest() {
		double dimension = 0;
		calculator.queueCalculationRequest(ShapeName.CIRCLE, CalcType.CALC_AREA, dimension);
	}

	@Test
	public void testGetAllPendingRequests() {
		calculator.getAllPendingRequests();
	}

	@Test
	public void testGetAllCalculationResults() {
		calculator.getAllCalculationResults();
	}

	@Test
	public void testRunAllPendingRequestsStopOnError() {
		calculator.runAllPendingRequestsStopOnError();
	}

	@Test
	public void testRunAllPendingRequestsNoStopOnError() {
		calculator.runAllPendingRequestsNoStopOnError();
	}

}
