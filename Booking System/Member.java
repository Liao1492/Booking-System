public class Member implements Comparable<Member>{

    private String id;
    private String name;
    private Day joinDay;
    private int nBorrowed;
    private int nRequested;

    public Member(String anId,String aName) throws ExMemberIdAlreadyUsed{
        if(Club.getInstance().memberExist(anId)){ // Safe data,no exception will arise!
            String name;
            try {
                name = Club.getInstance().searchMember(anId).getName();
                throw new ExMemberIdAlreadyUsed("Member ID already in use: "+ anId +" " + name);
            } catch (ExMemberDoesNotExist e) {
                e.printStackTrace();
            }
            
        }
        this.id=anId;
        this.name=aName;
        this.joinDay=SystemDate.getInstance().clone();
        this.nBorrowed=0;
        this.nRequested=0;
        Club.getInstance().addMember(this);
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public static String getListingHeader() {
        return String.format("%-5s%-9s%11s%11s%13s", "ID", "Name", "Join Date ", "#Borrowed", "#Requested");
      }


    public void increasedBorrowed() throws ExExceededNumberOfItems{
        if(nBorrowed>=6){
            throw new ExExceededNumberOfItems("Loan quota exceeded.");         
        }
        nBorrowed+=1;
    }

    public void decreaseBorrowedNumber(){
        nBorrowed-=1;
    }

    public void increaseRequest() throws ExItemRequestQuotaExceeded{
        if(nRequested>=3){
            throw new ExItemRequestQuotaExceeded("Item request quota exceeded.");
        }
        nRequested+=1;
    }

    public void decreaseRequestNumber(){
        nRequested-=1;
    }

    @Override
    public String toString() {
        return String.format("%-5s%-9s%11s%7d%13d", id, name, joinDay.toString(), nBorrowed, nRequested);
    }

    @Override
    public int compareTo(Member another){
        if(this.id.equals(another.id)){
            return 0;
        } else if(this.id.compareTo(another.id)>0){
            return 1;
        } else {
            return -1;
        }
    }
    
}
