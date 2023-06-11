package jpkr.advancedprogrammingproject.models;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
public class UserAccount implements UserDetails {
    @Id
    private String id;

    private String username;

    /**
     * Hexadecimal string (represented as a sequence of characters 0-9 and a-f).
     * <p>
     * The hashing algorithm used is SHA-256 without salt.
     * <p>
     * This field is marked with @JsonIgnore to prevent it
     * from being serialized and exposed in API responses.
     */
    @JsonIgnore
    private String password;

    private Set<UserRole> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
       return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (null == roles) {
            return Collections.emptyList();
        }
        // Convert user roles to Spring Security's GrantedAuthority collection
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
