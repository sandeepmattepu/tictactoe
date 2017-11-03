package com.sandeep.exceptions;

@SuppressWarnings("serial")
public class NonCompatibleBoardException extends Exception
{
	public NonCompatibleBoardException(String message)
	{
		super(message);
	}
}
