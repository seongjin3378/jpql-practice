package hello.jpql.dto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Item {

    @Column(nullable = false, length = 200)
    private String author;

    @Column(length = 50)
    private String isbn;

    // 생성자, getter/setter

    public Book() {}

    public Book(String name, String author, String isbn) {
        super(name);
        this.author = author;
        this.isbn   = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}