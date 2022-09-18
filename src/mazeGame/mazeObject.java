package mazeGame;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class mazeObject extends JLabel//inner class for each maze object, aka wall, player etc
{
    /**
     *
     */
    private final GameGui gameGui;

    public mazeObject(GameGui gameGui, String fileName)
    {
        this.gameGui = gameGui;
        fileName="src/"+fileName+".png";
        JLabel fancyLabel;
        fancyLabel = new JLabel("",new ImageIcon(fileName),JLabel.LEFT);
        this.gameGui.newPanel.add(fancyLabel);
    }
}//end inner class