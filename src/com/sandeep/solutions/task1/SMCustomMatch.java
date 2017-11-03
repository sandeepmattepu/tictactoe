package com.sandeep.solutions.task1;

import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.game.Board3D;
import de.ovgu.dke.teaching.ml.tictactoe.game.Match;
import de.ovgu.dke.teaching.ml.tictactoe.player.RandomPlayer;

public class SMCustomMatch
{
	private static int numberOfMatches = 9;
	public static void main(String[] args)
	{
		IBoard simpleBoard = new Board3D(5);
		IPlayer learningPlayer = new SolutionLearningPlayer();
		IPlayer randomPlayer = new RandomPlayer();
		Match match;
		for(int i = 1; i <= numberOfMatches; i++)
		{
			match = new Match(simpleBoard, learningPlayer, randomPlayer);
			match.play();
			simpleBoard = new Board3D(5);
		}
	}
}
