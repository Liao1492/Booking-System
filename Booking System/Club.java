import java.util.*;

public class Club {

    private ArrayList<Member> allMembers;
    private ArrayList<Item> allItems;
    
    private static Club instance = new Club();

    private Club(){
        allMembers=new ArrayList<Member>();
        allItems=new ArrayList<Item>();
    }

    public static Club getInstance(){
        return instance;
    }

    public Member searchMember(String id) throws ExMemberDoesNotExist{
        for(Member m:allMembers){
            if(m.getId().equals(id)){
                return m;
            }
        }
        throw new ExMemberDoesNotExist("Member not found.");
    }

    public boolean memberExist(String id){
        for(Member m:allMembers){
            if(m.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Item searchItem(String id) throws ExItemDoesNotExist{
        for(Item i:allItems){
            if(i.getId().equals(id)){
                return i;
            }
        }
        throw new ExItemDoesNotExist("Item not found.");
    }

    public boolean itemBelong(String id){
        for(Item i:allItems){
            if(i.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public void addMember(Member m) {
        allMembers.add(m);
        Collections.sort(allMembers);
    }

    public void removeMember(Member m){
        allMembers.remove(m);
    }

    public void addItem(Item i){
        allItems.add(i);
        Collections.sort(allItems);
    }

    public void removeItem(Item i){
        allItems.remove(i);
    }

    public boolean checkOutItem(String memberId,String itemId) throws ExItemDoesNotExist, ExMemberDoesNotExist, ExItemNotAvailable, ExExceededNumberOfItems{
        Member m=searchMember(memberId);
        Item i=searchItem(itemId);
        i.checkItemStatus(m);
        m.increasedBorrowed();
        boolean prev=i.borrowItem(m);    
        return prev;
    }

    public Member checkInItem(String memberId,String itemId) throws ExItemNotBorrowedByThisUser, ExItemDoesNotExist, ExMemberDoesNotExist{
        Member m=searchMember(memberId);
        Item i=searchItem(itemId);
        Member onHold = i.giveBackItem(m);
        return onHold;
    }

    public void requestItem(String memberId,String itemId) throws ExItemDoesNotExist, ExMemberDoesNotExist, ExItemNotAvailable, ExExceededNumberOfItems, ExItemIsAvailable, ExMemberAlreadyInTheList, ExMemberAlreadyBorrowingItem, ExItemRequestQuotaExceeded{
        Member m=searchMember(memberId);
        Item i=searchItem(itemId);
        i.requestItem(m);    
    }

    public void requestItem(String memberId,String itemId,int pos) throws ExItemRequestQuotaExceeded, ExItemDoesNotExist, ExMemberDoesNotExist{
        Member m=searchMember(memberId);
        Item i=searchItem(itemId);
        i.requestItem(m,pos);
    }

    public void removeItemList(String memberId,String itemId) throws ExItemDoesNotExist, ExMemberDoesNotExist{
        Member m=searchMember(memberId);
        Item i=searchItem(itemId);
        i.reduceRequestList(m);
        
    }

    public int cancelRequest(String memberId,String itemId) throws ExRequestComandNotFound, ExMemberDoesNotExist, ExItemDoesNotExist{
        Member m=searchMember(memberId);
        Item i=searchItem(itemId);
        int pos=i.searchForRequest(m);
        return pos;
    }
    
    public void checkDueDate(){
        for(Item i: allItems){
            i.dueDateItem();
        }
    }

    public void listClubMembers(){
        System.out.println(Member.getListingHeader());
        for(Member m: allMembers){
            System.out.println(m.toString());
        }
    }

    public void listItems(){
        System.out.println(Item.getListingHeader());
        for(Item i: allItems){
            System.out.println(i.toString());
        }
    }
}
