
public class ExWrongCommand extends Exception{
    public ExWrongCommand(){
        super("Wrong command!");
    }

    public ExWrongCommand(String message){
        super(message);
    }

}
