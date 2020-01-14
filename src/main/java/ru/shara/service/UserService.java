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


    private final String REST_SERVER_URL = "http://localhost:8090/rest/";

    @Autowired
    private RestTemplate restTemplate;

    UserService() {	}

    public void save(User user) {
        restTemplate.put(REST_SERVER_URL + "new-user", user);
    }

    public void saveIfNotExists(User user) {
        User checked = restTemplate.getForObject(REST_SERVER_URL + "user/name/" + user.getName(), User.class);

        if (checked == null) {
            restTemplate.put(REST_SERVER_URL + "new-user", user);
        }
    }

    public List<User> listAll() {
        List<User> result = restTemplate.getForObject(REST_SERVER_URL + "all-users", List.class);

        return result;
    }

    public User get(Long id) {
        User result = restTemplate.getForObject(REST_SERVER_URL + "user/id/" + id, User.class);

        return result;
    }

    public User getUserByName(String name) {
        User result = restTemplate.getForObject(REST_SERVER_URL + "user/name/" + name, User.class);

        System.out.println(result);
        return result;
    }

    public void delete(Long id) {
        restTemplate.delete(REST_SERVER_URL + "user/id/" + id);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        User user = getUserByName(name);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + name);
        }

        return user;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.basicAuthentication("root", "root").build();
    }
}
