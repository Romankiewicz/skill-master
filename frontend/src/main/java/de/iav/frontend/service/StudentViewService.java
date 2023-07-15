package de.iav.frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Student;

import java.net.http.HttpClient;

public class StudentViewService {

    private static StudentViewService instance;
    private final HttpClient studentClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StudentViewService() {
    }

    public static synchronized StudentViewService getInstance(){
        if(instance == null){
            instance = new StudentViewService();
        }
        return instance;
    }


}
