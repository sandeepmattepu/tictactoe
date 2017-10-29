package com.sandeep.solutions.task1;

import com.sandeep.machine.learning.lms.LinearEquation;

import de.ovgu.dke.teaching.ml.tictactoe.PlayMatch;
import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.game.Board2D;
import de.ovgu.dke.teaching.ml.tictactoe.game.Board3D;
import de.ovgu.dke.teaching.ml.tictactoe.game.Match;
import de.ovgu.dke.teaching.ml.tictactoe.player.KeyboardPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.player.RandomPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.player.SmartPlayer;

public class SMPlayMatch
{
	public static void main(String[] args)
	{
		// initial target function w0 + w1*number_of_indicies_horizontal_free
		// + w2*numbr_vertically_free + w3*number_horizonttal_our + w4*number_vertical_our
		// w5*number_horizonttal_enemy + w6*number_vertical_enemy + w7 * diagnonally aligned ours +
		// w8*diagonally aligned enemy
		LinearEquation targetFunction = new LinearEquation(8);
		IBoard simpleGame = new Board2D(3);
		IPlayer myself = new KeyboardPlayer();
		IPlayer randomPlayer = new RandomPlayer();
		
		Match sampleMatch = new Match(simpleGame, myself, randomPlayer);
		sampleMatch.play();
	}
}
