package hello.jpql;


import hello.jpql.dto.Member;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setUserName("이아무개");
            member.setAge(730);
            em.persist(member);
            em.flush();
            tx.commit();

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> members = query.getResultList();


            Member member1 = query.getSingleResult();
            System.out.println("result = " + member1);

            for(Member member2 : members) {
                System.out.println("member1" + member1);
            }

            Query q = em.createQuery("select m.userName, m.age from Member m");

            List<Object[]> rows =  q.getResultList();


            for(Object[] row : rows) {
                String name = (String) row[0];
                Integer age = (Integer) row[1];
                System.out.println("Age = "+ name+ " Name = "+ age );
            }

            TypedQuery<Member> query2 = em.createQuery("select m from Member m WHERE m.userName = :userName", Member.class);
            query2.setParameter("userName", member.getUserName());

            List<Member> members2 = query2.getResultList();
            for(Member member2 : members2) {
                System.out.println("특정 userName의 결과값 = " + member2);
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