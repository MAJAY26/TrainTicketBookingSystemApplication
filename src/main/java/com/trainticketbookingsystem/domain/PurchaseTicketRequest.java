package com.trainticketbookingsystem.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseTicketRequest {


    @NotBlank(message = "From location is required")
    private String from;

    @NotBlank(message = "To location is required")
    private String to;


    @Positive(message = "Price must be a positive number")
    private double price;

    @Valid
    @NotNull(message = "User information is required")
    private User user;

}

