package dev.bego.laika.chat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import dev.bego.laika.users.User;
import dev.bego.laika.users.UserService;

class ChatServiceTest {

    @InjectMocks
    private ChatService chatService;

    @Mock
    private UserService userService;

    @Mock
    private HttpClient httpClient;

    private User user;
    private MessageDto messageDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = User.builder().id(1L).username("testUser").build();
        messageDto = new MessageDto();
        messageDto.setMessage("Hello, world!");
    }
       

    @Test
    void testSendMessage_HttpClientThrowsIOException() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(httpClient.send(any(HttpRequest.class), any())).thenThrow(new IOException("Network error"));

        ResponseDto responseDto = chatService.sendMessage(messageDto, 1L);

        assertEquals("sad", responseDto.getEmotion());
        assertFalse(responseDto.getMessage().contains("Network error"));
        verify(userService, times(1)).findById(1L);
    }

    @Test
    void testSendMessage_HttpResponseNotOk() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(user));
        @SuppressWarnings("unchecked")
        HttpResponse<Object> httpResponse = mock(HttpResponse.class);
        when(httpClient.send(any(HttpRequest.class), any())).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(500);

        ResponseDto responseDto = chatService.sendMessage(messageDto, 1L);

        assertEquals("sad", responseDto.getEmotion());
        assertFalse(responseDto.getMessage().contains("Response code: 500"));
        verify(userService, times(1)).findById(1L);
    }
}

