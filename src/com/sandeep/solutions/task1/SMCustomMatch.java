package com.sandeep.solutions.task1;

import java.util.HashSet;
import java.util.Set;
import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.game.Board3D;
import de.ovgu.dke.teaching.ml.tictactoe.game.Tournament;
import de.ovgu.dke.teaching.ml.tictactoe.player.RandomPlayer;

public class SMCustomMatch
{
	private static int numberOfMatches = 10;
	public static void main(String[] args)
	{
		analyzeCommandLineArguments(args);
		IBoard simpleBoard = new Board3D(5);
		IPlayer learningPlayer = new SolutionLearningPlayer();
		IPlayer randomPlayer = new RandomPlayer();
		Set<IPlayer> players = new HashSet<IPlayer>();
		players.add(randomPlayer);
		players.add(learningPlayer);
		Tournament smallTournament = new Tournament(simpleBoard, players, numberOfMatches);
		smallTournament.run();

	}
	
	private static void analyzeCommandLineArguments(String[] args)
	{
		if(args.length == 0)
		{
			return;
		}
		else
		{
			try
			{
				numberOfMatches = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e)
			{
				return;
			}
		}
	}
}
