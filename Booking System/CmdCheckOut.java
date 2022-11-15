public class CmdCheckOut extends RecordedCommand{

    String memberId;
    String itemId;
    boolean previus;
    @Override
    public void execute(String[] cmdParts) {
        
        try {
            if(cmdParts.length<3){
                throw new ExInsufficientArguments("Insufficient command arguments.");
            }
            memberId=cmdParts[1];
            itemId=cmdParts[2];
            previus=Club.getInstance().checkOutItem(memberId, itemId);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        }catch(ExInsufficientArguments e){
            System.out.println(e.getMessage());
        } 
        catch (ExMemberDoesNotExist e) {
            System.out.println(e.getMessage());
        } 
        catch (ExItemDoesNotExist e) {
            System.out.println(e.getMessage());
        }catch (ExExceededNumberOfItems e){
            System.out.println(e.getMessage());
        }catch(ExItemNotAvailable e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Club instance=Club.getInstance();
        try {
            Item i=instance.searchItem(itemId);
            i.makeItemAvailable();
            instance.checkInItem(memberId, itemId); // No exception,safe data
            if(previus){
                Member m=instance.searchMember(memberId);
                i.makeItemOnHold(m);
            }
        } catch (ExMemberDoesNotExist | ExItemDoesNotExist |ExItemNotBorrowedByThisUser e) {
            e.printStackTrace();
        }
        addRedoCommand(this);
        
    }

    @Override
    public void redoMe() {
        try {
            previus=Club.getInstance().checkOutItem(memberId, itemId); // Try catch statement is used only to make the compiler happy, we know for sure that the data is error-free
        } catch (ExItemDoesNotExist e) {
            e.printStackTrace();
        } catch (ExMemberDoesNotExist e) {
            e.printStackTrace();
        }catch (ExExceededNumberOfItems e){
            e.printStackTrace();
        }
         catch (ExItemNotAvailable e) {
            e.printStackTrace();
        }
        addUndoCommand(this);
    }

}
