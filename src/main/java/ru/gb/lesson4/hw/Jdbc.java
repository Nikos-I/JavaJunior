package ru.gb.lesson4.hw;


import java.sql.*;

public class Jdbc {
//    static private final String URL = "jdbc:mysql://localhost:3306";
//    static private final String USER = "yn";
//    static private final String PASSWORD = "123456";
//    static private final int COUNT_BOOKS = 10;

    public static void workWithBooks(){
        Connection con;
        try {
            con = DriverManager.getConnection(Db.URL, Db.USER, Db.PASSWORD);
            Statement statement=con.createStatement();
            statement.execute("USE hw4;");

// * 1.2 Добавить в таблицу 10 книг
            String execStr;
            for (int i=1; i <= Db.COUNT_BOOKS; i++) {
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
