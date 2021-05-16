package com.graduation.project.service.impl;

import com.graduation.project.model.entities.Coordinator;
import com.graduation.project.model.entities.Student;
import com.graduation.project.repository.CoordinatorRepository;
import com.graduation.project.service.CoordinatorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CoordinatorDetailsServiceImpl implements UserDetailsService , CoordinatorDetailsService {

    private final CoordinatorRepository coordinatorRepository;

    private final StudentDetailsServiceImpl studentDetailsServiceImpl;

    private final PasswordEncoder bcryptEncoder;

    public CoordinatorDetailsServiceImpl(CoordinatorRepository coordinatorRepository, StudentDetailsServiceImpl studentDetailsServiceImpl, PasswordEncoder bcryptEncoder) {
        this.coordinatorRepository = coordinatorRepository;
        this.studentDetailsServiceImpl = studentDetailsServiceImpl;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<SimpleGrantedAuthority> roles = null;

        Coordinator coordinator = coordinatorRepository.findByUsername(username);
        if (coordinator != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(coordinator.getRole()));
            return new org.springframework.security.core.userdetails.User(coordinator.getUsername(), coordinator.getPassword(), roles);
        }

        return studentDetailsServiceImpl.loadUserByUsername(username);

       // throw new UsernameNotFoundException("Coordinator is not found with the name " + username);
    }



    @Override
    public void createCoordinator(Coordinator coordinator) {
        Coordinator newCoordinator = new Coordinator();
        newCoordinator.setUsername(coordinator.getUsername());
        newCoordinator.setPassword(bcryptEncoder.encode(coordinator.getPassword()));
        newCoordinator.setRole("ROLE_COORDINATOR");
        coordinatorRepository.save(newCoordinator);
    }
}
