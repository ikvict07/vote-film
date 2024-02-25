package org.fiit.votefilm.service;

import org.fiit.votefilm.model.VoterUser;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserDetailsService {
    private VoterUserRepository userRepository;

    @Autowired
    private void setUserRepository(VoterUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        VoterUser user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("No user exists with this username")
        );
        System.out.println("User found" + user.getUsername() + " " + user.getPassword() + " " + user.getRole());
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        // Create a collection of authorities
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities, user.getPoints());
    }
}
