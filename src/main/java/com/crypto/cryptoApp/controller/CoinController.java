package com.crypto.cryptoApp.controller;

import com.crypto.cryptoApp.entity.Coin;
import com.crypto.cryptoApp.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Timestamp;

@RestController
@RequestMapping("/coin")
public class CoinController {
    @Autowired
    private CoinRepository repository;

    @PostMapping()
    public ResponseEntity post(@RequestBody Coin coin){
        try {
            coin.setDateTime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(repository.insert(coin), HttpStatus.CREATED);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}