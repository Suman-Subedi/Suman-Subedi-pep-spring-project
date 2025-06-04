package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        // Validate Message Text
        if(message.getMessageText() == null || message.getMessageText().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message text cannot be blank.");
        }

        if(message.getMessageText().length() > 255){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message text cannot exceed 255 characters.");
        }

        //Validate User exists
        if(!accountRepository.existsById(message.getPostedBy())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }   

        //Create and Save the message
        return messageRepository.save(message);
    }

    // get All message from the database
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }


    // get message by Id from the database
    public Message getMessageById(Integer messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    // Delete Message by Id from the database
    
    public Integer deleteMessageById(Integer messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }

        return null;
    }


}
