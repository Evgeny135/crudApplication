package org.application.dao;

import org.application.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private List<Person> people = null;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/mvc_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Person person){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person(name, age, email) VALUES (?,?,?)");
            preparedStatement.setString(1,person.getName());
            preparedStatement.setLong(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());

           preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Person> index(){
        people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet =  statement.executeQuery("SELECT * FROM person ORDER BY id ");

            while (resultSet.next()){
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public Person getPerson(int id){
        Person person= null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            person=new Person();
            while (resultSet.next()){
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    public void  update(int id, Person person){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person SET name=?,age=?,email=? WHERE id=?");
            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());
            preparedStatement.setInt(4,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person WHERE id=?");
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
