package com.sandeep.machine_learning;

/**
 * This class acts as linear equation which has co-efficient and variable.
 * Example : w0 + (w1 x X1) + (w2 x X2) here w0, w1, w2 are co-efficients and X1,X2,X3 are variables
 * */
public class LinearEquation 
{
	private double[] coefficientValues;
	private int[] variableValues;
	
	/**
	 * Creates an instance of linear equation
	 * @param numberOfVariables Enter the number of variable function.
	 * For example : w0 + (w1 x X1) here value is 1
	 * @return Creates linear equation with all co-efficients as 0
	 * */
	public LinearEquation(int numberOfVariables)
	{
		coefficientValues = new double[numberOfVariables + 1];
		variableValues = new int[numberOfVariables + 1];
		variableValues[0] = 1;
	}
	
	/**
	 * Creates an instance of linear equation
	 * @param numberOfVariables Enter the number of variable function.
	 * For example : w0 + (w1 x X1) here value is 1
	 * @param defaultValue This will be the value of the all the coefficients
	 * @return Creates linear equation with all co-efficients as "defaultValue"
	 * */
	public LinearEquation(int numberOfVariables, double defaultValue)
	{
		coefficientValues = new double[numberOfVariables + 1];
		for(int i = 0; i < coefficientValues.length; i++)
		{
			coefficientValues[i] = defaultValue;
		}
		variableValues = new int[numberOfVariables + 1];
		variableValues[0] = 1;
	}
	
	/**
	 * This function helps us to access individual values
	 * @param at Enter number as index value similar to arrays
	 * @return Co-efficient value at the "at" index
	 * */
	public double getCoefficientAt(int at)
	{
		if(at < 0)
		{
			return coefficientValues[0];
		}
		else if(at >= coefficientValues.length)
		{
			return coefficientValues[coefficientValues.length - 1];
		}
		else
		{
			return coefficientValues[at];
		}
	}
	
	/**
	 * This function helps us to access individual values
	 * @param at Enter number as index value similar to arrays
	 * @return Variable value at the "at" index
	 * */
	public int getVariableValueAt(int at)
	{
		if(at < 0)
		{
			return variableValues[0];
		}
		else if(at >= variableValues.length)
		{
			return variableValues[variableValues.length - 1];
		}
		else
		{
			return variableValues[at];
		}
	}
	
	/**
	 * This function helps us to set individual coefficients
	 * @param at Enter number as index value similar to arrays
	 * @param as Enter coefficient value to be assigned
	 * */
	public void setCoefficientAt(int at, double as)
	{
		if(at < 0 || at >= coefficientValues.length)
		{
			return;
		}
		
		coefficientValues[at] = as;
	}
	
	/**
	 * This function return all values of the coefficients
	 * @return Returns {@link com.sandeep.machine_learning.LinearEquation#coefficientValues}
	 * */
	public double[] getAllCoefficients()
	{
		return coefficientValues;
	}
	
	/**
	 * This function gives number of degrees of equation.
	 * @return Number of degrees of equation. Example : w0 + (w1 x X1) here value returned is 1
	 * */
	public int degreeOfEquation()
	{
		return variableValues.length - 1;
	}
	
	/**
	 * This function gives total value of the function with values which was there previously in them.
	 * @return Value of the function.
	 * */
	public double valueOfFunction()
	{
		double result = 0;
		if(variableValues.length == 0)
		{
			return result;
		}
		else
		{
			for(int i = 0; i < variableValues.length; i++)
			{
				result += (coefficientValues[i] * variableValues[i]);
			}
			return result;
		}
	}
	
	/**
	 * This function gives total value of the function using values present in variables
	 * @param allVariableValues Enter array which has variable values in them.
	 * Example : w0 + (w1 * X1) then send {X1 = 9} array
	 * @return Value of the function.
	 * */
	public double valueOfFunction(int[] allVariableValues)
	{
		double result = 0;
		if(variableValues.length == 0)
		{
			return result;
		}
		else
		{
			int j = variableValues.length;
			if(variableValues.length > allVariableValues.length)
			{
				j = allVariableValues.length;
			}
			for(int i = 1; i < j; i++)
			{
				variableValues[i] = allVariableValues[i];
			}
			
			for(int i = 0; i < variableValues.length; i++)
			{
				result += (coefficientValues[i] * variableValues[i]);
			}
			return result;
		}
	}
	
	/**
	 * This function gives total value of the function using values present in variables and coefficients
	 * @param allVariableValues Enter array which has variable values in them.
	 * Example : w0 + (w1 * X1) then send {X1 = 9} array.
	 * @param coEffValues Enter array which has co-efficient values in them.
	 * Example : w0 + (w1 * X1) then send {w0 = 0.34, w1 = 0.645} array.
	 * @return Value of the function.
	 * */
	public double valueOfFunction(int[] allVariableValues, float[] coEffValues)
	{
		double result = 0;
		if(variableValues.length == 0)
		{
			return result;
		}
		else
		{
			int j = variableValues.length;
			if(variableValues.length > allVariableValues.length)
			{
				j = allVariableValues.length;
			}
			for(int i = 1; i < j; i++)
			{
				variableValues[i] = allVariableValues[i];
			}
			
			int k = coefficientValues.length;
			if(coefficientValues.length > coEffValues.length)
			{
				k = coEffValues.length;
			}
			for(int i = 0; i < k; i++)
			{
				coefficientValues[i] = coEffValues[i];
			}
			
			for(int i = 0; i < variableValues.length; i++)
			{
				result += (coefficientValues[i] * variableValues[i]);
			}
			return result;
		}
	}
}
