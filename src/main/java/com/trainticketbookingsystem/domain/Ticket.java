package com.trainticketbookingsystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "train_ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    private Long ticketId;

    private String fromLocation;

    private String toLocation;

    @Pattern(regexp = "[AaBb]", message = "Section must be either 'A' or 'B'")
    private String section;


    private double price;


    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
