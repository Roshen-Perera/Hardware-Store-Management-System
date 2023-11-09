package lk.ijse.Jayabima.dto;

public class CustomerDto {
    private String Id;
    private String Name;
    private String Address;
    private String Mobile;

    public CustomerDto(String id, String name, String address, String mobile) {
        Id = id;
        Name = name;
        Address = address;
        Mobile = mobile;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }
}
