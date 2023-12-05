package com.blockchain.controller;

import com.blockchain.domain.ResultDTO;
import com.blockchain.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blockchain")
public class BlockchainController {

    private final BlockchainService service;

    @PostMapping("/send")
    public ResponseEntity<ResultDTO> criar(
            @Valid
            @RequestParam(value = "value", required = true) final Double value,
            @RequestParam(value = "senderAddress", required = true) final String senderAddress,
            @RequestParam(value = "recipientAddress", required = true)final String recipientAddress) {

        return ResponseEntity.status(HttpStatus.OK).body(service.send(value, senderAddress, recipientAddress));
    }

}
