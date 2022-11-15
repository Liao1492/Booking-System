public class ExMemberAlreadyInTheList extends Exception{
    public ExMemberAlreadyInTheList(){
        super("Member is already in the request list!");
    }

    public ExMemberAlreadyInTheList(String message){
        super(message);
    }
    
}
