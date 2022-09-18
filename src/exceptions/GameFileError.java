package exceptions;


public class GameFileError extends RuntimeException //if a level is loaded with ether two players or two exits throw this
{
    public GameFileError(){
        super("Your maze file ether had more than one player, or more than one exit.");
    }
}