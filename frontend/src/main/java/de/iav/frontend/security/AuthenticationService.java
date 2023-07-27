package de.iav.frontend.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class AuthenticationService {

    private String username;
    private String sessionId;
    private String errorMessage;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String STUDENTS_URL_BACKEND = System.getenv("BACKEND_STUDENT_URI");

    private static AuthenticationService INSTANCE;

    private AuthenticationService() {
    }

    public static synchronized AuthenticationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthenticationService();
        }
        return INSTANCE;
    }

    public boolean createNewTeacher(String email, String username, String password) {
        try {
            AppUserRequest appUserRequest = new AppUserRequest(username, password, email);
            String requestBody = objectMapper.writeValueAsString(appUserRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STUDENTS_URL_BACKEND + "/api/auth/register/teacher"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.join().statusCode();

            return handleStatusCheckAndSetSessionId(statusCode, response, "Registration failed. Email duplicate?");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createNewStudent(String email, String username, String password) {
        try {
            AppUserRequest appUserRequest = new AppUserRequest(username, password, email);
            String requestBody = objectMapper.writeValueAsString(appUserRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(STUDENTS_URL_BACKEND + "/api/auth/register/student"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.join().statusCode();

            return handleStatusCheckAndSetSessionId(statusCode, response, "Registration failed. Email duplicate?");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean login(String username, String password) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/auth/login"))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .build();

        var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.join().statusCode();

        return handleStatusCheckAndSetSessionId(statusCode, response, "Login failed. Username or password is wrong!");
    }

    public boolean logout() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(STUDENTS_URL_BACKEND + "/api/users/logout"))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .header("Cookie", "JSESSIONID=" + sessionId)
                .build();

        var response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.join().statusCode();

        if (statusCode == 200) {
            return true;
        } else {
            if (statusCode == 401) {
                setErrorMessage("Logout failed");
            } else {
                setErrorMessage("Something went wrong");
            }
            return false;
        }
    }

    private boolean handleStatusCheckAndSetSessionId(int statusCode, CompletableFuture<HttpResponse<String>> response, String errorMessage) {
        if (statusCode == 200) {
            setUsername(response.join().body());
            String responseSessionId = response.join().headers().firstValue("Set-Cookie").orElseThrow();
            setSessionId(responseSessionId.substring(11, responseSessionId.indexOf(";")));
            return true;
        } else {
            if (statusCode == 401) {
                setErrorMessage(errorMessage);
            } else {
                setErrorMessage("Something went wrong");
            }
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpClient getClient(){return this.client;}
}
