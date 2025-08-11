package hello.jpql;

import hello.jpql.dto.Member;
import hello.jpql.dto.TEAM;

import javax.persistence.*;
import java.util.List;

public class NamedQueryExample {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            TEAM team = new TEAM();
            team.setName("KH SERUM TEAM");

            Member m1 = new Member(); m1.setUserName("손아무개"); m1.setAge(24);
            Member m2 = new Member(); m2.setUserName("혜아무개"); m2.setAge(25);
            Member m3 = new Member(); m3.setUserName("놀아무개"); m3.setAge(26);
            Member m4 = new Member(); m4.setUserName("워아무개"); m3.setAge(27);

            team.addMember(m1);
            team.addMember(m2);
            team.addMember(m3);
            team.addMember(m4);

            em.persist(team);
            em.flush();
            em.clear();
            tx.commit();

            List<Member> members = em.createNamedQuery("findByUsername", Member.class).setParameter("userName", "놀아무개").getResultList();


            System.out.println("result = " + members.size());



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();

        }
        emf.close();

    }

}
