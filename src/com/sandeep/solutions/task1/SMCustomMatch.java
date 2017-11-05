package com.sandeep.solutions.task1;

import java.util.HashSet;
import java.util.Set;
import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.game.Board3D;
import de.ovgu.dke.teaching.ml.tictactoe.game.Match;
import de.ovgu.dke.teaching.ml.tictactoe.game.Tournament;
import de.ovgu.dke.teaching.ml.tictactoe.player.RandomPlayer;

public class SMCustomMatch
{
	private static int numberOfMatches = 10;
	private static IBoard board;
	private static Match match;
	private static IPlayer learningPlayer;
	private static IPlayer randomPlayer;
	private static int numberOfDraws = 0;
	private static int numberOfLoses = 0;
	private static int numberOfWins = 0;
	
	public static void main(String[] args)
	{
		analyzeCommandLineArguments(args);
		learningPlayer = new SolutionLearningPlayer();
		randomPlayer = new RandomPlayer();
		playGames();
		displayResults();
	}
	
	private static void playGames()
	{
		for(int i = 1; i <= numberOfMatches; i++)
		{
			board = new Board3D(5);
			match = new Match(board, learningPlayer, randomPlayer);
			match.play();
			updateGameWinLoseDraw(match);
		}
	}
	
	private static void displayResults()
	{
		System.out.println(learningPlayer.getName() + " stats:");
		System.out.println("Wins : " + numberOfWins);
		System.out.println("Loses : " + numberOfLoses);
		System.out.println("Draws : " + numberOfDraws);
	}
	
	private static void updateGameWinLoseDraw(Match matchToAnalyze)
	{
		if(matchToAnalyze.getWinner() == null)
		{
			numberOfDraws++;
		}
		else if(matchToAnalyze.getWinner() == learningPlayer)
		{
			numberOfWins++;
		}
		else
		{
			numberOfLoses++;
		}
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
