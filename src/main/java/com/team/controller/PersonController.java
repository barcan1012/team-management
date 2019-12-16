package com.team.controller;

import com.team.model.dto.PersonDTO;
import com.team.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/persons")
public class PersonController {
    private PersonService personService;

    public PersonController(PersonService personService) {

        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity retrievePerson(@PathVariable Long id){
        if(personService.retrievePerson(id)==null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(personService.retrievePerson(id));
    }


    @PostMapping
    public ResponseEntity savePerson(@RequestBody PersonDTO person){
        PersonDTO savedPerson = personService.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deletePerson(@PathVariable Long id){
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAllPersons(){
        return ResponseEntity.ok(personService.retrieveAll());
    }
}


