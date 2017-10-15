import java.util.List;

public class Computer {

	public int nodesExpanded;
	private GameBoard gameBoard;
	
	public Computer() {
		
	}
	
	public GameBoard getBoard() {
		return gameBoard;
	}
	
	public int[] alphaBetaPrun(int depth, GameBoard gameBoard, boolean alphaPlayer, int alpha, int beta) {
		int heuristicValue = 0;
		int bestRow = -1;
		int bestCol = -1;
		int bestBlock = -1;
		int bestDir = -1;
		int row, col, block, dir;
		int compCount = 1;
		int humCount = 1;
		List<int[]> nextMoves = gameBoard.getChildren(alphaPlayer);
		
		
		if(depth == 0 || nextMoves.isEmpty()) {
			nodesExpanded++;
			heuristicValue = gameBoard.getHeuristicValue(alphaPlayer);
			return new int[] {heuristicValue, bestRow, bestCol};
		} else {
	         for (int[] move : nextMoves) {
	        	 row = move[0];
	        	 col = move[1];
	        	 block = move[2];
	        	 dir = move[3];
	             if (alphaPlayer) {  // max player (alpha)
//	            	 System.out.print("Human(MAX) Move: "+"("+row+","+col+")"+block+"/"+dir+" " + humCount++ + "/" + nextMoves.size() + " | ");
	            	 gameBoard.addToBoard(row, col, gameBoard.player1color);
	            	 gameBoard.rotateBoard(block, dir);
				     heuristicValue = alphaBetaPrun(depth - 1, gameBoard, false, alpha, beta)[0];
				     nodesExpanded++;
//				     System.out.println("Depth: "+ depth+ " Heuristic Value: " + heuristicValue + " ");
				     if (heuristicValue > alpha) {
				    	 alpha = heuristicValue;
				    	 bestRow = row;
				    	 bestCol = col;
	            		 bestBlock = block;
	            		 bestDir = dir;
				     }
	            	 //undo move
				     if (dir == 0) {
		            	 gameBoard.rotateBoard(block, 1);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             } else {
		            	 gameBoard.rotateBoard(block, 0);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             }
				     gameBoard.clearHeuristic();
	             } else {  // min player (beta)
//	            	 System.out.println("Computer(MIN) Move: "+"("+row+","+col+")"+block+"/"+dir+" " + humCount++ + "/" + nextMoves.size() + " | ");
	            	 gameBoard.addToBoard(row, col, gameBoard.player2color);
	            	 gameBoard.rotateBoard(block, dir);
	            	 heuristicValue = alphaBetaPrun(depth - 1, gameBoard, true, alpha, beta)[0];
	            	 nodesExpanded++;
//				     System.out.println("Depth: "+ depth+ " Heuristic Value: " + heuristicValue + " ");
	            	 if (heuristicValue < beta) {
	            		 beta = heuristicValue;
	            		 bestRow = row;
	            		 bestCol = col;
	            		 bestBlock = block;
	            		 bestDir = dir;
	            	 }
	            	 //undo move
	            	 if (dir == 0) {
		            	 gameBoard.rotateBoard(block, 1);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             } else {
		            	 gameBoard.rotateBoard(block, 0);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             }
	            	 gameBoard.clearHeuristic();
				 }
	             //prune off branch
	             if (alpha >= beta) {
	            	 break;
	             }
	          }
	         if (alphaPlayer) {
	        	 return new int[]{alpha, bestRow, bestCol, bestBlock, bestDir};
	         } else {
	        	 return new int[]{beta, bestRow, bestCol, bestBlock, bestDir};
	         }
	       }
	}
	
	public int[] minMax(int depth, GameBoard gameBoard, boolean alphaPlayer) {
		int heuristicValue = 0;
		int bestValue = 0;
		int bestRow = -1;
		int bestCol = -1;
		int bestBlock = -1;
		int bestDir = -1;
		int row, col, block, dir;
		int compCount = 1;
		int humCount = 1;
		List<int[]> nextMoves = gameBoard.getChildren(alphaPlayer);
		
		
		if(depth == 0 || nextMoves.isEmpty()) {
			nodesExpanded++;
			bestValue = gameBoard.getHeuristicValue(alphaPlayer);
			return new int[] {bestValue, bestRow, bestCol};
		} else {
	         for (int[] move : nextMoves) {
	        	 row = move[0];
	        	 col = move[1];
	        	 block = move[2];
	        	 dir = move[3];
	             if (alphaPlayer) {  // max player 
	            	 bestValue = Integer.MIN_VALUE;
//	            	 System.out.print("Human(MAX) Move: "+"("+row+","+col+")"+block+"/"+dir+" " + humCount++ + "/" + nextMoves.size() + " | ");
	            	 gameBoard.addToBoard(row, col, gameBoard.player1color);
	            	 gameBoard.rotateBoard(block, dir);
				     heuristicValue = minMax(depth - 1, gameBoard, false)[0];
	            	 nodesExpanded++;
//				     System.out.println("Depth: "+ depth+ " Heuristic Value: " + heuristicValue + " ");
				     if (heuristicValue > bestValue) {
				    	 bestValue = heuristicValue;
				    	 bestRow = row;
				    	 bestCol = col;
	            		 bestBlock = block;
	            		 bestDir = dir;
				     }
	            	 //undo move
				     if (dir == 0) {
		            	 gameBoard.rotateBoard(block, 1);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             } else {
		            	 gameBoard.rotateBoard(block, 0);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             }
				     gameBoard.clearHeuristic();
	             } else {  // min player
	            	 bestValue = Integer.MAX_VALUE;
//	            	 System.out.println("Computer(MIN) Move: "+"("+row+","+col+")"+block+"/"+dir+" " + humCount++ + "/" + nextMoves.size() + " | ");
	            	 gameBoard.addToBoard(row, col, gameBoard.player2color);
	            	 gameBoard.rotateBoard(block, dir);
	            	 heuristicValue = minMax(depth - 1, gameBoard, true)[0];
	            	 nodesExpanded++;
//				     System.out.println("Depth: "+ depth+ " Heuristic Value: " + heuristicValue + " ");
	            	 if (heuristicValue < bestValue) {
	            		 bestValue = heuristicValue;
	            		 bestRow = row;
	            		 bestCol = col;
	            		 bestBlock = block;
	            		 bestDir = dir;
	            	 }
	            	 //undo move
	            	 if (dir == 0) {
		            	 gameBoard.rotateBoard(block, 1);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             } else {
		            	 gameBoard.rotateBoard(block, 0);
		            	 gameBoard.addToBoard(row, col, '\u0000');
		             }
	            	 gameBoard.clearHeuristic();
				 }
	          }
	         if (alphaPlayer) {
	        	 return new int[]{bestValue, bestRow, bestCol, bestBlock, bestDir};
	         } else {
	        	 return new int[]{bestValue, bestRow, bestCol, bestBlock, bestDir};
	         }
		}
	}
	
	
}
