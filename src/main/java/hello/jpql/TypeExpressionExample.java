package hello.jpql;

import hello.jpql.dto.Member;
import hello.jpql.dto.MemberType;
import hello.jpql.dto.TEAM;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class TypeExpressionExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            TEAM team = new TEAM();
            team.setName("ROM");

            Member member = new Member();
            member.setUserName("전아무개");
            member.setAge(57);
            member.setMemberType(MemberType.ADMIN);

            em.persist(member);

            em.flush();
            em.clear();

            String query = "select m.userName, 'HELLO', true From Member m where m.memberType = hello.jpql.dto.MemberType.ADMIN";

            List<Object[]> result = em.createQuery(query, Object[].class).getResultList();


            for(Object[] row : result) {
                System.out.println("objects = " + row[0]);
                System.out.println("objects = " + row[1]);
                System.out.println("objects = " + row[2]);
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
