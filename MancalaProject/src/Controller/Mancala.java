package Controller;

import Model.PitsBoard;
import View.ViewListener;

/**
 * 
 * This class can deal stones moving action, and can judge the game state and
 * update the pits board
 *
 */
public class Mancala {
    private PitsBoard pitsBoard;
    private PitsBoard lastBoard;
    int player;// 1 or -1
    boolean undoFlag;
    int undoTime;
    int lastPlayer;
    boolean oneMoreTurn;
    ViewListener vlistener;

    /**
     * Constructor
     * 
     * @param firstPlayer
     *            set the player move first
     * @param pitsNum
     */
        public Mancala(int firstPlayer, int pitsNum) {
	this.player = firstPlayer;
	pitsBoard = new PitsBoard();
	lastBoard = new PitsBoard();
	
        for (int i = 0; i < 14; i++) {
            //the if below checks if the control is in the pits or the mancala
	    if (i != 6 && i != 13) {
		this.pitsBoard.setPits(i, pitsNum);
		this.lastBoard.setPits(i, pitsNum);
	    } else {
		this.pitsBoard.setPits(i, 0);
		this.lastBoard.setPits(i, 0);
	    }
	}
	undoFlag = false;// one step undo flag
	undoTime = 0;
	lastPlayer = 0;
    }

    /**
     * Set viewListener
     * 
     * @param vlistener
     */
    public void setListener(ViewListener vlistener) {
	this.vlistener = vlistener;
    }

    /**
     * Remind the view to refresh
     * 
     */
    public void refresh() {
	this.vlistener.refreshPits();
    }

    /**
     * Get the pits board
     * 
     * @return
     */
    public int[] getPits() {
	return this.pitsBoard.getPits();
    }

    /**
     * Get current player's name
     * 
     * @return
     */
    public String getPlayer() {
	return this.player == 1 ? "A" : "B";
    }

    /**
     * Move a single pits' stones, place them to next pits one each
     * 
     * @param pitIndex
     * @return
     * 
     */
    private int placeStone(int pitIndex) {
        //get number of stones in the clicked pit
	int num = this.pitsBoard.getPit(pitIndex);
        //set number of stones to 0 in the pit which is clicked
	this.pitsBoard.setPits(pitIndex, 0);
        //placei is the pit that was clicked
	int placei = pitIndex;
        //the loop distributes the stones from the clicked pit into the next consecutive pits until the stones run out
	while (num > 0) {
            //this is making sure you go back to the start of the circular array
	    placei = (placei + 1) % 14;
            //this if checks if you are going into your own mancla or your opponents mancala, if you're in your opppon's mancala don't place stones in there 
            //6 is player 1's mancala and 13 is player -1's mancala
	    if (this.player == 1 && placei == 13 || this.player == -1
		    && placei == 6) {
		continue;
                //if not your oppon's mancala place a stone in it
	    } else {
                
		this.pitsBoard.addtPits(placei);
		num--;
	    }
	}
        // placei is the last pit you are in after all stones from clicked pit are distributed
	return placei;
    }

    /**
     * Get the current player's Mancala
     * 
     * @return
     */
    private int getPlayerMancala() {
	if (player == 1) {
	    return 6;
	} else {
	    return 13;
	}
    }

    /**
     * Judge whether the No.index pit is current player's
     * checks whether you're clicking on your own pits or oppon's pits
     * @param index
     * @return
     */
    private boolean isOwn(int index) {
	if (this.player == 1 && index < 6 && index >= 0) {
	    return true;
	}
	if (this.player == -1 && index < 13 && index >= 7) {
	    return true;
	}
	return false;
    }

    /**
     * Undo a move
     * 
     * @return whether undo successfully
     */
    public boolean undo() {
	if (this.undoFlag) {
	    return false;
	}
	if (this.undoTime >= 3) {
	    return false;
	}
	this.undoTime++;
	this.pitsBoard.copy(this.lastBoard);
	if (!this.oneMoreTurn) {
	    this.player = -this.player;
	}
	this.undoFlag = true;
	return true;
    }

    /**
     * Move the No.pitIndex pits' stones
     * 
     * @param pitIndex
     * @return whether move successfully
     * this is the method with the game rules
     */
    public boolean move(int pitIndex) {
	if (!isOwn(pitIndex)) {
	    return false;
	}
	if (this.pitsBoard.getPit(pitIndex) <= 0) {
	    return false;
	}
	oneMoreTurn = false;
	this.undoFlag = false;
	this.lastBoard.copy(pitsBoard);
	if (lastPlayer != this.player) {
	    lastPlayer = this.player;
	    this.undoTime = 0;
	}
	int placeEndi;
	while (true) {
	    placeEndi = placeStone(pitIndex);
            //if the last stone falls in your own mancala you get one more turn
	    if (placeEndi == getPlayerMancala()) {
		oneMoreTurn = true;
		break;
                //this is checking if the last stone fell in a pit on your side
            } else if (isOwn(placeEndi)
                        //this is checking if the last stone fell in an empty pit by checking if the pit currently has one stone
		    && this.pitsBoard.getPit(placeEndi) == 1) {
		int oppPit = 12 - placeEndi;
		int capNum = this.pitsBoard.getPit(placeEndi)
			+ this.pitsBoard.getPit(oppPit);
		this.pitsBoard.setPits(placeEndi, 0);
		this.pitsBoard.setPits(oppPit, 0);
		this.pitsBoard.setPits(getPlayerMancala(),
			this.pitsBoard.getPit(getPlayerMancala()) + capNum);
		this.player = -this.player;
		break;
	    } else {
		this.player = -this.player;
		break;
	    }
	}

	return true;
    }

    /**
     * 
     * @return whether the game is end
     */
    public boolean isEnd() {
	int i;
	for (i = 0; i < 6; i++) {
	    if (this.pitsBoard.getPit(i) > 0) {
		break;
	    }
	}
	if (i >= 6) {
	    return true;
	}
	for (i = 7; i < 13; i++) {
	    if (this.pitsBoard.getPit(i) > 0) {
		break;
	    }
	}
	if (i >= 13) {
	    return true;
	}
	return false;
    }

    /**
     * Get the winner of the game and clear pits board , moving all the stones
     * to the two players' Mancala
     * 
     * @return the winner of the players
     */
    public int getWinner() {
	int sum1 = 0;
	for (int i = 0; i < 7; i++) {
	    sum1 = sum1 + this.pitsBoard.getPit(i);
	    this.pitsBoard.setPits(i, 0);
	}
	int sum2 = 0;
	for (int i = 7; i < 14; i++) {
	    sum2 = sum2 + this.pitsBoard.getPit(i);
	    this.pitsBoard.setPits(i, 0);
	}
	this.pitsBoard.setPits(6, sum1);
	this.pitsBoard.setPits(13, sum2);
	if (sum1 > sum2) {
	    return 1;
	} else if (sum1 < sum2) {
	    return -1;
	} else {// draw
	    return 0;
	}
    }
}
