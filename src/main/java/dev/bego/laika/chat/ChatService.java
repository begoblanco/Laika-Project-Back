package dev.bego.laika.chat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.bego.laika.users.User;
import dev.bego.laika.users.UserService;

@Service
public class ChatService {

    @Autowired
    private UserService userService;

    public ResponseDto sendMessage(MessageDto messageDto, Long userId) {
        try {
            
            Optional<User> user = userService.findById(userId);
            InformedMessageDto informedMessageDto = new InformedMessageDto(user.get().getId(), user.get().getUsername(),
                    messageDto.getMessage(),
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            
            ObjectMapper objectMapper = new ObjectMapper();

            String request_json = objectMapper.writeValueAsString(informedMessageDto);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:15000" + "/chat"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(request_json))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String response_json = response.body();
                
                ResponseDto responseDto = objectMapper.readValue(response_json, ResponseDto.class);
                return responseDto;
            } else {
                return new ResponseDto("sad",
                        "An error occurred while processing the request. Response code: " + response.statusCode());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            return new ResponseDto("sad",
                    "An error occurred while processing the request. Exception: " + ExceptionUtils.getStackTrace(e));
        }
    }

}
