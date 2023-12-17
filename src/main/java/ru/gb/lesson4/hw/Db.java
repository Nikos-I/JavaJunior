package ru.gb.lesson4.hw;

import java.sql.*;

public class Db {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "yn";
    private static final String PASSWORD = "123456";
    private static final int COUNT_BOOKS = 10;

    public static void workWithBooks(){
        Connection con;
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement=con.createStatement();
            statement.execute("DROP SCHEMA IF EXISTS hw4;");
            statement.execute("CREATE SCHEMA hw4;");
            statement.execute("USE hw4;");
// * 1.1 Создать таблицу book с колонками id bigint, name varchar, author varchar, ...
            statement.execute("DROP TABLE IF EXISTS books;");
            statement.execute("CREATE TABLE books(id bigint NOT NULL , name varchar(50) NOT NULL , author varchar(50), PRIMARY KEY (id));");
// * 1.2 Добавить в таблицу 10 книг
            String execStr;
            for (int i=0; i < COUNT_BOOKS; i++) {
                execStr = String.format("INSERT books(id, name, author) VALUES (%d, 'Книга_%d', 'Автор_%d')", i, i, i);
                statement.execute(execStr);
            }
// * 1.3 Сделать запрос select from book where author = 'какое-то имя' и прочитать его с помощью ResultSet
            int au = 5;
            execStr = String.format("SELECT id, name, author FROM books WHERE author = 'Автор_%d'", au);
            ResultSet res = statement.executeQuery(execStr);
            while (res.next()){
                System.out.printf("Для author = 'Автор %d': %d %s %s",
                        au, res.getInt(1), res.getString(2),  res.getString(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
