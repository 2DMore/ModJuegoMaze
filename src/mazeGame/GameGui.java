package mazeGame;
import javax.swing.*;

import exceptions.SlowAssPlayer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameGui extends JFrame implements ActionListener{
    public HighScore hs;  
    private int catFileName=1;
    Container cp;
    FileLoader fl = new FileLoader();
    //create menu items
    private JMenuBar menuBar;
    private JMenu newMenu;
    private JMenuItem itemExit;
    private JMenuItem newGameItem;
    private JMenuItem openFileItem;
    private JMenuItem itemEnterName;
    private JMenuItem itemHighScore;
    private JMenuItem itemSaveScore;
    //end create menu items
    private JLabel shagLabel;
    private int ix;
    private int jx;
    private int timeLeft;
    private JPanel progBarPanel;
    private JLabel[][] labelMatrix;

    JPanel dimondsPanel=new JPanel();
    JLabel mainLabel=new JLabel();
    private TimeCalculator timeCalc;
    private  JProgressBar progressBar;
    private mazeObject mo;
    JPanel newPanel;// = new JPanel();
    TheArchitect theArc = new TheArchitect();
    String[][] scrapMatrix; 
    private  Timer timely; 
    public TimeKeeper tk;
    public String playerName;
    public int levelNum=1;
    private static int CTRL=2;

    public static void main(String[] args)
    {
        try{
            new GameGui();
        }catch(Exception e){
            JPanel ventana = new JPanel();
            JOptionPane.showMessageDialog(ventana,e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            
        }
        
    }

    public GameGui()
    {
        super("Maze, a game of wondering"); //call super to initilize title bar of G.U.I.
        cp=getContentPane();
        shagLabel = new JLabel("",new ImageIcon("src/yeababyyea.jpg"),JLabel.LEFT);//GUI background for initial load
        cp.add(shagLabel);
        //Add Exit & New Game Menu Items
        itemExit = new JMenuItem("Exit");
        itemExit.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_X, CTRL));//press CTRL+X to exit if you want
        itemSaveScore = new JMenuItem("Save High Score");
        itemSaveScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_S, CTRL));//press CTRL+S to save high score if you want
        itemHighScore=new JMenuItem("High Score");
        itemHighScore.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_H, CTRL));//press CTRL+H to view high score if you want
        itemEnterName = new JMenuItem("Enter Player Name");
        itemEnterName.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_N, CTRL));//press CTRL+N to enter your name if you want
        newGameItem = new JMenuItem("New Game");
        openFileItem = new JMenuItem("Open Maze File.");
        openFileItem.setAccelerator (KeyStroke.getKeyStroke (KeyEvent.VK_O, CTRL));//press CTRL+O to open a level if you want
        newGameItem.setActionCommand("New Game");
        newGameItem.addActionListener(this);
        itemEnterName.setActionCommand("EnterName");
        itemEnterName.addActionListener(this);
        itemSaveScore.setActionCommand("SaveScore");
        itemSaveScore.addActionListener(this);
        itemHighScore.setActionCommand("HighScore");
        itemHighScore.addActionListener(this);
        itemExit.setActionCommand("Exit");
        itemExit.addActionListener(this);
        openFileItem.setActionCommand("Open");
        openFileItem.addActionListener(this);
        newMenu = new JMenu("File");
        newMenu.add(newGameItem);
        newMenu.add(itemEnterName);
        newMenu.add(openFileItem);
        newMenu.add(itemHighScore);
        newMenu.add(itemSaveScore);
        newMenu.add(itemExit);
        //Add Exit Menu Item
        //Add Menu Bar
        menuBar = new JMenuBar();
        menuBar.add(newMenu);
        setJMenuBar(menuBar);
        //Add Menu Bar     
        newPanel = new JPanel();
        hs = new HighScore();
        tk=new TimeKeeper();
        pack();
        setVisible (true);//show our menu bar and shagLabel.. Yea baby Yea! Whoa.. to much java.
    }//end constructor
     
    public void actionPerformed(ActionEvent e){
        switch(e.getActionCommand()){
            case "Exit":
            System.exit(0);
            break;

            case "New Game":
            setName();
            OpenMeze("src/level1.maz");
            break;

            case "EnterName":
            setName();
            break;

            case "HighScore":
            ScoreGui sg = new ScoreGui();
            sg.scoreGuiInit();  
            break; 

            case "SaveScore": 
            hs.addHighScore(playerName,tk.getMinutes(),tk.getSeconds(),levelNum);
            break;

            case "Open":
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                OpenMeze(chooser.getSelectedFile().getAbsolutePath());//load the file we need
            }
            break;



        }
        /* 
        if (e.getActionCommand().equals("Exit"))//exit on the menu bar
        {
             new Timer(1000, updateCursorAction).stop();
             System.exit(0); //exit the system.   
        }
        else if (e.getActionCommand().equals("New Game"))//new game on the menu bar
        {
             return; //maybe implent this feature later
        }//end New Game Command
        else if(e.getActionCommand().equals("EnterName"))//Allows user to enter their name for high score
        {
            setName();  
        }
        else if(e.getActionCommand().equals("HighScore"))//Displays the high scores
        {
            ScoreGui sg = new ScoreGui();
            sg.scoreGuiInit();   
        }
        else if(e.getActionCommand().equals("SaveScore"))//allows the user to save their score at any time.
        {
            hs.addHighScore(playerName,tk.getMinutes(),tk.getSeconds(),levelNum);
        }
        else if(e.getActionCommand().equals("Open"))//to start the game you have to open a maze file. this is on the menu
        {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) 
            {
                OpenMeze(chooser.getSelectedFile().getAbsolutePath());//load the file we need
            }
         }*/
     }//end actionPerformed method

    public void OpenMeze(String filePath){
        try{
            fl.loadFile(filePath);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Level Not Found", "Error", JOptionPane.ERROR_MESSAGE);
           this.dispose();
        }
        //load the file we need
        theArc.setExit(fl.ExitXCord(),fl.ExitYCord());
        loadMatrixGui("newLoad"); 
    }

    public void setName(){
        playerName=JOptionPane.showInputDialog("Please Enter your Earth Name");
    }

    public void reloadDiamondLabel(){
        //JLabel mainLabel=new JLabel(theArc.collected+"/"+theArc.globalTotalDimonds+" Diamonds", JLabel.CENTER);//show how many dimonds are left to collect on the gui!
        mainLabel.setText(theArc.collected+"/"+theArc.globalTotalDimonds+" Diamonds");
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        dimondsPanel = new JPanel();
        dimondsPanel.add(mainLabel);
        cp.remove(dimondsPanel);
        cp.add(dimondsPanel,BorderLayout.SOUTH);
    }

     
     public void loadMatrixGui(String event)
     {
        if (event == "newLoad")
         {  System.gc();
            theArc.globalTotalDimonds = fl.dimondCount(); //Set the total diamonds
            remove(mainLabel); 
            remove(dimondsPanel);
            reloadDiamondLabel();
            
             remove(newPanel);//remove the previous level's game from the screen
             if(progBarPanel !=null)//remove the progress bar from the gui as long as its already been created.
             remove(progBarPanel);
             String[][] temp = fl.getGameMatrix();
             scrapMatrix = new String[fl.getMatrixSizeRow()][fl.getMatrixSizeColumn()];   
             for (int i = 0; i < scrapMatrix.length; i++){
                for (int j = 0; j < scrapMatrix[i].length; j++){
                    scrapMatrix[i][j]= temp[i][j];//create a new matrix so we dont have a refrence to another objects matrix!
              }}//end double for loop
             
             timeCalc = new TimeCalculator();//create the time calculator used to determine how much time each level is given.
             timeCalc.calcTimeforMaze(fl.dimondCount(),fl.getMatrixSizeRow(),fl.getMatrixSizeColumn());//let time calculator know the parameters of the game 
             timeLeft=timeCalc.getMinutes();//get the minutes allowed for the level
             ix=timeCalc.getSeconds();//get the seconds allowed for the level;
             jx=0;//reset the variable used for keeping time to zero since its a new level
             timely = new Timer(1000,updateCursorAction);//create a timer to update the progress bar
             timely.start();//start the timer
             progBarPanel = new JPanel();//panel for progress bar
             progressBar = new JProgressBar(0, timeCalc.getMinutes()*100);//minutes returns a single digit, we have to multiply it for Bar.
             progressBar.setStringPainted(true);
             progBarPanel.add(progressBar);
             cp.add(progBarPanel,BorderLayout.NORTH);
             newPanel = new JPanel();
             newPanel.setLayout(new GridLayout(fl.getMatrixSizeRow(),fl.getMatrixSizeColumn()));//set our panel for the game to the size of the matrix      
             labelMatrix=new JLabel[fl.getMatrixSizeRow()][fl.getMatrixSizeColumn()];
             newPanel.addKeyListener( new MyKeyHandler(this) );
        }//end if
        else if(event =="updateLoad")//every time the player moves the gui must be updated.
        {
            reloadDiamondLabel();
            scrapMatrix = theArc.getUpdatedMatrix();//get the new matrix to be displayed from the architect
            remove(newPanel);//remove the old game
            newPanel = new JPanel();
            newPanel.setLayout(new GridLayout(fl.getMatrixSizeRow(),fl.getMatrixSizeColumn()));
            newPanel.addKeyListener( new MyKeyHandler(this) );
            newPanel.grabFocus();        
        }
          for (int i = 0; i < labelMatrix.length; i++){
              for (int j = 0; j < labelMatrix[i].length; j++){
                  labelMatrix[i][j]=  mo=new mazeObject(this, scrapMatrix[i][j]);//add our maze images into the gui
              }}//end double for loop
         cp.add(newPanel);
         remove(shagLabel);//remove the constructors initial background
         System.gc();//force java to clean up memory use.
         pack();
         setVisible (true);
         newPanel.grabFocus();  
     }//end loadMatrixGui method
 
    public void nextLevelLoad()
    {
        levelNum+=1;
        tk.keepTime(timeLeft,ix);//The TimeKeeper object keeps a running tab of the total time the player has used.(for high score)
        timely.stop();//dont count while we are loading the next level.
        theArc = new TheArchitect();//flush everything from TheArchitect so we dont get goffee results
        catFileName+=01;//the next file to be loaded (number)
        String fileName="src/level"+catFileName+".maz";
        System.gc();
        try {
            fl.loadFile(fileName);
            scrapMatrix=fl.getGameMatrix();//get the new matrix from the fileloader for the next level.
            theArc.setExit(fl.ExitXCord(),fl.ExitYCord());
            loadMatrixGui("newLoad");      
        } catch (IOException e) {
            hs.addHighScore(playerName,tk.getMinutes(),tk.getSeconds(),levelNum);
           JOptionPane.showMessageDialog(this, "No more levels for you", "End of game", JOptionPane.INFORMATION_MESSAGE);
           this.dispose();
           new GameGui();
        }//load the file we need
           
    }
 
    Action updateCursorAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e)throws SlowAssPlayer
    {
        ix-=1;
        jx+=1;
        if(ix<0)
        {
            ix=60;
            timeLeft-=1;
        }
    if(timeLeft==0 && ix==0)
    {
        timely.stop();
        JLabel yousuckLabel = new JLabel("",new ImageIcon("yousuck.jpg"),JLabel.LEFT);
        cp.add(yousuckLabel);
        remove(newPanel);
        remove(progBarPanel);
        pack();
        setVisible (true);
        timely.stop();
        catFileName-=01;
    if(catFileName<01){
        throw new SlowAssPlayer(GameGui.this, "Slow ass took to long.");
    } 
    else
        loadMatrixGui("newLoad");
    }//end first if
        progressBar.setValue(jx);
        progressBar.setString(timeLeft+":"+ix);
    }//end actionPerformed
};//end class
    

}//end class    