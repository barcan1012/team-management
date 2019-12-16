package com.team.service;

import com.team.exception.ResourceNotFoundException;
import com.team.model.Person;
import com.team.model.Team;
import com.team.model.dto.PersonDTO;
import com.team.model.dto.TeamDTO;
import com.team.repository.PersonRepository;
import com.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private PersonRepository personRepository;

    public TeamService(TeamRepository teamRepository, PersonRepository personRepository) {
        this.teamRepository = teamRepository;
        this.personRepository = personRepository;
    }

    public TeamDTO retrieveTeam(Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (!teamOptional.isPresent()) {
            throw new ResourceNotFoundException("The team with id " + id + " does not exist");
        }

        return teamOptional.map(team -> new TeamDTO(team.getName(), team.getMembers())).orElse(null);
    }

    public TeamDTO save(TeamDTO team) {
        List<Team> teams = new ArrayList<>();
        Team savedTeam = teamRepository.save(new Team(team.getName()));
        return team;
    }

    public void delete(Long teamId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            throw new ResourceNotFoundException("The team with id " + teamId + " does not exist");
        }
        teamRepository.deleteById(teamId);
    }

    public List<TeamDTO> retrieveAll() {

        List<TeamDTO> teams = new ArrayList<>();

        Iterator<Team> teamsIterator = teamRepository.findAll().iterator();

        while (teamsIterator.hasNext()) {
            Team currentTeam = teamsIterator.next();

            teams.add(new TeamDTO(
                    currentTeam.getName(),
                    currentTeam.getMembers()));
        }
        return teams;
    }

    public TeamDTO addNewTeamMember(Long teamId, PersonDTO newPerson) {
        TeamDTO teamDTO = new TeamDTO();

        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            throw new ResourceNotFoundException("The team with id " + teamId + " does not exist");
        }

        Team team = teamOptional.get();
        team.getMembers().add(new Person(newPerson.getFirstName(), newPerson.getLastName(), team.getName()));

        teamDTO.setName(team.getName());

        teamRepository.save(team);
        teamDTO.setMembers(team.getMembers());
        return teamDTO;
    }

    public TeamDTO addExistingTeamMember(Long teamId, Long personId) {
        TeamDTO teamDTO = new TeamDTO();
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            throw new ResourceNotFoundException("The team with id " + teamId + " does not exist");
        }
        Team team = teamOptional.get();
        Optional<Person> personOptional = personRepository.findById(personId);

        if (!personOptional.isPresent()) {
            throw new ResourceNotFoundException("The person with id " + personId + " does not exist");
        }
        teamDTO.setName(team.getName());
        Person person = personOptional.get();
        person.setTeam(team.getName());
        personRepository.save(person);
        team.getMembers().add(person);
        teamRepository.save(team);
        teamDTO.setMembers(team.getMembers());
        return teamDTO;
    }

    public void deleteTeamMember(Long teamId, Long personId) {
        Optional<Team> teamOptional = teamRepository.findById(teamId);
        if (!teamOptional.isPresent()) {
            throw new ResourceNotFoundException("The team with id " + teamId + " does not exist");
        }
        Team team = teamOptional.get();
        Optional<Person> personOptional = personRepository.findById(personId);

        if (!personOptional.isPresent()) {
            throw new ResourceNotFoundException("The person with id " + personId + " does not exist");
        }

        Person person = personOptional.get();
        team.getMembers().remove(person);
        teamRepository.save(team);
    }
}
