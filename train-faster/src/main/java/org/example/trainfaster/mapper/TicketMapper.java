package org.example.trainfaster.mapper;

import org.example.trainfaster.DTO.TicketDTO;
import org.example.trainfaster.model.Ticket;
import org.mapstruct.Mapper;

@Mapper
public interface TicketMapper {
    TicketMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TicketMapper.class);

    TicketDTO ticketToTicketDTO(Ticket ticket);
    Ticket ticketDTOToTicket(TicketDTO ticketDTO);
}
