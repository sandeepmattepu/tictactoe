package com.sandeep.solutions.task1;

import java.util.ArrayList;
import java.util.List;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IMove;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.api.IllegalMoveException;
import de.ovgu.dke.teaching.ml.tictactoe.game.Move;
import de.ovgu.dke.teaching.ml.tictactoe.player.RandomPlayer;

public class TestPlayer extends RandomPlayer 
{
	private LinearEquation approximatedTargetFunction = new LinearEquation(21, 1);
	private List<IBoard> historyOfBoards = new ArrayList<IBoard>();
	private List<int[]> allLegalMoves = new ArrayList<int[]>();
	private List<IBoard> allPossibleBoardsAfterLegalMove = new ArrayList<IBoard>();
	
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
		float bestRankOfBoard = 0;
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
				float valueOfBoard = valueOfBoardFromAppFunction(allPossibleBoardsAfterLegalMove.get(i));
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
	
	private float valueOfBoardFromAppFunction(IBoard boardToCalculate)
	{
		DataExtractorFrom3DBoard dataExtractor = new DataExtractorFrom3DBoard(this);
		float result = 0.0f;
		int[] variableValues = new int[21];
		try 
		{
			variableValues[0] = dataExtractor.numberOfFreeEntireSectionInX(boardToCalculate);
			variableValues[1] = dataExtractor.numberOfFreeEntireSectionInY(boardToCalculate);
			variableValues[2] = dataExtractor.numberOfFreeEntireSectionInZ(boardToCalculate);
			variableValues[3] = dataExtractor.numberOfEntireDiagonalSectionFreeInXY(boardToCalculate);
			variableValues[4] = dataExtractor.numberOfEntireDiagonalSectionFreeInYZ(boardToCalculate);
			variableValues[5] = dataExtractor.numberOfEntireDiagonalSectionFreeInXZ(boardToCalculate);
			variableValues[6] = dataExtractor.numberOfEntireDiagonalSectionFreeInXYZ(boardToCalculate);
			variableValues[7] = dataExtractor.numberOfSectionHalfFilledInX(boardToCalculate);
			variableValues[8] = dataExtractor.numberOfSectionHalfFilledInY(boardToCalculate);
			variableValues[9] = dataExtractor.numberOfSectionHalfFilledInZ(boardToCalculate);
			variableValues[10] = dataExtractor.numberOfDiagonalsOurFilledMoreThanHalfInXY(boardToCalculate);
			variableValues[11] = dataExtractor.numberOfDiagonalsOurFilledMoreThanHalfInYZ(boardToCalculate);
			variableValues[12] = dataExtractor.numberOfDiagonalsOurFilledMoreThanHalfInXZ(boardToCalculate);
			variableValues[13] = dataExtractor.numberOfDiagonalsOurFilledMoreThanHalfInXYZ(boardToCalculate);
			variableValues[14] = dataExtractor.numberOfSectionHalfFilledInXByOpponent(boardToCalculate);
			variableValues[15] = dataExtractor.numberOfSectionHalfFilledInYByOpponent(boardToCalculate);
			variableValues[16] = dataExtractor.numberOfSectionHalfFilledInZByOpponent(boardToCalculate);
			variableValues[17] = dataExtractor.numberOfDiagonalsOpponentFilledMoreThanHalfInXY(boardToCalculate);
			variableValues[18] = dataExtractor.numberOfDiagonalsOpponentFilledMoreThanHalfInYZ(boardToCalculate);
			variableValues[19] = dataExtractor.numberOfDiagonalsOpponentFilledMoreThanHalfInXZ(boardToCalculate);
			variableValues[20] = dataExtractor.numberOfDiagonalsOpponentFilledMoreThanHalfInXYZ(boardToCalculate);
			
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
			float trainingExampleScore = 0;
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
	
	private void adjustWeightsOfApproxTargetFunction(float trainingExampleScore, IBoard boardToAnalyze,
			float learningRate)
	{
		float scoreOfBoardFromEquation = valueOfBoardFromAppFunction(boardToAnalyze);
		for(int i = 0; i <= approximatedTargetFunction.degreeOfEquation(); i++)
		{
			float newWeight = approximatedTargetFunction.getCoefficientAt(i) + (learningRate * (trainingExampleScore
					- scoreOfBoardFromEquation) * approximatedTargetFunction.getVariableValueAt(i));
			approximatedTargetFunction.setCoefficientAt(i, newWeight);
		}
	}
}
