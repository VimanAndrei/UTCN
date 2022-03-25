package model;

public class Clients {
    private int id;
    private String name;
    private String address;



    public Clients(int idClient, String name, String address) {
        this.id = idClient;
        this.name = name;
        this.address = address;
    }

    public Clients(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client [" +
                "idClient=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                "]\n";
    }
}
