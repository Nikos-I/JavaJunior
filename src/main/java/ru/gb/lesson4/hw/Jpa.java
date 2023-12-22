package ru.gb.lesson4.hw;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class Jpa {

    public static void jpaWithBooks() {
        Connector connector=new Connector();
        Session session=connector.getSession();
        Book book;
        Author author;

        for (int i=11; i <=20 ; i++) {
            Transaction tx=session.beginTransaction();
            author=new Author(String.format("Автор_%d", i));
            session.persist(author);
            book=new Book(String.format("Книга_%d", i), author);
            session.persist(book);
            tx.commit();
        }
        session.close();
    }
}

