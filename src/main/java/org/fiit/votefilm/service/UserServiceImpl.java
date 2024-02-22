package org.fiit.votefilm.service;

import org.fiit.votefilm.model.VoterUser;
import org.fiit.votefilm.repository.VoterUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return UserDetailsImpl.build(user);
    }
}
