package hello.jpql.dto;

import javax.persistence.*;

@Entity
public class Member2 {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private int age;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
