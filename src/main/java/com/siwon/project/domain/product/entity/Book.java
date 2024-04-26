package com.siwon.project.domain.product.entity;

import com.siwon.project.global.auditing.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;

    private Long stock;

    @Builder
    public Book(String bookName, Long bookPrice, String description, String author,
            String publisher) {
        this.name = bookName;
        this.description = description;
        this.price = bookPrice;
        this.publisher = publisher;
        this.author = author;
        this.stock = 0L;
    }

    public void updateBookInfo(String name, Long price, String description, String author,
            String publisher, Long stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.stock = stock;
    }


}
