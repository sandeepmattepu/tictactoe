package com.sandeep.solutions.task1;

import com.sandeep.exceptions.NonCompatibleBoardException;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.player.SmartPlayer;

public class TestPlayer extends SmartPlayer 
{
	private DataExtractorFrom3DBoard dataExtractor;
	public int[] makeMove(IBoard board)
	{
		if(dataExtractor == null)
		{
			dataExtractor = new DataExtractorFrom3DBoard(this);
		}
		try {
			System.out.println("Favourable in x " + dataExtractor.numberOfSectionHalfFilledInX(board));
			System.out.println("Favourable in y " + dataExtractor.numberOfSectionHalfFilledInY(board));
			System.out.println("Favourable in z " + dataExtractor.numberOfSectionHalfFilledInZ(board));
		} catch (NonCompatibleBoardException e) {
			// TODO Auto-generated catc
		}
		int[] position;
		position = super.makeMove(board);
		return position;
	}
}
