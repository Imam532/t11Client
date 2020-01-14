package ru.shara.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.shara.model.User;

import java.util.List;

@Service
@Transactional
@EnableTransactionManagement(proxyTargetClass = true)
public class UserService implements UserDetailsService {

    private final String SERVER_URL = "http://localhost:8090/rest/";

    @Autowired
    private RestTemplate restTemplate;

    public UserService() {
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthentication("root", "root").build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<User> getAllUsers() {
        return restTemplate.getForObject(SERVER_URL + "users", List.class);

    }

    public User getUser(Long id) {
        return restTemplate.getForObject(SERVER_URL + "users/id/" + id, User.class);
    }

    public void createUser(User user) {
        restTemplate.put(SERVER_URL + "users", user);
    }

    public void updateUser(User user) {
        restTemplate.put(SERVER_URL + "users/id/" +  user.getId(), User.class);
    }

    public void deleteUser(Long id) {
        restTemplate.delete(SERVER_URL + "users/id/" + id);
    }

    public User getUserByUsername(String name) {
        return restTemplate.getForObject(SERVER_URL + "users/name/" + name, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        if( user == null) {
            throw  new UsernameNotFoundException("User with name " + username + "not found");
        }
        return user;
    }
}
