public class ExMemberIdAlreadyUsed extends Exception{
    public ExMemberIdAlreadyUsed(){
        super("Id already used by another member!");
    }
    public ExMemberIdAlreadyUsed(String message){
        super(message);
    }
}
