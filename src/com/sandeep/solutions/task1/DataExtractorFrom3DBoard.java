package com.sandeep.solutions.task1;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;

/**
 * Use this class to extract data about the board for a particular player. Data that can be extracted are <br />
 * 1) Number of free entire sections in x direction <br />
 * 2) Number of free entire sections in y direction <br />
 * 3) Number of free entire sections in z direction <br />
 * 4) Number of free entire diagonal sections in xy direction <br />
 * 5) Number of free entire diagonal sections in yz direction <br />
 * 6) Number of free entire diagonal sections in xz direction <br />
 * 7) Number of free entire diagonal sections in xyz direction <br />
 * 8) Number of sections which are filled with our player's more than
 *  or equal to half in x direction <br />
 * 9) Number of sections which are filled with our player's more than
 *  or equal to half in y direction <br />
 * 10) Number of sections which are filled with our player's more than
 *  or equal to half in z direction <br />
 *  11) Number of diagonal sections which are filled by our player's more than or equal to half in xy direction <br />
 *  12) Number of diagonal sections which are filled by our player's more than or equal to half in yz direction <br />
 *  13) Number of diagonal sections which are filled by our player's more than or equal to half in xz direction <br />
 *  14) Number of diagonal sections which are filled by our player's more than or equal to half in xyz direction <br />
 *  15) Number of sections which are half or more filled by opponent in X direction <br />
 *  16) Number of sections which are half or more filled by opponent in Y direction <br />
 *  17) Number of sections which are half or more filled by opponent in Z directions <br />
 *  18) Number of diagonals which are half or more by opponent in XY direction <br />
 * */
public class DataExtractorFrom3DBoard
{
	private IPlayer player;
	private NonCompatibleBoardException exception = 
			new NonCompatibleBoardException("Incompatible board. Cannot extract data from it");
	
	/**
	 * Creates an instance of DataExtractorFrom5x5x5
	 * @param ourPlayer Enter player of interest
	 * @return DataExtractorFrom5x5x5 instance which we can calculate data with "ourPlayer" as reference
	 * */
	public DataExtractorFrom3DBoard(IPlayer ourPlayer)
	{
		player = ourPlayer;
	}
	
