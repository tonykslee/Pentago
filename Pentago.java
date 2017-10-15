import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Pentago {
	public static final int DEPTH = 3;
	public static final boolean DEBUG = false;
	public static final boolean MANUAL_FIRST_MOVE = false;
	public static final int AI_FIRSTMOVE_ROW = 0;
	public static final int AI_FIRSTMOVE_COL = 0;
	public static final boolean AB_PRUNE = true; //alpha beta pruning. true will enable alpha beta pruning algorithm. false enables min max algorithm
	
	public static GameBoard myGameBoard;
	public static String myPlayerName;
	public static boolean isGameOver;
	public static char currentPlayer;
	
	public static void main (String args[]) throws FileNotFoundException {
		myGameBoard = new GameBoard();
		
		intro();
	}
	
	public static void intro() throws FileNotFoundException {
		boolean isPlayer1First;
		char color = '\u0000';
		Scanner myScanner = new Scanner(System.in);
		Random rand = new Random();
		System.out.print("Player 1 name:");
		myPlayerName = myScanner.nextLine();
		System.out.println("Player 2: Computer");
		System.out.print("Player 1 Token Color (B or W): ");
		while (color != 'B' && color != 'b' 
				&& color != 'W' && color != 'w') {
			color = (myScanner.nextLine()).charAt(0);
			myGameBoard.setPlayer1color(Character.toUpperCase(color));
		}
		if (color == 'W' || color == 'w') {
			myGameBoard.setPlayer2color('B');
		} else {
			myGameBoard.setPlayer2color('W');
		}
		System.out.print("Player to move next: ");
		if (rand.nextInt(2) == 0) {
			System.out.println("Player 1");
			isPlayer1First = true;
			currentPlayer = myGameBoard.getPlayer1color();
		} else {
			System.out.println("Player 2");
			isPlayer1First = false;
		}
		playGame(myScanner, isPlayer1First);
		myScanner.close();
	}
	
	public static void playGame(Scanner myScanner, boolean player1First) {
		String theMove;
		boolean isValid = false;
		int turnCount = 0;
		
		while (!isGameOver) {
			if (player1First) {
				currentPlayer = myGameBoard.getPlayer1color();
				System.out.println("Player 1 Move: ");
				theMove = myScanner.nextLine();
				
				do {
					if (myGameBoard.isValidSyntax(theMove)) {
						isValid = true;
						int[] myMove = myGameBoard.translateMove(theMove.charAt(0) - '0', theMove.charAt(2) - '0');
						myGameBoard.performMove(myMove[0], myMove[1], currentPlayer);
						
						myGameBoard.performRotate(myGameBoard.getDirection(theMove), myGameBoard.getBlockToRotate(theMove));
						
					} else {
						isValid = false;
						theMove = myScanner.nextLine();
					}
				} while (!isValid);
				
				myGameBoard.displayGameBoard();
				player1First = false;
				isGameOver = myGameBoard.isGameOver();
				if (isGameOver) {
					System.out.println("Player 1 wins");
				}
			} else if (turnCount == 0) { 
				currentPlayer = myGameBoard.getPlayer2color();
				playRandomMove();
				player1First = true;
			} else {
				currentPlayer = myGameBoard.getPlayer2color();
				playComputerMove();
				player1First = true;
				isGameOver = myGameBoard.isGameOver();
				if (isGameOver) {
					System.out.println("Player 2 wins");
				}
			}
			turnCount++;
		}
	}
	
	/**
	 * example move: 2/8 2R
	 * which means block 2 position 8, rotate block 2 right (clockwise)
	 */
	public static void playComputerMove() {
		int[] bestAImove = null;
		int num;
		Computer computer = new Computer();
		if (AB_PRUNE) {
			bestAImove = computer.alphaBetaPrun(DEPTH, myGameBoard, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
			num = computer.nodesExpanded;
		} else {
			bestAImove = computer.minMax(DEPTH, myGameBoard, false);
			num = computer.nodesExpanded;
		}
		int[] move = myGameBoard.reverseTranslate(bestAImove[1], bestAImove[2]);
		char dir;
		if (bestAImove[4] == 0) {
			dir = 'L';
		} else {
			dir = 'R';
		}
		System.out.println("Player 2 plays " + move[0] + "/" + move[1] + " " + (bestAImove[3] + 1) + dir + " with a heuristic value of: " + bestAImove[0]);
		myGameBoard.performMove(bestAImove[1], bestAImove[2], currentPlayer);
		myGameBoard.performRotate(bestAImove[4], bestAImove[3]);
		myGameBoard.displayGameBoard();
	}	
	
	public static void playRandomMove() {
		Random rand = new Random();
		if (MANUAL_FIRST_MOVE) {
			myGameBoard.performMove(AI_FIRSTMOVE_ROW, AI_FIRSTMOVE_COL, currentPlayer);
		} else {
			myGameBoard.performMove(rand.nextInt(6), rand.nextInt(6), currentPlayer);
		}
		myGameBoard.displayGameBoard();
	}
}
