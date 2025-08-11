package hello.jpql;

import hello.jpql.dto.Member;
import hello.jpql.dto.TEAM;

import javax.persistence.*;
import java.util.List;

public class BulkOperation {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            // 1) 데이터 세팅
            TEAM team = new TEAM();
            team.setName("H-B gy-rumr ex-end TEAM");

            Member m1 = new Member(); m1.setUserName("장아무개"); m1.setAge(18);
            Member m2 = new Member(); m2.setUserName("누아무개"); m2.setAge(19);

            team.addMember(m1);
            team.addMember(m2);

            em.persist(team);

            // INSERT가 DB에 반영되도록 먼저 flush
            em.flush();



            int updated = em.createQuery(
                            "update Member m " +
                                    "set m.age = 26 " +
                                    "where m.team   = :team " +
                                    "and m.userName in :names"
                    )
                    .setParameter("team", team)
                    .setParameter("names", List.of("장아무개", "누아무개"))
                    .executeUpdate();

            em.clear(); //벌크 연산후 영속성 컨텍스트 초기화 해줘야함 이전 값이 들어감

            // 3) 결과 확인 (조인 조회)
            List<Member> afterUpdate = em.createQuery(
                            "select m from Member m join fetch m.team t " +
                                    "where t.name = :teamName order by m.userName",
                            Member.class
                    )
                    .setParameter("teamName", "H-B gy-rumr ex-end TEAM")
                    .getResultList();

            for (Member member : afterUpdate) {
                System.out.println(member.getUserName() + " , " + member.getAge());
            }

            // 4) (옵션) 벌크 삭제 예: 특정 이름들만 삭제
            int deleted = em.createQuery(
                            "delete from Member m " +
                                    "where m.team = :team " +
                                    "and m.userName in :names"
                    )
                    .setParameter("team", team)
                    .setParameter("names", List.of("누아무개")) // 예: 누아무개만 삭제
                    .executeUpdate();

            em.clear();





            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();

        }
        emf.close();

    }
    }

