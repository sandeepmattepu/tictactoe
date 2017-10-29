import de.ovgu.dke.teaching.ml.tictactoe.PlayMatch;
import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.game.Board2D;
import de.ovgu.dke.teaching.ml.tictactoe.game.Board3D;
import de.ovgu.dke.teaching.ml.tictactoe.game.Match;
import de.ovgu.dke.teaching.ml.tictactoe.player.KeyboardPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.player.RandomPlayer;
import de.ovgu.dke.teaching.ml.tictactoe.player.SmartPlayer;

public class SMPlayMatch extends PlayMatch 
{
	public static void main(String[] args)
	{
		
		// Start of our experiment code
		IPlayer smartPlayer = new KeyboardPlayer();
		IPlayer keyBoardPlayer = new KeyboardPlayer();
		IBoard boardToPlay = new Board3D(5);
		
		Match firstMatch = new Match(boardToPlay, smartPlayer, keyBoardPlayer);
		firstMatch.play();
		
		IPlayer winner = firstMatch.getWinner();
		if(winner != null)
		{
			System.out.println(winner.getName());
		}
		else
		{
			System.out.println("Its draw");
		}
	}
}
