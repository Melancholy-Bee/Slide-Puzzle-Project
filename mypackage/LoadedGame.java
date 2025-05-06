//Code to Load previous game state (maura)

package mypackage;

public class LoadedGame {
    private int moveCount;
    private int[][] boardState;
    private boolean isCompleted;

    public LoadedGame(int moveCount, int[][] boardState, boolean isCompleted) {
        this.moveCount = moveCount;
        this.boardState = boardState;
        this.isCompleted = isCompleted;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
