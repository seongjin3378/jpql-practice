package hello.jpql;

import hello.jpql.dto.Member;

import javax.persistence.*;
import java.util.List;

public class PaginationExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

                Member member = new Member();
                member.setAge(27);
                member.setUserName("서아무개");
                em.persist(member);


                Member member2 = new Member();
                member2.setAge(27);
                member2.setUserName("경아무개");

                em.persist(member2);
                
                em.flush();
                em.clear();


                //오라클 방언은 3depth 로 나감
                List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                        .setFirstResult(0)
                        .setMaxResults(10)
                        .getResultList();

            System.out.println("result size = "+result.size());

            for(Member member1 : result) {
                System.out.println(member1);
            }

                

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();

        }
        emf.close();

    }
}
