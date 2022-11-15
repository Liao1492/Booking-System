public class ExMemberDoesNotExist extends Exception{
    public ExMemberDoesNotExist(){
        super("Member not found!");
    }

    public ExMemberDoesNotExist(String message){
        super(message);
    }
}
