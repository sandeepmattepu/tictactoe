package com.sandeep.solutions.task1;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.player.KeyboardPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.player.SmartPlayer;

public class TestPlayer extends KeyboardPlayer 
{
	private DataExtractorFrom3DBoard dataExtractor;
	public int[] makeMove(IBoard board)
	{
		if(dataExtractor == null)
		{
			dataExtractor = new DataExtractorFrom3DBoard(this);
		}
		try {
			System.out.println("Favourable enemy xy diagonal " + dataExtractor.numberOfDiagonalsOpponentFilledMoreThanHalfInXY(board));
			System.out.println("Favourible enemy xz diagonal " + dataExtractor.numberOfDiagonalsOpponentFilledMoreThanHalfInXZ(board));
			System.out.println("Fouvarable in yz enemy " +dataExtractor.numberOfDiagonalsOpponentFilledMoreThanHalfInYZ(board));
		} catch (NonCompatibleBoardException e) {
			// TODO Auto-generated catc
		}
		int[] position;
		position = super.makeMove(board);
		return position;
	}
}
