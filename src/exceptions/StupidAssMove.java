package exceptions;


public class StupidAssMove extends RuntimeException
{
     public StupidAssMove(String event)
     {
        super("You Stupid Ass, Ran into something did you?");
     }
}//end inner class