package ru.gb.lesson4.hw;


import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    //    @Column(name = "author")
//    private String author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorId", referencedColumnName = "id")
    private Author author;

    public Book() {
    }

    public Book(String name, Author author) {
        this.name=name;
        this.author=author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author=author;
    }

    @Override
    public String toString() {
        return "id=" + id + " Название=" + name + " Автор=" + author;
    }
}
