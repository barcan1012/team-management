package com.team.controller;

import com.team.model.dto.PersonDTO;
import com.team.model.dto.TeamDTO;
import com.team.service.PersonService;
import com.team.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/teams")
public class TeamController {
    private TeamService teamService;
    private PersonService personService;

    public TeamController(TeamService teamService, PersonService personService) {
        this.teamService = teamService;
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity retrieveTeam(@PathVariable Long id) {
        if (teamService.retrieveTeam(id) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(teamService.retrieveTeam(id));
    }

    @GetMapping
    public ResponseEntity getAllTeams() {
        return ResponseEntity.ok(teamService.retrieveAll());
    }

    @PostMapping
    public ResponseEntity saveTeam(@RequestBody TeamDTO team) {
        TeamDTO savedTeam = teamService.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeam);
    }

    @PatchMapping("/{id}")
    public ResponseEntity addNewTeamMember(@PathVariable Long id, @RequestBody PersonDTO person) {
        if (teamService.retrieveTeam(id) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(teamService.addNewTeamMember(id, person));
    }

    @PatchMapping("/{teamId}/addPerson/{personId}")
    public ResponseEntity addExistingTeamMember(@PathVariable Long teamId, @PathVariable Long personId) {
        if (teamService.retrieveTeam(teamId) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);

        if (personService.retrievePerson(personId) == null) return new ResponseEntity(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(teamService.addExistingTeamMember(teamId,personId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTeam(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/removePerson/{personId}")
    public ResponseEntity deleteTeamMember(@PathVariable Long id, @PathVariable Long personId) {
        if (teamService.retrieveTeam(id) == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        teamService.deleteTeamMember(id, personId);
        return ResponseEntity.ok().build();
    }

}

