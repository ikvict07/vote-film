package org.fiit.votefilm.service;

import org.fiit.votefilm.model.users.AbstractUser;
import org.fiit.votefilm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Service for user details.
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AbstractUser user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No user exists with this username")
        );
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // Create a collection of authorities
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities);
    }
}
