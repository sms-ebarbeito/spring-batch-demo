package com.labot.demo.controller;

import com.labot.demo.entity.Import;
import com.labot.demo.entity.Person;
import com.labot.demo.repository.jpa.ImportRepository;
import com.labot.demo.repository.jpa.PersonRepository;
import com.labot.demo.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DataController {

    private final DataService dataService;

    public DataController(@Autowired DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/import")
    public List<Import> getImports() {
        return dataService.getImports();
    }

    @GetMapping("/person")
    public List<Person> getPersons() {
        return dataService.getPersons();
    }
}
