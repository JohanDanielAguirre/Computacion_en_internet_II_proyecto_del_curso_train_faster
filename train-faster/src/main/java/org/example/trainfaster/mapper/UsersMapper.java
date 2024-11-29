package org.example.trainfaster.mapper;

import org.example.trainfaster.DTO.UsersDTO;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.model.Ticket;
import org.example.trainfaster.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UsersMapper {

    UsersMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UsersMapper.class);

    @Mappings({
        @Mapping(target = "roleIds", source = "roles", qualifiedByName = "rolesToRoleIds"),
        @Mapping(target = "ticketIds", source = "tickets", qualifiedByName = "ticketsToTicketIds")
    })
    UsersDTO usersToUsersDTO(Users users);

    @Mappings({
        @Mapping(target = "roles", source = "roleIds", qualifiedByName = "roleIdsToRoles"),
        @Mapping(target = "tickets", source = "ticketIds", qualifiedByName = "ticketIdsToTickets")
    })
    Users usersDTOToUsers(UsersDTO usersDTO);

    @Named("rolesToRoleIds")
    default List<Integer> rolesToRoleIds(List<Role> roles) {
        return roles.stream().map(Role::getId).collect(Collectors.toList());
    }

    @Named("roleIdsToRoles")
    default List<Role> roleIdsToRoles(List<Integer> roleIds) {
        return roleIds.stream().map(id -> {
            Role role = new Role();
            role.setId(id);
            return role;
        }).collect(Collectors.toList());
    }

    @Named("ticketsToTicketIds")
    default List<Integer> ticketsToTicketIds(List<Ticket> tickets) {
        return tickets.stream().map(Ticket::getId).collect(Collectors.toList());
    }

    @Named("ticketIdsToTickets")
    default List<Ticket> ticketIdsToTickets(List<Integer> ticketIds) {
        return ticketIds.stream().map(id -> {
            Ticket ticket = new Ticket();
            ticket.setId(id);
            return ticket;
        }).collect(Collectors.toList());
    }
}