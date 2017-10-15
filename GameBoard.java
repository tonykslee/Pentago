import java.util.ArrayList;
import java.util.List;

public class GameBoard {
	
	char myState[][];
	int myHeuristic;
	boolean isGameOver;
	private String previousMove;
	public char player1color;
	public char player2color;
	
	public GameBoard() {
		myState = new char[6][6];
	}
	
	public GameBoard(char[][] moves) {
		myState = new char[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				myState[i][j] = moves[i][j];
			}
		}
	}
	
	public boolean getIsGameOver() {
		return isGameOver;
	}
	public String getPreviousMove() {
		return previousMove;
	}

	public void setPreviousMove(String previousMove) {
		this.previousMove = previousMove;
	}

	public char getPlayer1color() {
		return player1color;
	}

	public void setPlayer1color(char player1color) {
		this.player1color = player1color;
	}

	public char getPlayer2color() {
		return player2color;
	}

	public void setPlayer2color(char player2color) {
		this.player2color = player2color;
	}

	public void rotateClockwise(int blockNum) {
		char temp;
		int offsetRow = 0, offsetCol = 0;
		if (blockNum == 2) {
			offsetCol = 3;
		} else if(blockNum == 3) {
			offsetRow = 3;
		} else if(blockNum == 4) {
			offsetRow = 3;
			offsetCol = 3;
		}
		temp = myState[0 + offsetRow][0 + offsetCol];
		myState[0 + offsetRow][0 + offsetCol] = myState[2 + offsetRow][0 + offsetCol];
		myState[2 + offsetRow][0 + offsetCol] = myState[2 + offsetRow][2 + offsetCol];
		myState[2 + offsetRow][2 + offsetCol] = myState[0 + offsetRow][2 + offsetCol];
		myState[0 + offsetRow][2 + offsetCol] = temp;
		temp = myState[0 + offsetRow][1 + offsetCol];
		myState[0 + offsetRow][1 + offsetCol] = myState[1 + offsetRow][0 + offsetCol];
		myState[1 + offsetRow][0 + offsetCol] = myState[2 + offsetRow][1 + offsetCol];
		myState[2 + offsetRow][1 + offsetCol] = myState[1 + offsetRow][2 + offsetCol];
		myState[1 + offsetRow][2 + offsetCol] = temp;
		
	}
	
	public void rotateCounterClockwise(int blockNum) {
		rotateClockwise(blockNum);
		rotateClockwise(blockNum);
		rotateClockwise(blockNum);
	}
	
	public void placePiece(int x, int y, char pieceColor) {
		myState[x][y] = pieceColor;
		
	}

	public char[][] getMyState() {
		return myState;
	}

	public void setMyState(char[][] myState) {
		this.myState = myState;
	}

	public void addToBoard(int row, int col, char playerColor) {
		this.myState[row][col] = playerColor;
	}
	
	public void rotateBoard(int block, int dir) {
		if (dir == 0) {
			rotateCounterClockwise(block);
		} else {
			rotateClockwise(block);
		}
	}
	public int getMyHeuristic() {
		return myHeuristic;
	}

	public void setMyHeuristic(int myHeuristic) {
		this.myHeuristic = myHeuristic;
	}
	
	public void clearHeuristic() {
		this.myHeuristic = 0;
	}
	
	public boolean isGameOver() {
		int countEmptySpots = 0;
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (myState[i][j] == '\u0000') {
					countEmptySpots++;
				}
			}
		}
		
		if (!checkHorizontal() && !checkVertical() 
				&& !checkDiagnalLeft() && !checkDiagnalRight()
				&& countEmptySpots > 0) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkHorizontal() {
		int count = 1;
		char previousChar = '\u0000';
		char currentChar;
		for (int i = 0; i < 6; i++) {//row
			for (int j = 0; j < 6; j++) {//column
				currentChar = myState[i][j];
				if (currentChar == previousChar) {
					count++;
					
					if (count == 5 && currentChar != '\u0000') {
						return true;
					}
				} else {
					count = 1;
					previousChar = currentChar;
				}
			}
		}
		return false;
	}
	
	private boolean checkVertical() {
		int count = 1;
		char previousChar = '\u0000';
		char currentChar;
		for (int i = 0; i < 6; i++) {//column
			for (int j = 0; j < 6; j++) {//row
				currentChar = myState[j][i];
				if (currentChar == previousChar) {
					count++;
					
					if (count == 5 && currentChar != '\u0000') {
						return true;
					}
				} else {
					count = 1;
					previousChar = currentChar;
				}
			}
		}
		return false;
	}
	
	private boolean checkDiagnalLeft() {
		int count = 1;
		char previousChar = '\u0000';
		char currentChar = '\u0000';
		int offset1 = 0, offset2 = 0;
		boolean inBound;
		for (int m = 0; m < 3; m++) {
			int j = 0;
			if (m == 1) {
				offset1 = 1;
				offset2 = 0;
			} else if (m == 2) {
				offset1 = 0;
				offset2 = 1;
			}
			for (int i = 0; i < 6; i++) {//iterate through rows
				inBound = false;
				if (i + offset1 < 6 && j + offset2 < 6) {
					currentChar = myState[i + offset1][j + offset2];
					j++;
					inBound = true;
				}
				if (currentChar == previousChar && inBound) {
					count++;
					
					if (count == 5 && currentChar != '\u0000') {
						return true;
					}
				} else {
					count = 1;
					previousChar = currentChar;
				}
			}
		}
		
		return false;
	}
	
	private boolean checkDiagnalRight() {
		int count = 1;
		char previousChar = '\u0000';
		char currentChar = '\u0000';
		int offset1 = 0, offset2 = 0;
		boolean inBound = false;
		for (int m = 0; m < 3; m++) {
			int j = 0;
			if (m == 1) {
				offset1 = 1;
				offset2 = 0;
			} else if (m == 2) {
				offset1 = 0;
				offset2 = 1;
			}
			for (int i = 5; i >= 0; i--) {//iterate through rows
				inBound = false;
				if (i - offset1 >= 0 && j + offset2 < 6) {
					currentChar = myState[i - offset1][j + offset2];
					j++;
					inBound = true;
				}
				if (currentChar == previousChar && inBound) {
					count++;
					
					if (count == 5 && currentChar != '\u0000') {
						return true;
					}
				} else {
					count = 1;
					previousChar = currentChar;
				}
			}
		}
		
		return false;
	}
	
	
	public boolean isValidMove(int row, int col) {
		if (myState[row][col] == '\u0000') {
			return true;
		}
		return false;
		
	}
	
	public int[] translateMove(int block, int position) {
		int row = 0, col = 0;
		int[] newMove = new int[2];
		if (block == 1) {
			row = (position - 1) / 3;
			col = (position - 1) % 3;
		} if (block == 2) {
			row = (position - 1) / 3;
			col = (position - 1) % 3 + 3;
		} if (block == 3) {

			row = (position - 1) / 3 + 3;
			col = (position - 1) % 3;
		} if (block == 4) {

			row = (position - 1) / 3 + 3;
			col = (position - 1) % 3 + 3;
		}
		newMove[0] = row;
		newMove[1] = col;
		return newMove;
	}
	
	public int[] reverseTranslate(int row, int col) {
		int block = 0, position = 0;
		int [] newMove = new int[2];
		
		if (row / 3 == 0) {
			if (col / 3 == 0) {
				block = 1;
			} else {
				block = 2;
			}
		} else {
			if (col / 3 == 0) {
				block = 3;
			} else {
				block = 4;
			}
		}
		position = (col % 3) + (3 * (row % 3)) + 1;
		newMove[0] = block;
		newMove[1] = position;
		return newMove;
	}
	
	public int getDirection (String theMove) {
		char direction = theMove.charAt(5);
		if (direction != 'R' || direction != 'r' ) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int getBlockToRotate(String theMove) {
		return theMove.charAt(4) - '0';
	}
	
	public boolean isValidSyntax(String theMove) {
		boolean validSyntax = true;
		int blockPlacement = 0;
		int position = 0;
		int blockToRotate = 0;
		char direction;
		int[] newMove = new int[2];
		
		try {

			blockPlacement = theMove.charAt(0) - '0';
			position = theMove.charAt(2) - '0';
			blockToRotate = getBlockToRotate(theMove);
			direction = theMove.charAt(5);
			if ((direction != 'R' && direction != 'r' 
					&& direction != 'L' && direction != 'l')
					|| (blockPlacement < 1 || blockPlacement > 4)
					|| (blockToRotate < 1 || blockToRotate > 4)
					|| (position < 1 || position > 9)) {
				throw new IllegalArgumentException();
			}

			newMove = translateMove(blockPlacement, position);
			
		} catch (Exception e) {
			validSyntax = false;
		}
		
		if (validSyntax && isValidMove(newMove[0], newMove[1])) {
			return true;
		}
		System.out.println("Invalid move");
		return false;
	}
	
	public void performMove(int row, int col, char color) {
		myState[row][col] = color;
		
	}
	
	//dir:  0 = left, 1 = right
	//block:   the block to rotate 1-4
	public void performRotate(int dir, int block) {
		if (dir == 1) {
			rotateClockwise(block);
		} else {
			rotateCounterClockwise(block);
		}
	}
	
	
	public List<int[]> getChildren(boolean isCurrentPlayer2) {
		List<int[]> nextMoves = new ArrayList<int[]>();
		
		int count = 0;
		int row = 0;
		int col = 0;
		int block = 0; // block to rotate
		int dir = 0; // 0 is left, 1 is right
		ArrayList<GameBoard> tempList = new ArrayList<GameBoard>();
		for (dir = 0; dir < 2; dir++) {
			for (block = 0; block < 4; block++) {
				for(row = 0; row < 6; row++) {
					for(col = 0; col < 6; col++) {
						if(myState[row][col] == '\u0000') {
							nextMoves.add(new int[] {row, col, block, dir});
						}
					}
				}
			}
		}
		
		return nextMoves;
	}
	
	public void displayGameBoard() {
		char temp[][] = myState;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (i % 3 == 0 && j == 0) {
					System.out.println("+-------+-------+");
				} 
				if (j == 0 || j == 3) {
					System.out.print("| ");
				}
				
				if (temp[i][j] == '\u0000') {
					System.out.print(". ");
				} else {
					System.out.print(temp[i][j] + " ");	
				}
				
				if (j == 5) {
					System.out.println("|");
				}
			}
		}
		System.out.println("+-------+-------+"); 
	}


	public boolean equals(GameBoard theGameBoard) {
		if ((theGameBoard.myState).equals(this.myState)) {
			return true;
		}
		return false;
	}

	
	//adds up heuristic: available spots left + points for how many pieces they have in a row.
	public int getHeuristicValue(boolean isAlphaPlayer) {
		char previousChar = '$';
		char currentChar = '\u0000';
		char myColor;
		if (isAlphaPlayer) {
			myColor = player1color;
		} else {
			myColor = player2color;
		}
		int numAvailableWinningSpots = 0, countEmpty = 0, 
				offset1 = 0, offset2 = 0, count = 1;
		
		//horizontal
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				currentChar = myState[i][j];
				
				if (myState[i][j] == '\u0000' || currentChar == myColor) {
					countEmpty++;
					if (countEmpty == 5) {
						numAvailableWinningSpots++;
					} if (countEmpty == 6) {
						numAvailableWinningSpots++;
					}
				} else {
					countEmpty = 0;
				}
				
				if (currentChar == previousChar && currentChar != '\u0000') {
					count++;
					
					countNumInArow(currentChar, previousChar, count, isAlphaPlayer);
					
				} else {
					count = 1;
					previousChar = currentChar;
				}
			}
			countEmpty = 0;
			previousChar = '$';
		}
		countEmpty = 0;
		
		//vertical
		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < 6; i++) {
				currentChar = myState[i][j];
				
				if (myState[i][j] == '\u0000' || currentChar == myColor) {
					countEmpty++;
					if (countEmpty == 5) {
						numAvailableWinningSpots++;
					} if (countEmpty == 6) {
						numAvailableWinningSpots++;
					}
				} else {
					countEmpty = 0;
				}

				if (currentChar == previousChar && currentChar != '\u0000') {
					count++;
					
					countNumInArow(currentChar, previousChar, count, isAlphaPlayer);
					
				} else {
					count = 1;
					previousChar = currentChar;
				}
			}
			countEmpty = 0;
			previousChar = '$';
		}
		countEmpty = 0;
		//bottom left to upper right
		for (int m = 0; m < 3; m++) {
			int j = 0;
			if (m == 1) { offset1 = 1; offset2 = 0;}
			if (m == 2) { offset1 = 0; offset2 = 1;}
			for (int i = 5; i >= 0; i--) {
				
				if (i - offset1 >= 0 && j + offset2 < 6) {
					currentChar = myState[i - offset1][j + offset2];
					if (currentChar == '\u0000' || currentChar == myColor) {
						countEmpty++;
						if (countEmpty == 5) {
							numAvailableWinningSpots++;
						} if (countEmpty == 6) {
							numAvailableWinningSpots++;
						}
					}else {
						countEmpty = 0;
					}
					if (currentChar == previousChar && currentChar != '\u0000') {
						count++;
						
						countNumInArow(currentChar, previousChar, count, isAlphaPlayer);
						
					} else {
						count = 1;
						previousChar = currentChar;
					}
				} 
				j++;
			}
			previousChar = '$';
			countEmpty = 0;
		}
		countEmpty = 0;
		offset1 = 0; 
		offset2 = 0;
		
		//upper left to bottom right
		for (int m = 0; m < 3; m++) {
			int j = 0;
			if (m == 1) { offset1 = 1; offset2 = 0;}
			if (m == 2) { offset1 = 0; offset2 = 1;}
			for (int i = 0; i < 6; i++) {
				if (i + offset1 < 6 && j + offset2 < 6) {
					currentChar = myState[i + offset1][j + offset2];
					if (currentChar == '\u0000' || currentChar == myColor) {
						countEmpty++;
						if (countEmpty == 5) {
							numAvailableWinningSpots++;
						} if (countEmpty == 6) {
							numAvailableWinningSpots++;
						}
					} else {
						countEmpty = 0;
					}
					
					if (currentChar == previousChar && currentChar != '\u0000') {
						count++;
						
						countNumInArow(currentChar, previousChar, count, isAlphaPlayer);
						
					} else { 
						count = 1;
						previousChar = currentChar;
					}
				} 
				j++;
			}
			previousChar = '$';
			countEmpty = 0;
		}
		return (numAvailableWinningSpots + myHeuristic);
	}
	
	public void countNumInArow(char currentChar, char previousChar, int count, boolean isAlphaPlayer) {
		if (isAlphaPlayer) {
			if (count == 5 && player2color == currentChar) {
				myHeuristic += 500;
			} else if (count == 4 && player2color == currentChar) {
				myHeuristic += 50;
			} else if (count == 3 && player2color == currentChar) {
				myHeuristic += 20;
			} else if (count == 2 && player2color == currentChar) {
				myHeuristic += 10;
			} 
			
			if (count == 5 && player1color == currentChar) {
				myHeuristic -= 500;
			} else if (count == 4 && player1color == currentChar) {
				myHeuristic -= 50;
			} else if (count == 3 && player1color == currentChar) {
				myHeuristic -= 20;
			} else if (count == 2 && player1color == currentChar) {
				myHeuristic -= 10;
			} 
		} else {
			if (count == 5 && player2color == currentChar) {
				myHeuristic -= 500;
			} else if (count == 4 && player2color == currentChar) {
				myHeuristic -= 50;
			} else if (count == 3 && player2color == currentChar) {
				myHeuristic -= 20;
			} else if (count == 2 && player2color == currentChar) {
				myHeuristic -= 10;
			} 
			
			if (count == 5 && player1color == currentChar) {
				myHeuristic += 500;
			} else if (count == 4 && player1color == currentChar) {
				myHeuristic += 50;
			} else if (count == 3 && player1color == currentChar) {
				myHeuristic += 20;
			} else if (count == 2 && player1color == currentChar) {
				myHeuristic += 10;
			} 
		}
		
	}
	
	public List<int[]> generateMoves() {
      List<int[]> nextMoves = new ArrayList<int[]>(); // allocate List
 
      // If gameover, i.e., no next move
      if (isGameOver()) {
         return nextMoves;   // return empty list
      }
 
      // Search for empty cells and add to the List
      for (int l = 0; l < 2; l++) {
    	 for (int k = 0; k < 4; k++) {
    		 for (int row = 0; row < 6; ++row) {
    			 for (int col = 0; col < 6; ++col) {
    				 if (myState[row][col] == '\u0000') {
    					 nextMoves.add(new int[] {row, col});
    				 }
    			 }
    		 }
    	 }
      }
      return nextMoves;
   }
}
