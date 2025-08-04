package hello.jpql;

import hello.jpql.dto.Member;
import hello.jpql.dto.Member2;
import hello.jpql.dto.TEAM;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JoinExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {


            TEAM team = new TEAM();
            team.setName("TOM");



            Member member = new Member();
            member.setAge(31);
            member.setUserName("고아무개");

            Member member2 = new Member();
            member2.setAge(29);
            member2.setUserName("이아무개");

            team.addMember(member);
            team.addMember(member2);
            em.persist(team);


            TEAM team2 = new TEAM();
            team2.setName("HOT WaterMelon");

            Member member3 = new Member();
            member3.setAge(26);
            member3.setUserName("HOT WaterMelon");
            team2.addMember(member3);

            em.persist(team2);

            Member2 member4 = new Member2();
            member4.setAge(25);
            member4.setUserName("HOT WaterMelon");

            em.persist(member4);

            em.flush();
            em.clear();

            tx.commit();

            List<Member> founds = em.createQuery("select m from Member m inner join m.team t", Member.class).getResultList();

            for(Member m : founds) {
                System.out.println("============INNER JOIN============");
                System.out.println(m.getUserName());
            }

            founds = em.createQuery("select m from Member m left join m.team t", Member.class).getResultList();

            
            //놀 아무개는 team이 없기 때문에 null로 나옴
            for(Member m : founds) {
                System.out.println("============LEFT JOIN============");
                System.out.println(m.getUserName() + ", " + m.getTeam().getName());
                
            }

            
            //=, <, >, <=, <> 전부 사용가능
            founds = em.createQuery("select m from Member m, TEAM t where m.userName = t.name", Member.class).getResultList();
            for(Member m : founds) {
                System.out.println("============THETA JOIN============");
                System.out.println(m.getUserName() + ", " + m.getTeam().getName());

            }

            //oneToMany, ManyToOne 안걸려있어도 entity를 가져올 수 있음 //현재
            List<Object[]> founds2 = em.createQuery("select m, t from Member2 m left join  TEAM t on m.userName = t.name", Object[].class).getResultList();

            for(Object[] row : founds2) {

                Member2 m = (Member2) row[0];
                TEAM   t = (TEAM)   row[1];   // 조건 불일치 시 null

                System.out.println("============연관 관계 없는 JOIN============");
                System.out.println(m.getUserName()+", "+ t.getName());
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
