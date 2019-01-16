package election_kontrolno2.domain;

public class Citizen {

    private String name;
    private String id;
    private String address;
    private boolean isAdmin;

    public Citizen() {
    }

    public Citizen(String name, String id, String address, boolean isAdmin) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
