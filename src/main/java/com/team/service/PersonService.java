package com.team.service;

import com.team.exception.ResourceNotFoundException;
import com.team.model.Person;
import com.team.model.dto.PersonDTO;
import com.team.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDTO retrievePerson(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (!personOptional.isPresent()) {
            throw new ResourceNotFoundException("The person with id " + personId + " does not exist");
        }
        return personOptional.map(person -> new PersonDTO(person.getFirstName(), person.getLastName())).orElse(null);
    }

    public PersonDTO save(PersonDTO person) {
        Person savedPerson = personRepository.save(new Person(person.getFirstName(), person.getLastName()));

        return new PersonDTO(savedPerson.getFirstName(), savedPerson.getLastName());
    }

    public void delete(Long personId) {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (!personOptional.isPresent()) {
            throw new ResourceNotFoundException("The person with id " + personId + " does not exist");
        }
        personRepository.deleteById(personId);
    }

    public List<PersonDTO> retrieveAll() {
        Iterator<Person> personsIterator = personRepository.findAll().iterator();

        List<PersonDTO> persons = new ArrayList<>();
        while (personsIterator.hasNext()) {
            Person currentPerson = personsIterator.next();
            persons.add(new PersonDTO(
                    currentPerson.getFirstName(),
                    currentPerson.getLastName()));
        }
        return persons;
    }
}
