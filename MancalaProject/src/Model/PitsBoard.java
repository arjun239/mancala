package Model;

/**
 * 
 * This class represent the game pits data
 *
 */
public class PitsBoard {

    private int[] pits;

    public PitsBoard() {
	this.pits = new int[14];
    }
//used to undo, restore previous state
    public void copy(PitsBoard other) {
	for (int i = 0; i < 14; i++) {
	    this.pits[i] = other.getPit(i);
	}
    }

    /**
     * @return the pits
     */
    public int[] getPits() {
	return pits;
    }

    /**
     * Get pit's number of stone
     * 
     * @param index
     * @return
     */
    public int getPit(int index) {
	return pits[index];
    }

    /**
     * @param pits
     *            the pits to Add a stone
     * this method increments number of stone by 1 in each pit
     */
    public void addtPits(int index) {
	this.pits[index]++;
    }

    /**
     * set the number of stones in a pit, this happens only once in the start of the program
     * 
     * @param index
     * @param num
     */
    public void setPits(int index, int num) {
	this.pits[index] = num;
    }
}
