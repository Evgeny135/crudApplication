package org.application.dao;

import org.application.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private List<Person> people;
    private int ID=0;

    {
        people = new ArrayList<>();

        people.add(new Person(++ID,"Zheka","trish@bk.ru",19));
        people.add(new Person(++ID,"Andrew","andreyka@bk.ru",23));
        people.add(new Person(++ID,"Pavel","pashka@mail.ru",23));
    }

    public void save(Person person){
        person.setId(++ID);
        people.add(person);
    }

    public List<Person> index(){
        return people;
    }

    public Person getPerson(int id){
        return people.stream().filter(person -> person.getId()==id).findAny()
                .orElse(null);
    }

    public void  update(int id, Person person){
        Person personToBeUpdated = getPerson(id);
        personToBeUpdated.setName(person.getName());
        personToBeUpdated.setAge(person.getAge());
        personToBeUpdated.setEmail(person.getEmail());
    }

    public void delete(int id){
        people.removeIf(person -> person.getId()==id);
    }
}
