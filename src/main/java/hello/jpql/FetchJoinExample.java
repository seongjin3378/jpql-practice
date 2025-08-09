package hello.jpql;

import hello.jpql.dto.Member;
import hello.jpql.dto.TEAM;

import javax.persistence.*;
import java.util.List;

public class FetchJoinExample {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TEAM team = new TEAM();
            team.setName("fk group TEAM");
            em.persist(team);

            Member member = new Member();
            member.setUserName("장아무개");
            member.setAge(19);

            member.setTeam(team);
            em.persist(member);

            Member member0 = new Member();
            member0.setUserName("호아무개");
            member0.setAge(19);
            member0.setTeam(team);

            em.persist(member0);

            TEAM team2 = new TEAM();
            team2.setName("rumr Team");
            em.persist(team2);

            Member member2 = new Member();
            member2.setUserName("늘아무개");
            member2.setAge(29);
            member2.setTeam(team2);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUserName("박아무개");
            member3.setAge(11);
            member3.setTeam(team2);
            em.persist(member3);


            em.flush();
            em.clear();


            //fetch join 안쓰면 n+1 문제 발생
            String query = "select t from TEAM t join fetch t.members";

            List<TEAM> teamsList = em.createQuery(query, TEAM.class).getResultList();

            System.out.println("=============== 중복 문제 ===============");
            for(TEAM t : teamsList) {
                System.out.println(t.getName() + "member size : " + t.getMembers().size() );
            }

            query = "select distinct t from TEAM t join fetch t.members";
            teamsList = em.createQuery(query, TEAM.class).getResultList();


            //같은 식별자를 가진 entity를 jpql 내장 명령어로 삭제시킴
            System.out.println("=============== 중복 문제 해결 ===============");
            for(TEAM t : teamsList) {
                System.out.println(t.getName() + "member size : " + t.getMembers().size() );
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();

        }
        emf.close();

    }
}
