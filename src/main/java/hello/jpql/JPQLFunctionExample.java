package hello.jpql;

import hello.jpql.dto.Address;
import hello.jpql.dto.Member;
import hello.jpql.dto.Order;

import javax.persistence.*;
import java.util.List;

public class JPQLFunctionExample {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Order order = new Order();
            order.setOrderAmount(6);
            order.setAddress(new Address("아무개시", "아무개구 E편의점", "575200"));
            order.setCustomerName("이아무개");
            em.persist(order);
            Order order2 = new Order();
            order2.setOrderAmount(6);
            order2.setAddress(new Address("아무개시", "아무개구 A편의점", "727726"));
            order2.setCustomerName("상아무개");
            em.persist(order2);

            em.flush();
            em.clear();
                            // concat('a', 'b')
                            //substring(o.customerName, 2, 3) //customerName에 2번째 인덱스부터 3개 문자열 가져옴
                            //Lowwer, Upper 대소문자 바꾸기
                            //TRIM 공백 제거(ltrim, rtrim)
                            //locate('de', 'abcdefg') 숫자 4를 돌려줌
                            //length 문자 길이
                            //ABS, SQRT, MODE 수학 function
                            //size, 자식 크기 돌려줌 ex) select size(t.members) FROM Team t;
            String query = "select 'a' || 'b' FROM Order o";

            List<String> results = em.createQuery(query, String.class).getResultList();

            for (String result : results) {
                System.out.println(result);
            }

            query="select function('group_concat', o.customerName) FROM Order o";

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
