package com.sandeep.solutions.task1;


import de.ovgu.dke.teaching.ml.tictactoe.PlayMatch;
import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IMove;
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
		IBoard simpleGame = new Board3D(3);
		IPlayer myself = new TestPlayer();
		IPlayer randomPlayer = new TestPlayer();
		Match match = new Match(simpleGame,myself,randomPlayer);
		match.play();
		
//		List<IMove> moves = simpleGame.getMoveHistory();
//		for (IMove iMove : moves) 
//		{
//			System.out.println("Player " + iMove.getPlayer().getName() + " makes move " + iMove.getPosition()[0] +
//					", " + iMove.getPosition()[1]);
//		}
//		System.out.println(simpleGame.getSize());
	}
}
