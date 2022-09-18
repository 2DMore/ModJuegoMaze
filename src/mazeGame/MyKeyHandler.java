package mazeGame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MyKeyHandler extends KeyAdapter //captures arrow keys movement
    {
        /**
         *
         */
        private final GameGui gameGui;

        /**
         * @param gameGui
         */
        MyKeyHandler(GameGui gameGui) {
            this.gameGui = gameGui;
        }

        public void keyPressed (KeyEvent theEvent){     
            try{    
           switch (theEvent.getKeyCode())
           {
               case KeyEvent.VK_UP:
               {
                 this.gameGui.theArc.playerMove(-1,0,this.gameGui.scrapMatrix,this.gameGui.fl.dimondCount());//let the Architect know we moved, along with the current matrix
                 this.gameGui.loadMatrixGui("updateLoad");//reload the gui to show the move
                 if (this.gameGui.theArc.getLevel()==true)
                 {
                    this.gameGui.nextLevelLoad();//if the player hit an exit door, load the next level
                 }
                 break;
              }
              case KeyEvent.VK_DOWN:
              {
                 this.gameGui.theArc.playerMove(1,0,this.gameGui.scrapMatrix,this.gameGui.fl.dimondCount());//see above
                 this.gameGui.loadMatrixGui("updateLoad");//see above
                 if (this.gameGui.theArc.getLevel()==true)//see above
                 {
                    this.gameGui.nextLevelLoad();//see above
                 }
                 break;
             }
             case KeyEvent.VK_LEFT:
             {
                this.gameGui.theArc.playerMove(0,-1,this.gameGui.scrapMatrix,this.gameGui.fl.dimondCount());//see above
                this.gameGui.loadMatrixGui("updateLoad");//see above
                 if (this.gameGui.theArc.getLevel()==true)//see above
                 {
                     this.gameGui.nextLevelLoad();//see above
                 }
                break;
             }
             case KeyEvent.VK_RIGHT:
             { 
                this.gameGui.theArc.playerMove(0,1,this.gameGui.scrapMatrix,this.gameGui.fl.dimondCount()); //see above
                this.gameGui.loadMatrixGui("updateLoad");//see above
                 if (this.gameGui.theArc.getLevel()==true)
                 {
                     this.gameGui.nextLevelLoad();//see above
                 }
                break;   
             }
           }//end switch
        }catch(RuntimeException e){
            JPanel ventana = new JPanel();
            JOptionPane.showMessageDialog(ventana,e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }

            /* 
           JLabel mainLabel=new JLabel(this.gameGui.theArc.collected+"/"+this.gameGui.theArc.globalTotalDimonds +"Diamods", JLabel.CENTER);//show how many dimonds are left to collect on the gui!
           JPanel dimondsPanel = new JPanel();
           dimondsPanel.add(mainLabel);
           this.gameGui.cp.add(dimondsPanel,BorderLayout.SOUTH);*/
       }//end method
   }//end inner class