package ru.gb.lesson4.hw;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authorname")
    private String authorName;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<Book> books;

    public Author(String authorN){
        this.authorName=authorN;
    }

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorN) {
        this.authorName= authorN;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", books= " + books +
                '}';
    }
}