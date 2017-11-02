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
	public static void main(String[] args)
	{
		IBoard simpleGame = new Board3D(5);
		IPlayer myself = new SolutionLearningPlayer();
		IPlayer randomPlayer = new RandomPlayer();
		Set<IPlayer> players = new HashSet<IPlayer>();
		players.add(randomPlayer);
		players.add(myself);
		Tournament smallTournament = new Tournament(simpleGame, players, 10);
		smallTournament.run();
	}
}
