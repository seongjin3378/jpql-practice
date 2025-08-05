package hello.jpql;

import hello.jpql.dto.Member;
import hello.jpql.dto.TEAM;

import javax.persistence.*;
import java.util.List;

public class SubQueryExample {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            TEAM team = new TEAM();
            team.setName("스텔라루멘");

            Member member = new Member();
            member.setUserName("샴아무개");
            member.setAge(11);
            member.setTeam(team);

            Member member2 = new Member();
            member2.setUserName("란아무개");
            member2.setAge(18);

            Member member3 = new Member();
            member3.setUserName("오아무개");
            member3.setAge(26);

            member.changeTeam(team);
            member2.changeTeam(team);
            member3.changeTeam(team);

            em.persist(team);

            em.flush();
            em.clear();

            String query = "select (select avg(m1.age) From Member m1) as avgAge from Member m join TEAM t ON m.team = t";
            Double result = em.createQuery(query, Double.class).setMaxResults(1).getSingleResult();

            System.out.println("result = " + result);

            String jpql = """
                    SELECT t.name, sub.avg_age FROM team t JOIN (SELECT team_id, AVG(age) AS avg_age
                       FROM member GROUP BY team_id
                    ) sub ON t.id = sub.team_id
                    """;
            List<Object[]> rows = em.createNativeQuery(jpql).getResultList();
            for (Object[] row : rows) {
                String teamName = (String) row[0];
                Double avgAge   = ((Number) row[1]).doubleValue();
                System.out.printf("팀 %s → 평균 나이 %.2f%n", teamName, avgAge);
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
