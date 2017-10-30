package com.sandeep.solutions.task1;

import com.sandeep.exceptions.NonCompatibleBoardException;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;

/**
 * Use this class to extract data about the board for a particular player. Data that can be extracted are <br />
 * 1) Number of free entire sections in x direction <br />
 * 2) Number of free entire sections in y direction <br />
 * 3) Number of free entire sections in z direction <br />
 * 4) 
 * */
public class DataExtractorFrom5x5x5
{
	private IPlayer player;
	private NonCompatibleBoardException exception = 
			new NonCompatibleBoardException("Incompatible board. Cannot extract data from it");
	
	/**
	 * Creates an instance of DataExtractorFrom5x5x5
	 * @param ourPlayer Enter player of interest
	 * @return DataExtractorFrom5x5x5 instance which we can calculate data with "ourPlayer" as reference
	 * */
	public DataExtractorFrom5x5x5(IPlayer ourPlayer)
	{
		player = ourPlayer;
	}
	
	/**
	 * @param dimension 1 for x, 2 for y, 3 for z 
	 * */
	private int numberOfFreeEntireSection(IBoard board, int dimension)
	{
		int[] somePos;
		int result = 0;
		IPlayer somePlayer;
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				for(int k = 0; k < 5; k++)
				{
					if(dimension == 1)		// in x dimension
					{
						somePos = new int[]{k,i,j};
					}
					else if(dimension == 2)		// in y dimension
					{
						somePos = new int[] {i,k,j};
					}
					else if(dimension == 3)		// in z direction
					{
						somePos = new int[] {i,j,k};
					}
					else
					{
						return 0;
					}
					somePlayer = board.getFieldValue(somePos);
					if(somePlayer != null)
					{
						break;
					}
					else if(k == 4)
					{
						result++;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * This function gives data about number of entire free sections in X direction
	 * @param board Enter the board to analyze
	 * @return Number of free entire sections available in X direction
	 * @throws NonCompatibleBoardException When other board whose dimension is not
	 *  3 and size is not 5 will make function throw exception
	 * */
	public int numberOfFreeEntireSectionInX(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getSize() != 5)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSection(board,1);
		}
	}
	
	/**
	 * This function gives data about number of entire free sections in Y direction
	 * @param board Enter the board to analyze
	 * @return Number of free entire sections available in Y direction
	 * @throws NonCompatibleBoardException When other board whose dimension is not
	 *  3 and size is not 5 will make function throw exception
	 * */
	public int numberOfFreeEntireSectionInY(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getSize() != 5)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSection(board,2);
		}
	}
	
	/**
	 * This function gives data about number of entire free sections in Z direction
	 * @param board Enter the board to analyze
	 * @return Number of free entire sections available in Z direction
	 * @throws NonCompatibleBoardException When other board whose dimension is not
	 *  3 and size is not 5 will make function throw exception
	 * */
	public int numberOfFreeEntireSectionInZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getSize() != 5)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSection(board,3);
		}
	}
}
