public class ExMemberAlreadyBorrowingItem extends Exception{
    public ExMemberAlreadyBorrowingItem(){
        super("Member already borrowing the item!");
    }

    public ExMemberAlreadyBorrowingItem(String message){
        super(message);
    }
    
}
