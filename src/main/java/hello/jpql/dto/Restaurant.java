package hello.jpql.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Restaurant {
    @Id
    private Long id; // 기본키

    @Column(nullable = false, length = 100)
    private String name; // 식당 이름
}
