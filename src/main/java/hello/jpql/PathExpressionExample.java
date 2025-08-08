package hello.jpql;

import hello.jpql.dto.Member;
import hello.jpql.dto.TEAM;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PathExpressionExample {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {


            TEAM team = new TEAM();
            team.setName("Breaking Sound TEAM");
            em.persist(team);

            Member member = new Member();
            member.setUserName("선아무개");
            member.setAge(24);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setAge(25);
            member2.setUserName("김아무개");
            member2.setTeam(team);
            em.persist(member2);


            em.flush();
            em.clear();
            tx.commit();

            //명시적 조인
            List<Member> members = em.createQuery("select m from TEAM t join t.members m", Member.class).getResultList();

            System.out.println("================= 명시적 조인 =================");
            for (Member member1 : members) {

                System.out.println(member1);
            }

            List<Collection> result = em.createQuery("select t.members from TEAM t", Collection.class).getResultList();

            System.out.println("================= 묵시적 조인 ================="); // t.members 안에 내용을 조회 못하며
                                                                                 // 헷갈리니 안쓰는게 좋음
            System.out.println(result);

            

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();

        }
        emf.close();

    }
}
