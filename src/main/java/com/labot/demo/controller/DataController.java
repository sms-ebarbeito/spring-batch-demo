package com.labot.demo.controller;

import com.labot.demo.entity.Import;
import com.labot.demo.entity.Person;
import com.labot.demo.repository.jpa.ImportRepository;
import com.labot.demo.repository.jpa.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DataController {

    private final ImportRepository importRepository;
    private final PersonRepository personRepository;

    public DataController(@Autowired ImportRepository importRepository,
                          @Autowired PersonRepository personRepository) {
        this.importRepository = importRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/import")
    public List<Import> getImports() {
        return importRepository.findAll();
    }

    @GetMapping("/person")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }
}
