package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private DataSource dataSource;

    @Autowired
    public UserDetailsServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user details from the database
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT user_name, password FROM users WHERE user_name = ?";
        UserDetails userDetails = jdbcTemplate.queryForObject(sql, new Object[]{username}, (rs, rowNum) -> {
            String dbUserName = rs.getString("user_name");
            String password = rs.getString("password");
            return new User(dbUserName, password, true, true, true, true, new ArrayList<>());
        });
        // Retrieve user roles from the database
        String sqlRoles = "SELECT r.role FROM roles r " +
            "INNER JOIN user_roles ur ON r.id = ur.role_id " +
            "INNER JOIN users u ON ur.user_id = u.id " +
            "WHERE u.user_name = ?";
        List<String> dbRoles = jdbcTemplate.queryForList(sqlRoles, new Object[]{username}, String.class);

        // Convert roles to a collection of GrantedAuthority objects
        Collection<GrantedAuthority> authorities = dbRoles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        // Set the authorities in the UserDetails instance
        return new User(
            userDetails.getUsername(),
            userDetails.getPassword(),
            userDetails.isEnabled(),
            true,
            true,
            true,
            authorities
        );
    }
}
