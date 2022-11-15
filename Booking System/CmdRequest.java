public class CmdRequest extends RecordedCommand{

    String memberId;
    String itemId;

    @Override
    public void execute(String[] cmdParts) {
        
        Club istance= Club.getInstance();
        try {
            if(cmdParts.length<3){
                throw new ExInsufficientArguments("Insufficient command arguments.");
            }
            memberId=cmdParts[1];
            itemId=cmdParts[2];
            istance.requestItem(memberId, itemId);
            addUndoCommand(this);
            clearRedoList();
            int size=istance.searchItem(itemId).getSize();
            System.out.println("Done. This request is no. "+ size+ " in the queue.");
        } catch (ExInsufficientArguments|ExItemDoesNotExist | ExMemberDoesNotExist | ExItemNotAvailable | ExExceededNumberOfItems
                | ExItemIsAvailable | ExMemberAlreadyInTheList | ExMemberAlreadyBorrowingItem
                | ExItemRequestQuotaExceeded e) {
            System.out.println(e.getMessage());
        }
        
        
    }

    @Override
    public void undoMe() {
        Club instance=Club.getInstance();
        try {
            instance.removeItemList(memberId,itemId); // Safe data,no need to handle any exceptions.
            instance.searchMember(memberId).decreaseRequestNumber();
        } catch (ExItemDoesNotExist | ExMemberDoesNotExist e) {
            e.printStackTrace();
        }
        addRedoCommand(this);     
    }

    @Override
    public void redoMe() {
        Club instance=Club.getInstance();
        try {
            instance.requestItem(memberId,itemId); // Safe data, No exception!
        } catch (ExItemDoesNotExist | ExMemberDoesNotExist | ExItemNotAvailable | ExExceededNumberOfItems
                | ExItemIsAvailable | ExMemberAlreadyInTheList | ExMemberAlreadyBorrowingItem
                | ExItemRequestQuotaExceeded e) {
            e.printStackTrace();
        }
        addUndoCommand(this);
    }

}
