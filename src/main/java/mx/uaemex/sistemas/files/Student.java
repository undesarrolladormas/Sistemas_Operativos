package mx.uaemex.sistemas.files;

import java.io.Serializable;

public class Student implements Serializable {
    private String name; //10
    private String lastName; //10
    private int age; //2
    private String address; //20
    private int zipCode; // 5
    private String mail; // 200

    public Student(String name, String lastName, int age, String address, int zipCode, String mail) throws Exception {
        this.setName(name);
        this.setLastName(lastName);
        this.setAge(age);
        this.setAddress(address);
        this.setZipCode(zipCode);
        this.setMail(mail);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if ( name.length() < 3 || name.length() > 10 )
            throw new Exception("Invalid Value");
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws Exception {
        if ( lastName.length() < 3 || lastName.length() > 10 )
            throw new Exception("Invalid Value");
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws Exception {
        if (age < 0 || age > 200)
            throw new Exception("Invalid Value");
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws Exception {
        if(address.length() < 3 || address.length() > 15)
            throw new Exception("Invalid Value");
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) throws Exception {
        if(zipCode < 10000 || zipCode > 999999)
            throw new Exception("Invalid Value");
        this.zipCode = zipCode;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) throws Exception {
        if (mail.length() < 3 || mail.length() > 35)
            throw new Exception("Invalid Value");
        this.mail = mail;
    }
}
