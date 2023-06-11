package jpkr.advancedprogrammingproject.security.services;

import jpkr.advancedprogrammingproject.models.UserAccount;
import jpkr.advancedprogrammingproject.repositories.UserAccountRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public UserDetailsServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromCallable(() -> {
            final UserAccount user = userAccountRepository.findByUsername(username);
            if (null == user) {
                throw new UsernameNotFoundException("User \"" + username + "\" not found.");
            }
            return user;
        })
                .cast(UserDetails.class);
    }
}
