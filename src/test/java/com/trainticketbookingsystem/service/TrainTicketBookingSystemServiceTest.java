package com.trainticketbookingsystem.service;

import com.trainticketbookingsystem.PayloadUtil;
import com.trainticketbookingsystem.domain.PurchaseTicketRequest;
import com.trainticketbookingsystem.domain.Ticket;
import com.trainticketbookingsystem.domain.User;
import com.trainticketbookingsystem.exception.NoUserFoundInTheSectionException;
import com.trainticketbookingsystem.exception.UserNotFoundException;
import com.trainticketbookingsystem.persistance.TrainTicketRepository;
import com.trainticketbookingsystem.persistance.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TrainTicketBookingSystemServiceTest {
    private final String USER_REMOVED = "User Removed Successfully";

    @InjectMocks
    private TrainTicketBookingSystemService trainTicketBookingSystemService;

    @Mock
    private  UserRepository userRepository;
    @Mock
    private TrainTicketRepository trainTicketRepository;


    @Test
    public void testPurchaseTicket_existingUser() {
        when(userRepository.findByEmail("johncena@gmail.com")).thenReturn(PayloadUtil.buildUser());
        Ticket ticket = trainTicketBookingSystemService.purchaseTicket(PayloadUtil.buildTicketRequest());
        verify(trainTicketRepository, times(1)).save(any(Ticket.class));
        assertEquals(PayloadUtil.buildUser(), ticket.getUser());
        assertEquals("Paris", ticket.getFromLocation());
        assertEquals("London", ticket.getToLocation());
        assertEquals(20.0, ticket.getPrice());
    }

    @Test
    public void testPurchaseTicket_newUser() {
        when(userRepository.findByEmail("johncena@gmail.com")).thenReturn(null);
        when(userRepository.save(any())).thenReturn(PayloadUtil.buildUser());
        Ticket ticket = trainTicketBookingSystemService.purchaseTicket(PayloadUtil.buildTicketRequest());
        verify(trainTicketRepository, times(1)).save(any(Ticket.class));
        assertEquals(PayloadUtil.buildUser(), ticket.getUser());
        assertEquals("Paris", ticket.getFromLocation());
        assertEquals("London", ticket.getToLocation());
        assertEquals(20.0, ticket.getPrice());
    }


    @Test
    public void testReceiptDetails() {
        when(trainTicketRepository.findById(100L)).thenReturn(Optional.ofNullable(PayloadUtil.buildTicket()));
        Ticket result = trainTicketBookingSystemService.receiptDetails(100L);
        assertEquals(PayloadUtil.buildTicket(), result);
    }

    @Test
    public void testReceiptDetailsFailure() {
        when(trainTicketRepository.findById(100L)).thenReturn(Optional.empty());
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            trainTicketBookingSystemService.receiptDetails(100L);
        });

        assertEquals("Ticket with ID 100 not found", exception.getMessage());
        verify(trainTicketRepository, times(1)).findById(100L);
    }

    @Test
    public void testGetUsersByAllocatedSeat() {
        List<Ticket> mockTickets = PayloadUtil.buildTicketList();
        lenient().when(userRepository.save(any(User.class))).thenReturn(PayloadUtil.buildMockUser("John", "Doe"))
                .thenReturn(PayloadUtil.buildMockUser("Jane", "Smith"))
                .thenReturn(PayloadUtil.buildMockUser("Michael", "Johnson"));
        lenient().when(trainTicketRepository.findAll()).thenReturn(mockTickets);

        List<User> result = trainTicketBookingSystemService.getUsersByAllocatedSeat("A");

        List<User> expectedUsers = Arrays.asList(PayloadUtil.buildMockUser("John", "Doe"), PayloadUtil.buildMockUser("Michael", "Johnson"));
        assertEquals(expectedUsers, result);
    }

    @Test
    public void testGetUsersByAllocatedSeatFailure() {
        when(trainTicketRepository.findAll()).thenReturn(Collections.emptyList());
        Exception exception = assertThrows(NoUserFoundInTheSectionException.class, () -> trainTicketBookingSystemService.getUsersByAllocatedSeat("A"));
        assertEquals("No Users Found in Section A", exception.getMessage());
    }

    @Test
    public void testRemoveUser_userExists() {
        Long userId = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(PayloadUtil.buildUser()));
        String result = trainTicketBookingSystemService.removeUser(userId);
        verify(trainTicketRepository, times(1)).deleteByUser(PayloadUtil.buildUser());
        verify(userRepository, times(1)).delete(PayloadUtil.buildUser());
        assertEquals("User Removed Successfully", result);

    }

    @Test
    public void testRemoveUser_userNotFound() {
        Long userId = 456L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> trainTicketBookingSystemService.removeUser(userId));
        assertEquals("User with ID 456 not found", exception.getMessage());
    }


    @Test
    public void testModifySeat() {
        Long ticketId = 123L;

        when(trainTicketRepository.findById(ticketId)).thenReturn(Optional.of(PayloadUtil.buildTicket()));
        when(trainTicketRepository.save(any(Ticket.class))).thenReturn(PayloadUtil.buildTicket());


        Ticket result = trainTicketBookingSystemService.modifySeat(ticketId, "A");

        verify(trainTicketRepository, times(1)).findById(ticketId);
        verify(trainTicketRepository, times(1)).save(PayloadUtil.buildTicket());
        assertEquals("A", result.getSection());
    }

    @Test
    public void testModifySeat_UserNotFound() {
       Long ticketId = 456L;
       when(trainTicketRepository.findById(ticketId)).thenReturn(Optional.empty());

       UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            trainTicketBookingSystemService.modifySeat(ticketId, "A");
        });

       assertEquals("User with ID " + ticketId + " not found", exception.getMessage());
    }


}