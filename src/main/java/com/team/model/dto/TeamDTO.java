package com.team.model.dto;

import com.team.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TeamDTO {
    private String name;
    private Set<Person> members;
}
