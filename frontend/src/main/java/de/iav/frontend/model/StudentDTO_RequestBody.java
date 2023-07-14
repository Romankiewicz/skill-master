package de.iav.frontend.model;

public record StudentDTO_RequestBody(
        String loginName,
        String firstName,
        String lastName,
        String email){
}
