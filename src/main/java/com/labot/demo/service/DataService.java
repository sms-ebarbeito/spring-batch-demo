package com.labot.demo.service;

import com.labot.demo.entity.Import;
import com.labot.demo.entity.Person;
import com.labot.demo.repository.jpa.ImportRepository;
import com.labot.demo.repository.jpa.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private final ImportRepository importRepository;
    private final PersonRepository personRepository;

    public DataService(@Autowired ImportRepository importRepository,
                       @Autowired PersonRepository personRepository) {
        this.importRepository = importRepository;
        this.personRepository = personRepository;
    }

    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    public List<Import> getImports() {
        return importRepository.findAll();
    }
}
