package com.splitwise.Splitwise.controller;


import com.splitwise.Splitwise.dto.*;
import com.splitwise.Splitwise.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settlements")
public class SettlementController {

    @Autowired
    private SettlementService settlementService;

    @PostMapping
    public ResponseEntity<SettlementDTO> createSettlement(@RequestBody SettlementDTO settlementDTO) {
        return new ResponseEntity<>(settlementService.createSettlement(settlementDTO), HttpStatus.CREATED);
    }
}