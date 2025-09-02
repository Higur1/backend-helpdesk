package io.github.higur.helpdesk.security.service;

import io.github.higur.helpdesk.domain.mapping.PersonMapper;
import io.github.higur.helpdesk.repository.PersonRepository;
import io.github.higur.helpdesk.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return personRepository.findByEmail(email)
                .map(person -> mapper.toCustomUserDetails(person))
                .orElseThrow(() -> new ObjectNotFoundException("User Not Found!"));
    }
}
