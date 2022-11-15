public class ItemStatusBorrowed extends ItemStatusGeneral{
    Member borrower;
    Day loanDate;
    
    public ItemStatusBorrowed(Member aMember){
        this.borrower=aMember;
        this.loanDate=SystemDate.getInstance().clone();
    }

    public Member getMember(){
        return borrower;
    }

    @Override
    public String getStatus() {
        return String.format("Borrowed by %s %s on %s", borrower.getId(),borrower.getName(),loanDate);
    }

    @Override
    public boolean getAvaibility() {
        return false;
    }
}