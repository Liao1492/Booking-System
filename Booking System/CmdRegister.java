public class CmdRegister extends RecordedCommand{
    
    Member m;

    @Override
    public void execute(String[] cmdParts) {
        
        try {
            if(cmdParts.length<3){
                throw new ExInsufficientArguments("Insufficient command arguments.");
            }
            m=new Member(cmdParts[1], cmdParts[2]);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } 
        catch (ExInsufficientArguments|ExMemberIdAlreadyUsed e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe(){
        Club c= Club.getInstance();
        c.removeMember(m);
        addRedoCommand(this);
    }

    @Override
    public void redoMe(){
        Club c=Club.getInstance();
        c.addMember(m);
        addUndoCommand(this);
    }
}
