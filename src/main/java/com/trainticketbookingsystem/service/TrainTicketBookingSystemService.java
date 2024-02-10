package com.trainticketbookingsystem.service;


import com.trainticketbookingsystem.domain.PurchaseTicketRequest;
import com.trainticketbookingsystem.domain.Ticket;
import com.trainticketbookingsystem.domain.User;
import com.trainticketbookingsystem.exception.InvalidSectionException;
import com.trainticketbookingsystem.exception.NoUserFoundInTheSectionException;
import com.trainticketbookingsystem.exception.UserNotFoundException;
import com.trainticketbookingsystem.persistance.TrainTicketRepository;
import com.trainticketbookingsystem.persistance.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrainTicketBookingSystemService {

    private final String USER_REMOVED = "User Removed Successfully";


    private final TrainTicketRepository trainTicketRepository;
    private final UserRepository userRepository;

    public TrainTicketBookingSystemService(TrainTicketRepository trainTicketRepository, UserRepository userRepository) {
        this.trainTicketRepository = trainTicketRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public Ticket purchaseTicket(PurchaseTicketRequest request) {
        log.info("*************** Purchase Ticket Service Started ***************");
        User existingUser = userRepository.findByEmail(request.getUser().getEmail());
        String section = (trainTicketRepository.count() % 2 == 0) ? "A" : "B";
        if (existingUser != null) {
            Ticket ticket = Ticket.builder()
                    .fromLocation(request.getFrom())
                    .toLocation(request.getTo())
                    .price(request.getPrice())
                    .section(section)
                    .user(existingUser)
                    .build();
            trainTicketRepository.save(ticket);
            log.info("*************** Purchase Ticket Service Completed ***************");
            return ticket;
        } else {
            User newUser = request.getUser();
            userRepository.save(newUser);
            Ticket ticket = Ticket.builder()
                    .fromLocation(request.getFrom())
                    .toLocation(request.getTo())
                    .price(request.getPrice())
                    .section(section)
                    .user(newUser)
                    .build();
            trainTicketRepository.save(ticket);
            log.info("*************** Purchase Ticket Service Completed ***************");
            return ticket;
        }
    }


    public Ticket receiptDetails(Long id) {
        log.info("*************** Get Receipt Details Started ***************");
        Optional<Ticket> getReceiptDetails = trainTicketRepository.findById(id);
        log.info("*************** Get Receipt Details Completed ***************");
        return getReceiptDetails.orElseThrow(() -> new UserNotFoundException("Ticket with ID " + id + " not found"));
    }

    @Transactional
    public List<User> getUsersByAllocatedSeat(String section) {
        if(section.equalsIgnoreCase("A") || section.equalsIgnoreCase("B")) {
            log.info("*************** Get User Details by Seat Started ***************");
            List<Ticket> allTickets = trainTicketRepository.findAll();
            List<Ticket> ticketsInRequestedSection = allTickets.stream()
                    .filter(ticket -> ticket.getSection().equalsIgnoreCase(section))
                    .toList();

            List<User> list = ticketsInRequestedSection.stream()
                    .map(Ticket::getUser)
                    .collect(Collectors.toList());
            log.info("*************** Get User Details by Seat Completed ***************");
            if (!list.isEmpty()) {
                return list;
            } else {
                throw new NoUserFoundInTheSectionException("No Users Found in Section " + section);
            }
        }else {
            throw new InvalidSectionException("Invalid Section, Section Should be Either A or B");
        }
    }

    @Transactional
    public String removeUser(Long id) {
        log.info("*************** Remove User Details Started ***************");
        Optional<User> getUserDetails = userRepository.findById(id);
        if(getUserDetails.isPresent()){
            trainTicketRepository.deleteByUser(getUserDetails.get());
            userRepository.delete(getUserDetails.get());
            log.info("*************** Remove User Details Completed ***************");
            return USER_REMOVED;
        }else{
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    @Transactional
    public Ticket modifySeat(Long id, String section) {
        log.info("*************** Modify User Details Started ***************");
        Ticket getTicketDetails = trainTicketRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        getTicketDetails.setSection(section);
        log.info("*************** Modify User Details Completed ***************");
        return trainTicketRepository.save(getTicketDetails);
    }
}
