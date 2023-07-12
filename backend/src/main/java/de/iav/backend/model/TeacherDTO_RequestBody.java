package de.iav.backend.model;

public record TeacherDTO_RequestBody(String loginName,
                                     String firstName,
                                     String lastName,
                                     String email) {
}
