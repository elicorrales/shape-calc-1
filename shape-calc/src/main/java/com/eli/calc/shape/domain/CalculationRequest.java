package com.eli.calc.shape.domain;

import com.eli.calc.shape.model.CalcType;
import com.eli.calc.shape.model.ShapeName;

public final class CalculationRequest {

	private ShapeName shapeName;
	
	private CalcType calcType;
	
	private double dimension;
	
	public CalculationRequest(ShapeName shapeName, CalcType calcType, double dimension) {
		
		this.shapeName = shapeName;
		this.calcType = calcType;
		this.dimension = dimension;
	}

	public ShapeName getShapeName() {
		return shapeName;
	}

	public void setShapeName(ShapeName shapeName) {
		this.shapeName = shapeName;
	}

	public CalcType getCalcType() {
		return calcType;
	}

	public void setCalcType(CalcType calcType) {
		this.calcType = calcType;
	}

	public double getDimension() {
		return dimension;
	}

	public void setDimension(double dimension) {
		this.dimension = dimension;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calcType == null) ? 0 : calcType.hashCode());
		long temp;
		temp = Double.doubleToLongBits(dimension);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((shapeName == null) ? 0 : shapeName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalculationRequest other = (CalculationRequest) obj;
		if (calcType != other.calcType)
			return false;
		if (Double.doubleToLongBits(dimension) != Double.doubleToLongBits(other.dimension))
			return false;
		if (shapeName != other.shapeName)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CalculationRequest [shapeName=" + shapeName + ", calcType=" + calcType + ", dimension=" + dimension
				+ "]";
	}


}
