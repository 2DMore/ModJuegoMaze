package exceptions;
import mazeGame.GameGui;


public class SlowAssPlayer extends RuntimeException
{
    /**
     *
     */
    private final GameGui gameGui;

    public SlowAssPlayer(GameGui gameGui, String event)
    {
        super("You Stupid Ass, Did you eat to much for dinner?  Move Faster!");//the entire game has ended.
        this.gameGui = gameGui;
        //the game is over, here we must tell our high score method to recond the details.
        this.gameGui.hs.addHighScore(this.gameGui.playerName,this.gameGui.tk.getMinutes(),this.gameGui.tk.getSeconds(),this.gameGui.levelNum);
        
    }
}//end class