package ghar.root.recycleAndDrawer.bkEnd;

/**
 * Created by root on 2/7/18.
 */

public class ContactsData {
    private int Id;
    private String contName;
    private String phNum;

    public ContactsData(int id, String contName, String phNum) {
        Id = id;
        this.contName = contName;
        this.phNum = phNum;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getContName() {
        return contName;
    }

    public void setContName(String contName) {
        this.contName = contName;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
    }

}