	/**
	 * @param dimension 1 for x, 2 for y, 3 for z 
	 * */
	private int numberOfFreeEntireSectionInOneDirection(IBoard board, int dimension)
	{
		int[] somePos;
		int result = 0;
		IPlayer somePlayer;
		for(int i = 0; i < board.getSize(); i++)
		{
			for(int j = 0; j < board.getSize(); j++)
			{
				for(int k = 0; k < board.getSize(); k++)
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
					else if(k == (board.getSize() - 1))
					{
						result++;
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * @param exceptDimension 1 for diagonals in yz direction <br />
	 * 2 for diagonals in xz direction <br />
	 * 3 for diagonals in xy direction 
	 * */
	private int numberOfFreeEntireSectionInTwoDirection(IBoard board, int exceptDimension)	// Diagonally in 2d
	{
		int result = 0;
		int[] somePos;
		int j = 0;
		int k = 0;
		IPlayer somePlayer;
		for(int i = 0; i < board.getSize(); i++)		// Logic for one diagonal
		{
			for(j = 0, k = 0; j < board.getSize(); j++, k++)
			{
				if(exceptDimension == 1)		// Diagonal in yz direction
				{
					somePos = new int[] {i,j,k};
				}
				else if(exceptDimension == 2)		// Diagonal in xz direction
				{
					somePos = new int[] {j,i,k};
				}
				else if(exceptDimension == 3)		// Diagonal in xy direction
				{
					somePos = new int[] {j,k,i};
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
				else if(j == (board.getSize() - 1))
				{
					result++;
				}
			}
		}
		
		j = (board.getSize() - 1);
		k = 0;
		for(int i = 0; i < board.getSize(); i++)		// Logic for second diagonal
		{
			for(j = (board.getSize() - 1), k = 0; k < board.getSize(); j--, k++)
			{
				if(exceptDimension == 1)		// Diagonal in yz direction
				{
					somePos = new int[] {i,j,k};
				}
				else if(exceptDimension == 2)		// Diagonal in xz direction
				{
					somePos = new int[] {j,i,k};
				}
				else if(exceptDimension == 3)		// Diagonal in xy direction
				{
					somePos = new int[] {j,k,i};
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
				else if(k == (board.getSize() - 1))
				{
					result++;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * This function gives data about number of diagonally free sections in XYZ direction
	 * @param board Enter the board to analyze
	 * @return Number of free diagonal sections available in XYZ direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfEntireDiagonalSectionFreeInXYZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			int result = 0;
			int[] somePos;
			IPlayer somePlayer;
			for(int i=0, j=0, k=0; i < board.getSize(); i++, j++, k++)		// For first diagonal
			{
				somePos = new int[] {i,j,k};
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					break;
				}
				else if( i == (board.getSize() - 1))
				{
					result++;
				}
			}
			
			for(int i=(board.getSize() - 1),j=0,k=(board.getSize() - 1); j < board.getSize(); i--, j++, k--)		// For second diagonal
			{
				somePos = new int[] {i,j,k};
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					break;
				}
				else if(j == (board.getSize() - 1))
				{
					result++;
				}
			}

			return result;
		}
	}
	
	/**
	 * This function gives data about number of entire free sections in X direction
	 * @param board Enter the board to analyze
	 * @return Number of free entire sections available in X direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfFreeEntireSectionInX(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSectionInOneDirection(board,1);
		}
	}
	
	/**
	 * This function gives data about number of entire free sections in Y direction
	 * @param board Enter the board to analyze
	 * @return Number of free entire sections available in Y direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfFreeEntireSectionInY(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSectionInOneDirection(board,2);
		}
	}
	
	/**
	 * This function gives data about number of entire free sections in Z direction
	 * @param board Enter the board to analyze
	 * @return Number of free entire sections available in Z direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfFreeEntireSectionInZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSectionInOneDirection(board,3);
		}
	}
	
	/**
	 * This function gives data about number of diagonally free sections in XY direction
	 * @param board Enter the board to analyze
	 * @return Number of free diagonal sections available in XY direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfEntireDiagonalSectionFreeInXY(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSectionInTwoDirection(board,3);
		}
	}
	
	/**
	 * This function gives data about number of diagonally free sections in YZ direction
	 * @param board Enter the board to analyze
	 * @return Number of free diagonal sections available in YZ direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfEntireDiagonalSectionFreeInYZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSectionInTwoDirection(board,1);
		}
	}
	
	/**
	 * This function gives data about number of diagonally free sections in XZ direction
	 * @param board Enter the board to analyze
	 * @return Number of free diagonal sections available in XZ direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfEntireDiagonalSectionFreeInXZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfFreeEntireSectionInTwoDirection(board,2);
		}
	}
	
	/**
	 * @param dimension Enter 1 for x, 2 for y, 3 for z
	 * */
	private int numberOfSectionsMoreThanHalfFilledInOneDirection(IBoard board, int dimension)
	{
		int result = 0;
		int[] somePos;
		IPlayer somePlayer;
		for(int i = 0; i<board.getSize(); i++)
		{
			for(int j = 0; j < board.getSize(); j++)
			{
				int assignedSpaces = 0;
				for(int k = 0; k < board.getSize(); k++)
				{
					if(dimension == 1)
					{
						somePos = new int[] {k,i,j};
					}
					else if(dimension == 2)
					{
						somePos = new int[] {i,k,j};
					}
					else if(dimension == 3)
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
						if(somePlayer != player)
						{
							break;		// That section is already filled with some of opponent places
						}
						else
						{
							assignedSpaces ++;
						}
					}
					
					if(assignedSpaces != 0)		// At least one of our player's position
						//should be there in section
					{
						int halfOfSpaces = board.getSize() / 2;
						if(assignedSpaces >= halfOfSpaces && (k == (board.getSize() - 1)))
						{
							result++;
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * This function gives data about number of sections that are filled more than or equal to half
	 * by only our player in X direction. 
	 * For example: If we are player 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half
	 * by only our player in X direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfSectionHalfFilledInX(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfSectionsMoreThanHalfFilledInOneDirection(board,1);
		}
	}
	
	/**
	 * This function gives data about number of sections that are filled more than or equal to half
	 * by only our player in Y direction. 
	 * For example: If we are player 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half
	 * by only our player in Y direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfSectionHalfFilledInY(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfSectionsMoreThanHalfFilledInOneDirection(board,2);
		}
	}
	
	/**
	 * This function gives data about number of sections that are filled more than or equal to half
	 * by only our player in Z direction. 
	 * For example: If we are player 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half
	 * by only our player in Z direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfSectionHalfFilledInZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfSectionsMoreThanHalfFilledInOneDirection(board,3);
		}
	}
	
	/**
	 * @param exceptDimension 1 for diagonals in yz direction <br />
	 * 2 for diagonals in xz direction <br />
	 * 3 for diagonals in xy direction <br />
	 * */
	private int diagonalsFilledMoreHalfByOurPlayerInTwoDirection(IBoard board, int exceptDimension)
	{
		int result = 0;
		int[] somePos;
		int j = 0;
		int k = 0;
		IPlayer somePlayer;
		for(int i = 0; i < board.getSize(); i++)		// Logic for one diagonal
		{
			int assignedSpaces = 0;
			for(j = 0, k = 0; j < board.getSize(); j++, k++)
			{
				if(exceptDimension == 1)		// Diagonal in yz direction
				{
					somePos = new int[] {i,j,k};
				}
				else if(exceptDimension == 2)		// Diagonal in xz direction
				{
					somePos = new int[] {j,i,k};
				}
				else if(exceptDimension == 3)		// Diagonal in xy direction
				{
					somePos = new int[] {j,k,i};
				}
				else
				{
					return 0;
				}
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					if(somePlayer != player)
					{
						break;
					}
					else
					{
						assignedSpaces ++;
					}
				}
				
				if(j == (board.getSize() - 1))
				{
					int halfSpaces = board.getSize()/2;
					if(assignedSpaces >= halfSpaces)
					{
						result ++;
					}
				}
			}
		}
		
		j = (board.getSize() - 1);
		k = 0;
		for(int i = 0; i < board.getSize(); i++)		// Logic for second diagonal
		{
			int assignedSpaces = 0;
			for(j = (board.getSize() - 1), k = 0; k < board.getSize(); j--, k++)
			{
				if(exceptDimension == 1)		// Diagonal in yz direction
				{
					somePos = new int[] {i,j,k};
				}
				else if(exceptDimension == 2)		// Diagonal in xz direction
				{
					somePos = new int[] {j,i,k};
				}
				else if(exceptDimension == 3)		// Diagonal in xy direction
				{
					somePos = new int[] {j,k,i};
				}
				else
				{
					return 0;
				}
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					if(somePlayer != player)
					{
						break;
					}
					else
					{
						assignedSpaces ++;
					}
				}
				
				if(k == (board.getSize() - 1))
				{
					int half = board.getSize() / 2;
					if(assignedSpaces >= half)
					{
						result++;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * This function gives data about number of diagonals that filled with our player's with more than half
	 * in XY direction.<br />
	 * For example: If we are player 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half diagonally in XY
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfDiagonalsOurFilledMoreThanHalfInXY(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return diagonalsFilledMoreHalfByOurPlayerInTwoDirection(board, 3);
		}
	}
	
	/**
	 * This function gives data about number of diagonals that filled with our player's with more than half
	 * in YZ direction.<br />
	 * For example: If we are player 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half diagonally in YZ
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfDiagonalsOurFilledMoreThanHalfInYZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return diagonalsFilledMoreHalfByOurPlayerInTwoDirection(board, 1);
		}
	}
	
	/**
	 * This function gives data about number of diagonals that filled with our player's with more than half
	 * in XZ direction.<br />
	 * For example: If we are player 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half diagonally in XZ
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfDiagonalsOurFilledMoreThanHalfInXZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return diagonalsFilledMoreHalfByOurPlayerInTwoDirection(board, 2);
		}
	}
	
	/**
	 * This function gives data about number of diagonals that filled with our player's with more than half
	 * in XYZ direction.<br />
	 * For example: If we are player 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half diagonally in XYZ
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfDiagonalsOurFilledMoreThanHalfInXYZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			int result = 0;
			int[] somePos;
			IPlayer somePlayer;
			int assignedSpaces = 0;
			for(int i=0, j=0, k=0; i < board.getSize(); i++, j++, k++)		// For first diagonal
			{
				somePos = new int[] {i,j,k};
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					if(somePlayer != player)
					{
						break;
					}
					else
					{
						assignedSpaces ++;
					}
				}
				
				if(i == (board.getSize() - 1))
				{
					int half = board.getSize()/2;
					if(assignedSpaces >= half)
					{
						result++;
					}
				}
			}
			assignedSpaces = 0;
			for(int i=(board.getSize() - 1),j=0,k=(board.getSize() - 1); j < board.getSize(); i--, j++, k--)		// For second diagonal
			{
				somePos = new int[] {i,j,k};
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					if(somePlayer != player)
					{
						break;
					}
					else
					{
						assignedSpaces ++;
					}
				}
				
				if(j == (board.getSize() - 1))
				{
					int half = board.getSize()/2;
					if(assignedSpaces >= half)
					{
						result++;
					}
				}
			}
			
			return result;
		}
	}
	
	/**
	 * @param dimension Enter 1 for x, 2 for y, 3 for z
	 * */
	private int numberOfSectionsMoreThanHalfFilledByEnemyInOneDirection(IBoard board, int dimension)
	{
		int result = 0;
		int[] somePos;
		IPlayer somePlayer;
		for(int i = 0; i<board.getSize(); i++)
		{
			for(int j = 0; j < board.getSize(); j++)
			{
				int assignedSpaces = 0;
				for(int k = 0; k < board.getSize(); k++)
				{
					if(dimension == 1)
					{
						somePos = new int[] {k,i,j};
					}
					else if(dimension == 2)
					{
						somePos = new int[] {i,k,j};
					}
					else if(dimension == 3)
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
						if(somePlayer == player)
						{
							break;		// That section is already filled with some of our player's places
						}
						else
						{
							assignedSpaces ++;
						}
					}
					
					if(assignedSpaces != 0)		// At least one of our opponent's position
						//should be there in section
					{
						int halfOfSpaces = board.getSize() / 2;
						if(assignedSpaces >= halfOfSpaces && (k == (board.getSize() - 1)))
						{
							result++;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * This function gives data about number of sections that are filled more than or equal to half
	 * by only opponent's in X direction. 
	 * For example: If opponent is 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half
	 * by only opponent in X direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfSectionHalfFilledInXByOpponent(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfSectionsMoreThanHalfFilledByEnemyInOneDirection(board,1);
		}
	}
	
	/**
	 * This function gives data about number of sections that are filled more than or equal to half
	 * by only opponent's in Y direction. 
	 * For example: If opponent is 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half
	 * by only opponent in Y direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfSectionHalfFilledInYByOpponent(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfSectionsMoreThanHalfFilledByEnemyInOneDirection(board,2);
		}
	}
	
	/**
	 * This function gives data about number of sections that are filled more than or equal to half
	 * by only opponent's in Z direction. 
	 * For example: If opponent is 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half
	 * by only opponent in Z direction
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfSectionHalfFilledInZByOpponent(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return numberOfSectionsMoreThanHalfFilledByEnemyInOneDirection(board,3);
		}
	}
	
	/**
	 * @param exceptDimension 1 for diagonals in yz direction <br />
	 * 2 for diagonals in xz direction <br />
	 * 3 for diagonals in xy direction <br />
	 * */
	private int diagonalsFilledMoreHalfByOpponentInTwoDirection(IBoard board, int exceptDimension)
	{
		int result = 0;
		int[] somePos;
		int j = 0;
		int k = 0;
		IPlayer somePlayer;
		for(int i = 0; i < board.getSize(); i++)		// Logic for one diagonal
		{
			int assignedSpaces = 0;
			for(j = 0, k = 0; j < board.getSize(); j++, k++)
			{
				if(exceptDimension == 1)		// Diagonal in yz direction
				{
					somePos = new int[] {i,j,k};
				}
				else if(exceptDimension == 2)		// Diagonal in xz direction
				{
					somePos = new int[] {j,i,k};
				}
				else if(exceptDimension == 3)		// Diagonal in xy direction
				{
					somePos = new int[] {j,k,i};
				}
				else
				{
					return 0;
				}
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					if(somePlayer == player)
					{
						break;
					}
					else
					{
						assignedSpaces ++;
					}
				}
				
				if(j == (board.getSize() - 1))
				{
					int halfSpaces = board.getSize()/2;
					if(assignedSpaces >= halfSpaces)
					{
						result ++;
					}
				}
			}
		}
		
		j = (board.getSize() - 1);
		k = 0;
		for(int i = 0; i < board.getSize(); i++)		// Logic for second diagonal
		{
			int assignedSpaces = 0;
			for(j = (board.getSize() - 1), k = 0; k < board.getSize(); j--, k++)
			{
				if(exceptDimension == 1)		// Diagonal in yz direction
				{
					somePos = new int[] {i,j,k};
				}
				else if(exceptDimension == 2)		// Diagonal in xz direction
				{
					somePos = new int[] {j,i,k};
				}
				else if(exceptDimension == 3)		// Diagonal in xy direction
				{
					somePos = new int[] {j,k,i};
				}
				else
				{
					return 0;
				}
				somePlayer = board.getFieldValue(somePos);
				if(somePlayer != null)
				{
					if(somePlayer == player)
					{
						break;
					}
					else
					{
						assignedSpaces ++;
					}
				}
				
				if(k == (board.getSize() - 1))
				{
					int half = board.getSize() / 2;
					if(assignedSpaces >= half)
					{
						result++;
					}
				}
			}
		}
		return result;
	}

	/**
	 * This function gives data about number of diagonals that filled with opponent's with more than half
	 * in XY direction.<br />
	 * For example: If opponent is 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half diagonally in XY
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfDiagonalsOpponentFilledMoreThanHalfInXY(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return diagonalsFilledMoreHalfByOpponentInTwoDirection(board, 3);
		}
	}
	
	/**
	 * This function gives data about number of diagonals that filled with opponent's with more than half
	 * in YZ direction.<br />
	 * For example: If opponent is 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half diagonally in YZ
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfDiagonalsOpponentFilledMoreThanHalfInYZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return diagonalsFilledMoreHalfByOpponentInTwoDirection(board, 1);
		}
	}
	
	/**
	 * This function gives data about number of diagonals that filled with opponent's with more than half
	 * in XZ direction.<br />
	 * For example: If opponent is 1 then |1|1| | will be counted but not |1|1|2|. Also | | | | is also 
	 * not counted
	 * @param board Enter the board to analyze
	 * @return Number of sections that are filled more than or equal to half diagonally in XZ
	 * @throws NonCompatibleBoardException When the board is not 3d
	 * */
	public int numberOfDiagonalsOpponentFilledMoreThanHalfInXZ(IBoard board) throws NonCompatibleBoardException
	{
		if(board.getDimensions() != 3)
		{
			throw exception;
		}
		else
		{
			return diagonalsFilledMoreHalfByOpponentInTwoDirection(board, 2);
		}
	}
}
