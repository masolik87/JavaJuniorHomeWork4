package org.example;

import com.mysql.cj.xdevapi.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/*
Создайте базу данных (например, SchoolDB).
В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
Настройте Hibernate для работы с вашей базой данных.
Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
Убедитесь, что каждая операция выполняется в отдельной транзакции.
*/

public class Main {
    private final static Random random = new Random();
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "password";

// Подключение к базе
        try {
            Connection connection = DriverManager.getConnection(url, user, password);

//Создание базы данных
            createDatabase(connection);
            System.out.println("Database created");

//Использование базы данных
            useDatabase(connection);
            System.out.println("Use database");

//Создание таблицы
            createTable(connection);
            System.out.println("Create table");

//Вставка данных
/*            int count = random.nextInt(5, 11);
            for (int i = 0; i < count; i++) {
                insertData(connection, Students.create());
            }
            System.out.println("Insert data");
 */

//Чтение данных
            Collection<Students> students = readData(connection);
            for(var student: students)
                System.out.println(student);
            System.out.println("Read data");

//Обновление данных
            for(var student: students) {
                student.updateTitle();
                student.updateDuration();
                updateData(connection,student);
            }
            System.out.println("Update data");

//Удаление данных
            for(var student: students)
                deleteData(connection, student.getId());
            System.out.println("Delete data");

//Закрытие соединения
            connection.close();
            System.out.println("Database connection close");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

//Создание базы данных
    private static void createDatabase(Connection connection) throws SQLException{
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS SchoolDB;";
        PreparedStatement statement = connection.prepareStatement(createDatabaseSQL);
        statement.execute();
    }

//Использование базы данных
    private static void useDatabase(Connection connection) throws SQLException{
        String  useDatabaseSQL = "USE SchoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)){
            statement.execute();
        }
    }

//Создание таблицы
    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Courses (id INT AUTO_INCREMENT " +
                "PRIMARY KEY, title VARCHAR(255), duration INT);";
        try (PreparedStatement statement =  connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

//Вставка данных
    private static void insertData(Connection connection, Students students) throws SQLException {
        String insertDataSQL = "INSERT INTO Courses(title, duration) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, students.getTitle());
            statement.setInt(2, students.getDuration());
            statement.executeUpdate();
        }
    }

//Чтение данных
    private static Collection<Students> readData(Connection connection) throws SQLException {
        ArrayList<Students> studentsList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM Courses;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                studentsList.add(new Students(id, title, duration));
            }
            return studentsList;
        }
    }
//Обновление данных
    private static void updateData(Connection connection, Students students) throws SQLException {
        String updateDataSQL = "UPDATE Courses SET title=?, duration=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setString(1, students.getTitle());
            statement.setInt(2, students.getDuration());
            statement.setInt(3, students.getId());
            statement.executeUpdate();
        }
    }

//Удаление данных
    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM Courses WHERE id=?;";
        try(PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setLong(1, id);
            statement.execute();
        }
    }
}