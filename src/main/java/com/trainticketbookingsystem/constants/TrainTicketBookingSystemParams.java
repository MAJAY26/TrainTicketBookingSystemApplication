package com.trainticketbookingsystem.constants;

public interface TrainTicketBookingSystemParams {


    String TRAIN_TICKET_BOOKING_MAPPER = "/v1/booking_system";
    String TRAIN_TICKET_PURCHASE_TICKETS = "/purchase_tickets";
    String RECEIPT_DETAILS = "/getTicketDetails/{id}";
    String USER_DETAILS_BY_SECTION = "/getUsersBySection/{section}";
    String REMOVE_USER = "/removeUser/{id}";
    String MODIFY_USER_SEAT = "/modifySeat/{id}";
}
