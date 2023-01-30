package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> listOfPersonDB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {

        listOfPersonDB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return listOfPersonDB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return listOfPersonDB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int UpdatePersonById(UUID id, Person newName) {
        return selectPersonById(id)
                .map(oldPerson -> {
                    int indexOfPersonToUpdate = listOfPersonDB.indexOf(oldPerson);
                    if (indexOfPersonToUpdate >= 0) {
                        listOfPersonDB.set(indexOfPersonToUpdate, new Person(id, newName.getName()));
                        return 1;
                    } else {
                        return 0;
                    }
                } )
                .orElse(0);
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if (person.isEmpty()) {
            return 0;
        } else {
            listOfPersonDB.remove(person.get());
            return 1;
        }
    }
}
