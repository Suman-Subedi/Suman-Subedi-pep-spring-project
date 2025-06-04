package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.List;
import java.util.Map;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;
    
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // User registration
    @PostMapping("/register")
    public Account registerUser(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    // User Login
    @PostMapping("/login")
    public Account login(@RequestBody Account loginRequest){
        return accountService.login(loginRequest);
    }
    //Create Message
    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message){
        return messageService.createMessage(message);
    }
    //Fetch Message
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessage(){
        List <Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }
    // Fetch Messages matching messageId
    @GetMapping("/messages/{messageId}")
    public ResponseEntity <Message> getMessageById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    // Delete Message by ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity <Integer> deleteMessageById(@PathVariable Integer messageId){
        Integer deleteCount = messageService.deleteMessageById(messageId);
        return ResponseEntity.ok(deleteCount);
    }

    // Updating Message
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageText(@PathVariable Integer messageId, @RequestBody Map<String, String> request){
        String newMessageText = request.get("messageText");
        int updatedCount = messageService.updateMessageText(messageId, newMessageText);

        return ResponseEntity.ok(updatedCount);
    }

    //Retrieve all the messages by User Id
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByUser(@PathVariable Integer accountId){
        List<Message> messages = messageService.getMessageByUser(accountId);
        return ResponseEntity.ok(messages);
    }
  

}
