package hello.jpql.dto;

import javax.persistence.*;

@Entity
@NamedQuery(
        name = "findByUsername",
        query = "select m from Member m where m.userName = :userName"
)
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private int age;
    private MemberType memberType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TEAM team;

    public MemberType getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void changeTeam(TEAM team) {
        this.team = team;
        team.addMember(this);
    }

    public TEAM getTeam() {
        return team;
    }

    public void setTeam(TEAM team) {
        this.team = team;
    }

    public Member(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public Member() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
