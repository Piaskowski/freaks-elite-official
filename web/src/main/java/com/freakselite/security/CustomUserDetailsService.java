package com.freakselite.security;

import com.freakselite.dao.daoImpl.UserEntityDaoImpl;
import com.freakselite.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    // == fields ==
    UserEntityDaoImpl userEntityDaoImpl;

    // == constructors ==
    @Autowired
    public CustomUserDetailsService(UserEntityDaoImpl userEntityDaoImpl) {
        this.userEntityDaoImpl = userEntityDaoImpl;
    }

    // == public methods ==
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityDaoImpl.findByUsername(username);
        if (user != null){

            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream().map((role) -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                            .collect(Collectors.toList())
            );

        } else {
            throw new UsernameNotFoundException("Nieprawidłowy login lub hasło");
        }
    }
}
