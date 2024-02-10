package com.trainticketbookingsystem.persistance;


import com.trainticketbookingsystem.domain.Ticket;
import com.trainticketbookingsystem.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainTicketRepository extends JpaRepository<Ticket, Long> {
    void deleteByUser(User user);

}

