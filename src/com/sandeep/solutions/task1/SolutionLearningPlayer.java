package com.sandeep.solutions.task1;

import java.util.ArrayList;
import java.util.List;

import com.sandeep.exceptions.NonCompatibleBoardException;
import com.sandeep.machine_learning.LinearEquation;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IMove;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.api.IllegalMoveException;
import de.ovgu.dke.teaching.ml.tictactoe.game.Move;

public class SolutionLearningPlayer implements IPlayer 
{
	private LinearEquation approximatedTargetFunction;
	private List<IBoard> historyOfBoards = new ArrayList<IBoard>();
	private List<int[]> allLegalMoves = new ArrayList<int[]>();
	private List<IBoard> allPossibleBoardsAfterLegalMove = new ArrayList<IBoard>();
	private double[] oldWeights = new double[7];
	
	public SolutionLearningPlayer()
	{
		super();
		approximatedTargetFunction = new LinearEquation(6, 1);
		for(int i = 0; i < oldWeights.length; i++)
		{
			oldWeights[i] = approximatedTargetFunction.getCoefficientAt(i);
		}
	}
	
	public int[] makeMove(IBoard board)
	{
		historyOfBoards.add(board.clone());
		calculateAllLegalMoves(board);
		calculateAllBoardsAfterLegalMoves(board);
		IMove bestMove = determineBestPossibleMove();
		return bestMove.getPosition();
	}
	
	public void onMatchEnds(IBoard board)
	{
		applyLMSAlgorithm(board);
		displayResults();
	}
	
	private boolean isLegalMove(int[] movePosition, IBoard boardToAnalyze)
	{
		IPlayer somePlayer = boardToAnalyze.getFieldValue(movePosition);
		if(somePlayer != null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public String getName()
	{
		return "Learning Player";
	}

	private void calculateAllLegalMoves(IBoard boardToAnalyze)
	{
		allLegalMoves.clear();
		
		int[] positionInBoard;
		for(int i = 0; i < boardToAnalyze.getSize(); i++)
		{
			for(int j = 0; j < boardToAnalyze.getSize(); j++)
			{
				for(int k = 0; k < boardToAnalyze.getSize(); k++)
				{
					positionInBoard = new int[] {i,j,k};
					if(isLegalMove(positionInBoard, boardToAnalyze))
					{
						allLegalMoves.add(positionInBoard);
					}
				}
			}
		}
	}

	private void calculateAllBoardsAfterLegalMoves(IBoard boardToAnalyze)
	{
		allPossibleBoardsAfterLegalMove.clear();
		
		for(int[] legalPosition : allLegalMoves)
		{
			IBoard nextBoardAfterMove = boardToAnalyze.clone();
			IMove moveToMake = new Move(this, legalPosition);
			try {
				nextBoardAfterMove.makeMove(	moveToMake);
				allPossibleBoardsAfterLegalMove.add(nextBoardAfterMove);
			} catch (IllegalMoveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private IMove determineBestPossibleMove()
	{
		double bestRankOfBoard = 0;
		int indexOfBestRankBoard = 0;
		for(int i = 0; i < allPossibleBoardsAfterLegalMove.size(); i++)
		{
			if(i == 0)		// First board value as best rank of board
			{
				bestRankOfBoard = valueOfBoardFromAppFunction(allPossibleBoardsAfterLegalMove.get(i));
				indexOfBestRankBoard = 0;
			}
			else
			{
				double valueOfBoard = valueOfBoardFromAppFunction(allPossibleBoardsAfterLegalMove.get(i));
				if(valueOfBoard > bestRankOfBoard)
				{
					bestRankOfBoard = valueOfBoard;
					indexOfBestRankBoard = i;
				}
			}
		}
		
		IBoard bestBoard = allPossibleBoardsAfterLegalMove.get(indexOfBestRankBoard);
		List<IMove> allMovesToAchieveBestBoard = bestBoard.getMoveHistory();
		IMove bestPossibleMove = allMovesToAchieveBestBoard.get(allMovesToAchieveBestBoard.size() - 1);
		return bestPossibleMove;
	}
	
	private double valueOfBoardFromAppFunction(IBoard boardToCalculate)
	{
		DataExtractorFrom3DBoard dataExtractor = new DataExtractorFrom3DBoard(this);
		double result = 0.0f;
		int[] variableValues = new int[21];
		try 
		{
			variableValues[1] = dataExtractor.totalNumberOfEntireFreeSectionsInBoard(boardToCalculate);
			variableValues[2] = dataExtractor.totalSectionsHalfOrMorePlayerFilledInBoard(boardToCalculate);
			variableValues[3] = dataExtractor.totalSectionsHalfOrMoreOpponentFilledInBoard(boardToCalculate);
			variableValues[4] = dataExtractor.totalNumberOfIntermixedSectionsInBoard(boardToCalculate);
			variableValues[5] = dataExtractor.totalNumberSectionsOneMoveAwayForOurWin(boardToCalculate);
			variableValues[6] = dataExtractor.totalNumberSectionsOneMoveAwayForOpponentWin(boardToCalculate);
			
			result = approximatedTargetFunction.valueOfFunction(variableValues);
		} 
		catch (NonCompatibleBoardException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private void applyLMSAlgorithm(IBoard finalStateOfBoard) 
	{
		if(finalStateOfBoard.isFinalState())
		{
			double trainingExampleScore = 0;
			float learningRate = 0.01f;
			IPlayer winner = finalStateOfBoard.getWinner();
			if(winner == null)		// Draw match
			{
				trainingExampleScore = 0;
			}
			else if(winner == this)		// Won
			{
				trainingExampleScore = 100;
			}
			else							// Lost
			{
				trainingExampleScore = -100;
			}
			
			for(int i = 0; i < historyOfBoards.size(); i++)
			{
				adjustWeightsOfApproxTargetFunction(trainingExampleScore, historyOfBoards.get(i),
						learningRate);
				trainingExampleScore = valueOfBoardFromAppFunction(historyOfBoards.get(i));		// For training 
				// next board
			}
		}
	}
	
	private void adjustWeightsOfApproxTargetFunction(double trainingExampleScore, IBoard boardToAnalyze,
			float learningRate)
	{
		double scoreOfBoardFromEquation = valueOfBoardFromAppFunction(boardToAnalyze);
		for(int i = 0; i <= approximatedTargetFunction.degreeOfEquation(); i++)
		{
			double newWeight = approximatedTargetFunction.getCoefficientAt(i) + (learningRate * (trainingExampleScore
					- scoreOfBoardFromEquation) * approximatedTargetFunction.getVariableValueAt(i));
			approximatedTargetFunction.setCoefficientAt(i, newWeight);
		}
	}
	
	private void displayResults()
	{
		System.out.println("Old Values:");
		for(int i = 0; i < oldWeights.length; i++)
		{
			System.out.println("w" + i + " : " + oldWeights[i]);
		}
		System.out.println();
		System.out.println("New Values:");
		for(int i = 0; i < (approximatedTargetFunction.degreeOfEquation() + 1); i++)
		{
			System.out.println("w" + i + " : " + approximatedTargetFunction.getCoefficientAt(i));
		}
	}
}
