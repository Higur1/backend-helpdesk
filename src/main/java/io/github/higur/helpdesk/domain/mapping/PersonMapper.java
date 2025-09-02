package io.github.higur.helpdesk.domain.mapping;

import io.github.higur.helpdesk.domain.Person;
import io.github.higur.helpdesk.security.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {
    public CustomUserDetails toCustomUserDetails(Person person) {
        return new CustomUserDetails(
                person.getId(),
                person.getEmail(),
                person.getPassword(),
                person.getProfiles()
        );
    }
}
