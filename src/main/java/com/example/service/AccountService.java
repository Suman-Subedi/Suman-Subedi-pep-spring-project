package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account){
        //Validate username is not blank
        if(account.getUsername() == null || account.getUsername().trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be blank.");
        }
        //Validate password length
        if(account.getPassword() == null || account.getPassword().length() < 4){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 4 characters long.");
        }
        //Check for duplicate username
        if(accountRepository.existsByUsername(account.getUsername())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username alrady exists.");
        }

        return accountRepository.save(account);
    }

    public Account login(Account loginRequest){
        //Find accouny by Username
        Account account = accountRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials"));

        // Verify password matches
        if(!account.getPassword().equals(loginRequest.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
        }
        
        return account;
    }

    
}
