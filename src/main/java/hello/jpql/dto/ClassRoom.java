package hello.jpql.dto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClassRoom {

    @Id
    @GeneratedValue
    private Long id;

    /** 강의실 코드 (예: A101) */
    private String roomCode;

    /** 강의실 이름 (예: “멀티미디어 강의실”) */
    private String name;

    private String floor;

    @OneToMany(mappedBy = "classRoom")
    private List<Student> students = new ArrayList<>();

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
