package org.example.trainfaster.services.implementations;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.example.trainfaster.model.Maintance;
import org.example.trainfaster.model.Permission;
import org.example.trainfaster.model.Role;
import org.example.trainfaster.model.Ticket;
import org.example.trainfaster.model.Train;
import org.example.trainfaster.model.Trip;
import org.example.trainfaster.model.Users;
import org.example.trainfaster.repositories.MaintanceRepository;
import org.example.trainfaster.repositories.PermissionRepository;
import org.example.trainfaster.repositories.RoleRepository;
import org.example.trainfaster.repositories.TicketRepository;
import org.example.trainfaster.repositories.TrainRepository;
import org.example.trainfaster.repositories.TripRepository;
import org.example.trainfaster.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Datainit {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MaintanceRepository maintanceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        Users user1 = new Users();
        user1.setUsername("mrzen");
        user1.setPassword(passwordEncoder.encode("123"));
        user1.setEmail("si@gmail.com");
        user1.setName("johan");
        user1.setLastName("aguirre");
        userRepository.save(user1);


        Users user2 = new Users();
        user2.setUsername("gerson");
        user2.setPassword(passwordEncoder.encode("venezuela"));;
        user2.setEmail("gerson@gmail.com");
        user2.setName("gerson de jesus");
        user2.setLastName("hurtado borja");
        userRepository.save(user2);



        Users user3 = new Users();
        user3.setUsername("AlejoAmu");
        user3.setPassword(passwordEncoder.encode("alejo"));;
        user3.setEmail("si@gmail.com");
        user3.setName("johan");
        user3.setLastName("aguirre");
        userRepository.save(user3);

        Train train = new Train();
        train.setId(1);
        train.setModel("model");
        trainRepository.save(train);

        Maintance maintance = new Maintance();
        maintance.setMaintenance_Date(new Timestamp(System.currentTimeMillis()));
        maintance.setDescription("Description");
        maintance.setStatus("Status");
        maintanceRepository.save(maintance);



        Permission permission = new Permission();
        permission.setName("READ");
        permissionRepository.save(permission);
        Permission permission1 = new Permission();
        permission1.setName("WRITE");
        permission1 = permissionRepository.save(permission1);
        Permission permission2 = new Permission();
        permission2.setName("ADD");
        permission2 = permissionRepository.save(permission2);
        Permission permission3 = new Permission();
        permission3.setName("DELETE");
        permission3 = permissionRepository.save(permission3);
        Permission permission4 = new Permission();
        permission4.setName("UPDATE");
        permission4 = permissionRepository.save(permission4);
        Permission permission5 = new Permission();
        permission5.setName("UPLOAD");
        permission5 = permissionRepository.save(permission5);

        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");
        role1 = roleRepository.save(role1);
        Role role2 = new Role();
        role2.setName("ROLE_TRAVELLER");
        role2 = roleRepository.save(role2);
        Role role3 = new Role();
        role3.setName("ROLE_STAFF");
        role3 = roleRepository.save(role3);

        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setSeat("seat");
        ticketRepository.save(ticket);



        Trip trip = new Trip();
        trip.setId(1);
        trip.setRoute("route");
        trip.setDepartureTime(new Timestamp(System.currentTimeMillis()));
        trip.setArrivalTime(new Timestamp(System.currentTimeMillis()));
        tripRepository.save(trip);

        // Assign ROLE_ADMIN to user1
        Role adminRole = roleRepository.findById(2).orElseThrow(() -> new RuntimeException("Admin role not found"));
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        user1.setRoles(roles);
        userRepository.save(user1);
    }
}
