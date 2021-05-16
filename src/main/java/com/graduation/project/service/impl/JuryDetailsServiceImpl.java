package com.graduation.project.service.impl;

import com.graduation.project.model.entities.Jury;
import com.graduation.project.repository.JuryRepository;
import com.graduation.project.service.JuryDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JuryDetailsServiceImpl implements UserDetailsService, JuryDetailsService {

    private final JuryRepository juryRepository;

    private final PasswordEncoder bcryptEncoder;

    public JuryDetailsServiceImpl(JuryRepository juryRepository, PasswordEncoder bcryptEncoder) {
        this.juryRepository = juryRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;

        Jury jury = juryRepository.findByUsername(username);
        if (jury != null) {
            roles = Arrays.asList(new SimpleGrantedAuthority(jury.getRole()));
            return new org.springframework.security.core.userdetails.User(jury.getUsername(), jury.getPassword(), roles);
        }

        return  null;
    }

    @Override
    public void createJury(Jury jury) {
        Jury newJury = new Jury();
        newJury.setUsername(jury.getUsername());
        newJury.setPassword(bcryptEncoder.encode(jury.getPassword()));
        newJury.setRole("ROLE_JURY");
        juryRepository.save(newJury);
    }
}
