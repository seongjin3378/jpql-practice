package hello.jpql;

import hello.jpql.dto.ClassRoom;
import hello.jpql.dto.Member;
import hello.jpql.dto.Student;

import javax.persistence.*;
import java.util.List;

public class ConditionalExpressionExample {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {


            ClassRoom classRoom = new ClassRoom();
            classRoom.setRoomCode("305");
            classRoom.setFloor("4F");
            classRoom.setName("아무개강의실");

            em.persist(classRoom);

            Student student = new Student();
            student.setName("리아무개");
            student.setState("공부 중");
            student.setClassRoom(classRoom);
            em.persist(student);

            Student student2 = new Student();
            student2.setName(null);
            student2.setState("방치게임 중");
            student2.setClassRoom(classRoom);
            em.persist(student2);

            em.flush();
            em.clear();

            List<String> results = em.createQuery("SELECT " +
                    "CASE s.state " +
                    "WHEN '공부 중' THEN '모니터링 X'" +
                    "WHEN '게임 중' THEN '모니터링 및 컴퓨터 초기화'" +
                    "END FROM Student s", String.class).getResultList();

            for (String result : results) {
                System.out.println(result);
            }

            String query = "select coalesce(s.name, '없음') from Student s";

            results = em.createQuery(query, String.class).getResultList();

            for (String result : results) {
                System.out.println(result);
            }

            query= "select NULLIF(s.name, 'ROM') from Student s";
            results = em.createQuery(query, String.class).getResultList();

            for (String result : results) {
                System.out.println(result);

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
