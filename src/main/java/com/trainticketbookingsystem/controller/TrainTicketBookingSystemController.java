package com.trainticketbookingsystem.controller;


import com.trainticketbookingsystem.constants.TrainTicketBookingSystemParams;
import com.trainticketbookingsystem.domain.PurchaseTicketRequest;
import com.trainticketbookingsystem.domain.Ticket;
import com.trainticketbookingsystem.domain.User;
import com.trainticketbookingsystem.service.TrainTicketBookingSystemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = TrainTicketBookingSystemParams.TRAIN_TICKET_BOOKING_MAPPER)
public class TrainTicketBookingSystemController {

    private final TrainTicketBookingSystemService trainTicketBookingSystemService;

    @Autowired
    public TrainTicketBookingSystemController(TrainTicketBookingSystemService trainTicketBookingSystemService) {
        this.trainTicketBookingSystemService = trainTicketBookingSystemService;
    }

    @PostMapping(value = TrainTicketBookingSystemParams.TRAIN_TICKET_PURCHASE_TICKETS)
    public ResponseEntity<Ticket> purchaseTicket(@Valid  @RequestBody PurchaseTicketRequest request) {
        return ResponseEntity.ok(trainTicketBookingSystemService.purchaseTicket(request));
    }


    @GetMapping(value = TrainTicketBookingSystemParams.RECEIPT_DETAILS)
    public ResponseEntity<Ticket> readProduct(@PathVariable Long id){
        return ResponseEntity.ok(trainTicketBookingSystemService.receiptDetails(id));
    }

    @GetMapping(value = TrainTicketBookingSystemParams.USER_DETAILS_BY_SECTION)
    public ResponseEntity<List<User>> usersByAllocatedSeat(@PathVariable String section){
        return ResponseEntity.ok(trainTicketBookingSystemService.getUsersByAllocatedSeat(section));
    }

    @DeleteMapping(value = TrainTicketBookingSystemParams.REMOVE_USER)
    public ResponseEntity<String> removeUser(@PathVariable Long id){
        return ResponseEntity.ok(trainTicketBookingSystemService.removeUser(id));
    }

    @PatchMapping(value = TrainTicketBookingSystemParams.MODIFY_USER_SEAT)
    public ResponseEntity<Ticket> modifySeat(@PathVariable Long id, @RequestParam(required = false) String section){
        return ResponseEntity.ok(trainTicketBookingSystemService.modifySeat(id,section));
    }

}