package dev.bego.laika.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.bego.laika.users.User;

@Controller
@RequestMapping(path = "${api-endpoint}/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("")
    public ResponseEntity<ResponseDto> createNote(@RequestBody MessageDto messageDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        ResponseDto response = chatService.sendMessage(messageDto, currentUser.getId());
        return ResponseEntity.ok(response);
    }
}
