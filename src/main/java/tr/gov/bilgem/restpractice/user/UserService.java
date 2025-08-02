package tr.gov.bilgem.restpractice.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tr.gov.bilgem.restpractice.model.User;
import tr.gov.bilgem.restpractice.service.AbstractService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService extends AbstractService<User, Long> implements UserDetailsService {

    private static final Log logger = LogFactory.getLog(UserService.class);

    protected UserService(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public Log getServiceLoggerByEntity() {
        return logger;
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !authentication.getName().equals("anonymousUser")) {
            return findByUsername(authentication.getName());
        }
        return Optional.empty();
    }

    public Optional<User> findByUsername(String username){
        return ((UserRepository) repository).findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return createUserDetails(user);
    }

    private UserDetails createUserDetails(User user) {
        // Add default authority - you might want to get this from your User entity
        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}