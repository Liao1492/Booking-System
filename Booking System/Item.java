import java.util.*;

public class Item implements Comparable<Item>{
    private String id;
    private String name;
    private Day arrivalDay;
    private ItemStatus status;
    private ArrayList<Member> requestList;

    public Item(String anId, String aName) throws ExItemIdAlreadyExist{
        if(Club.getInstance().itemBelong(anId)){ // Safe data,no exception will happen
            try {
                String name = Club.getInstance().searchItem(anId).getName();
                throw new ExItemIdAlreadyExist("Item ID already in use: "+ anId+" "+ name);
            } catch (ExItemDoesNotExist e) {
                e.printStackTrace();
            }
        }
        this.id=anId;
        this.name=aName;
        this.arrivalDay=SystemDate.getInstance().clone();
        this.status=new ItemStatusAvailable();
        this.requestList=new ArrayList<Member>();
        Club.getInstance().addItem(this);
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public static String getListingHeader() {
        return String.format("%-5s%-17s%11s   %s", "ID", "Name", "  Arrival  ", "Status");
    }

    public void checkItemStatus(Member m) throws ExItemNotAvailable{
        if(!status.getAvaibility()){
            throw new ExItemNotAvailable("Item not available.");
        } else {
            if(status.getMember()!=null){
                if(status.getMember().compareTo(m)!=0){
                    throw new ExItemNotAvailable("Item not available.");
                }
            }
        }
    }

    public boolean borrowItem(Member m){
        boolean previous;
        if(this.status.getAvaibility() && this.status.getMember()!=null){
            previous=true;
        } else {
            previous= false;
        }
        status=new ItemStatusBorrowed(m);
        return previous;
    }

    
    public Member giveBackItem(Member m) throws ExItemNotBorrowedByThisUser {
        if(!status.getAvaibility()){
            if(this.status.getMember().compareTo(m)!=0){
                throw new ExItemNotBorrowedByThisUser("The item is not borrowed by this member.");
            }
            if(!requestList.isEmpty()){
                Member onHold=requestList.get(0);
                Day dueDate=SystemDate.getInstance().clone();
                dueDate.addDays(3);
                status=new ItemStatusOnhold(onHold,dueDate);
                onHold.decreaseRequestNumber();
                System.out.println(String.format("Item [%s %s] is ready for pick up by [%s %s]. On hold due on %s.", this.id,this.name,onHold.getId(),onHold.getName(),dueDate));
                requestList.remove(onHold);
                m.decreaseBorrowedNumber();
                return onHold;
            } else {
                status=new ItemStatusAvailable();
                m.decreaseBorrowedNumber();
                return null;
            }
    }else{
        m.decreaseBorrowedNumber();
        return null;
    }
    }

    public void makeItemAvailable(){
        status=new ItemStatusAvailable();
    }

    public void makeItemOnHold(Member m){
        Day dueDate=SystemDate.getInstance().clone();
        dueDate.addDays(3);
        status=new ItemStatusOnhold(m, dueDate);
    }

    public void requestBorrowed(Member m){
        requestList.add(m);
    }

    public int searchForRequest(Member m) throws ExRequestComandNotFound{
        int pos=0;
        boolean requestFound=false;
        for(Member a:requestList){
            if(a.compareTo(m)==0){
                pos=requestList.indexOf(a);
                requestFound=true;
            }
        }
        if(!requestFound){
            throw new ExRequestComandNotFound("Request record is not found.");
        } else {
            requestList.remove(m);
            m.decreaseRequestNumber();
        }
        return pos;
    }



    public int getSize(){
        return requestList.size();
    }

    public void requestItem(Member m) throws ExItemIsAvailable, ExMemberAlreadyInTheList, ExMemberAlreadyBorrowingItem, ExItemRequestQuotaExceeded{
        if(this.status.getAvaibility()){
            if(this.status.getMember()==null || this.status.getMember().compareTo(m)==0){
                throw new ExItemIsAvailable("The item is currently available.");
            }
            for(Member a:requestList){
                if(a.compareTo(m)==0){
                    throw new ExMemberAlreadyInTheList("The same member has already requested the item." );
                }
            }  
        }else {
            if(this.status.getMember().compareTo(m)==0){
                throw new ExMemberAlreadyBorrowingItem("The item is already borrowed by the same member.");
            }
            for(Member a:requestList){
                if(a.compareTo(m)==0){
                    throw new ExMemberAlreadyInTheList("The same member has already requested the item." );
                }
            }  
        }
        m.increaseRequest();;
        requestList.add(m);
    }

    public void requestItem(Member m,int pos) throws ExItemRequestQuotaExceeded{
        m.increaseRequest();;
        requestList.add(pos,m);
    }

    public void reduceRequestList(Member m){
        requestList.remove(m);
    }

    public void dueDateItem(){
        if(this.status.getAvaibility() && this.status.getMember()!=null){
            Day d=this.status.getDay();
            if(SystemDate.getInstance().clone().compareDays(d)==0){
                d= SystemDate.getInstance().clone();
                d.addDays(3);
                System.out.println(String.format("On hold period is over for %s %s.", this.id,this.name));
                if(!requestList.isEmpty()){
                    Member onHold=requestList.get(0);
                    status= new ItemStatusOnhold(onHold, d);
                    System.out.println(String.format("Item [%s %s] is ready for pick up by [%s %s]. On hold due on %s.", this.id,this.name,onHold.getId(),onHold.getName(),d));
                    onHold.decreaseRequestNumber();
                    reduceRequestList(onHold);
                } else {
                    status= new ItemStatusAvailable();
                }
            }
        }
    }
      
    @Override
    public String toString(){
        String out;
        if(requestList.isEmpty()){
            out= String.format("%-5s%-17s%11s   %s", id, name, arrivalDay, status.getStatus());
        } else {
            out =String.format("%-5s%-17s%11s   %s + %d request(s):", id, name, arrivalDay, status.getStatus(),requestList.size());
            for(Member m: requestList){
                out=out+" "+m.getId();
            }
        }
        return out;
    }

    @Override
    public int compareTo(Item anotherItem) {
        if(this.id.equals(anotherItem.id)){
            return 0;
        } else if(this.id.compareTo(anotherItem.id)>0){
            return 1;
        } else {
            return -1;
        }
    }
}