public class ItemStatusOnhold extends ItemStatusGeneral{
    Member m;
    Day dueDate;
    

    public ItemStatusOnhold(Member aMember,Day aDueDate){
        m=aMember;
       this.dueDate= aDueDate;
    }

    @Override
    public String getStatus() {
        return String.format("On holdshelf for %s %s until %s", m.getId(),m.getName(),dueDate);
    }

    @Override
    public Member getMember(){
        return m;
    }

    @Override
    public boolean getAvaibility() {
        return true;
    }

    @Override
    public Day getDay(){
        return dueDate;
    }
    
    
    
}
