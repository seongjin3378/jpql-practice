package hello.jpql.dto;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TEAM {
    @Id
    @GeneratedValue
    private Long id;

    private String name;


    @OneToMany(mappedBy = "team",   cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();


    public void addMember(Member member) {
        member.setTeam(this);
        members.add(member);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
