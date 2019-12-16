package com.team.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String firstName;
    private String lastName;
//    private String team;

//    public PersonDTO(String firstName, String lastName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//    }
}
