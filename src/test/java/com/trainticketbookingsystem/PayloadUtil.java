package com.trainticketbookingsystem;

import com.trainticketbookingsystem.domain.PurchaseTicketRequest;
import com.trainticketbookingsystem.domain.Ticket;
import com.trainticketbookingsystem.domain.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PayloadUtil {

    public static Ticket buildTicket(){
        return Ticket.builder()
                .ticketId(1L)
                .fromLocation("Paris")
                .toLocation("London")
                .section("A")
                .price(20.0)
                .user(buildUser())
                .build();
    }

    public static User buildUser(){
        return User.builder()
                .userId(1L)
                .firstName("John")
                .lastName("Cena")
                .email("johncena@gmail.com")
                .build();
    }

    public static PurchaseTicketRequest buildTicketRequest() {
        return PurchaseTicketRequest.builder()
                .from("Paris")
                .to("London")
                .price(20.0)
                .user(buildUser())
                .build();
    }
    public static List<Ticket> buildTicketList(){
        Ticket ticket1 = buildMockTicket("London", "Paris", "A", buildMockUser("John", "Doe"));
        Ticket ticket2 = buildMockTicket("London", "Berlin", "B", buildMockUser("Jane", "Smith"));
        Ticket ticket3 = buildMockTicket("Paris", "Rome", "A", buildMockUser("Michael", "Johnson"));

        return Arrays.asList(ticket1, ticket2, ticket3);
    }

    public static Ticket buildMockTicket(String fromLocation, String toLocation, String section, User user) {
        Ticket ticket = new Ticket();
        ticket.setFromLocation(fromLocation);
        ticket.setToLocation(toLocation);
        ticket.setSection(section);
        ticket.setUser(user);
        return ticket;

    }

    public static User buildMockUser(String firstName, String lastName) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

}
