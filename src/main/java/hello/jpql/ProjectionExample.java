package hello.jpql;

import javax.persistence.*;
import java.util.List;


public class ProjectionExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            String[] userNames   = { "이아무개", "이아무개", "여아무개" };
            int[]    ages        = { 30, 30, 26};
            String[]    productName = {"바나나 라떼 분망형 / 플라스틱 빨대 X 1000", "말차 라떼 분말형", "냉동딸기 지퍼백 1kg / 삼다수 2L * 6", };
            int[]       productPrice = {103000, 10000, 52400};
            int       productStock = 91;
            String    city        = "아무개시";
            String    street      = "아무개동";
            String    zipCode     = "58091";
            int       orderAmount = 1;


            for(int i = 0; i < userNames.length; i++) {
                Member member = new Member();
                member.setUserName(userNames[i]);
                member.setAge(ages[i]);

                // --- Product 생성 & 설정 ---
                Product product = new Product();
                product.setName(productName[i]);
                product.setPrice(productPrice[i]);
                product.setStockAmount(productStock);

                // --- Order 생성 & 설정 ---
                Order order = new Order();
                order.setAddress(new Address(city, street, zipCode));
                order.setOrderAmount(orderAmount);
                order.setProduct(product);


                em.persist(member);
                em.persist(product);
                em.persist(order);

            }
            em.flush();
            em.clear();
            tx.commit();


            List<Member> members = em.createQuery("SELECT m from Member m", Member.class).getResultList();
            System.out.println("엔티티 프로젝션 = " + members.get(0).getUserName());


            Address address = em.createQuery("SELECT o.address from Order o ORDER BY o.id", Address.class)
                    .setMaxResults(1)
                    .getSingleResult();
            //단건이 아니므로 최대 조회 갯수를 하나로 설정 한후 getSingleResult()를 반환해야함
            System.out.println("임베디드 타입 프로젝션 " + address);

            Query q = em.createQuery("SELECT distinct m.userName, m.age from Member m");

            List<Object[]> rows =  q.getResultList();


            for(Object[] row : rows) { //3건이지만 distinct로 두건 조회
                String name = (String) row[0];
                Integer age = (Integer) row[1];
                System.out.println("distinct Age = "+ name+ ", distinct Name = "+ age );
            }

            List<Member> newOperation = em.createQuery("SELECT NEW hello.jpql.Member(m.userName, m.age) FROM Member m", Member.class).getResultList();

            for(Member member : newOperation) {
                System.out.println("newOperation Age = "+ member.getAge() + ", newOperation Name = "+ member.getUserName());
            }

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();

        }
        emf.close();

    }
}
