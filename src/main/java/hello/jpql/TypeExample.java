package hello.jpql;

import hello.jpql.dto.Book;
import hello.jpql.dto.Item;
import hello.jpql.dto.Member;

import javax.persistence.*;
import java.util.List;

public class TypeExample {
    public static void main(String[] args) {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Book book = new Book();
            book.setName("도커 & 쿠버네티스 책");
            book.setAuthor("심아무개");

            em.persist(book);

            List<Item> books =  em.createQuery(
                    "SELECT i     " +
                            "FROM   Item i " +
                            "WHERE  TYPE(i) = Book",
                    Item.class
            ).getResultList();

            System.out.println("Type BOOK = " + books.size());




            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }finally {
            em.close();

        }
        emf.close();

    }

}
