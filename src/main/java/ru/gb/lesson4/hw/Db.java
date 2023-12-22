package ru.gb.lesson4.hw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** @noinspection SqlDialectInspection*/
public class Db {
    static public final String URL="jdbc:mysql://localhost:3306";
    static public final String USER="yn";
    static public final String PASSWORD="123456";
    static public final int COUNT_BOOKS=10;

    public static void CreateDb() {
        Connection con;
        try {
            con=DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement=con.createStatement();
            statement.execute("DROP SCHEMA IF EXISTS hw4;");
            statement.execute("CREATE SCHEMA hw4;");
            statement.execute("USE hw4;");
// * 1.1 Создать таблицу book с колонками id bigint, name varchar, author varchar, ...
            statement.execute("DROP TABLE IF EXISTS authors;");
            String execStr="CREATE TABLE authors(id BIGINT NOT NULL AUTO_INCREMENT, authorname VARCHAR(45) NOT NULL, " +
                    "PRIMARY KEY (id))";
            statement.execute(execStr);

            statement.execute("DROP TABLE IF EXISTS books;");
            execStr="CREATE TABLE books(id bigint NOT NULL AUTO_INCREMENT, name varchar(50) NOT NULL, " +
                    " author varchar(50) DEFAULT NULL,authorId bigint DEFAULT NULL, " +
                    "PRIMARY KEY (id), CONSTRAINT authorFK FOREIGN KEY (authorId) REFERENCES authors(id))";
            statement.execute(execStr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

