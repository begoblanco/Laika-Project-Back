package dev.bego.laika.chat;

import dev.bego.laika.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    @Mock
    private ChatService chatService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ChatController chatController;

    private User mockUser;
    private MessageDto mockMessage;
    private ResponseDto mockResponse;

    @BeforeEach
    void setUp() {
  
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");


        mockMessage = new MessageDto();
        mockMessage.setMessage("Hello, world!");


        mockResponse = new ResponseDto();
        mockResponse.setMessage("Message sent");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreateNote_Success() {
    
        when(chatService.sendMessage(mockMessage, mockUser.getId())).thenReturn(mockResponse);

       
        ResponseEntity<ResponseDto> response = chatController.createNote(mockMessage);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse.getMessage(), response.getBody().getMessage());

        verify(chatService, times(1)).sendMessage(mockMessage, mockUser.getId());
    }
}
