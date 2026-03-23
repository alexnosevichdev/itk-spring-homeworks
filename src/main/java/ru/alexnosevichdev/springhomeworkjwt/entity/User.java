package ru.alexnosevichdev.springhomeworkjwt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alexnosevichdev.springhomeworkjwt.enums.Role;
import java.util.List;

import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Укажите юзернейм")
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private boolean isAccountNonLocked = true;

    @Builder.Default
    private int shitLoginAttempts = 0;


    //Проверка ролей у пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    //Проверка срока действия аккаунта
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //Проверка срока действия пароля
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Проверка активен ли аккаут
    @Override
    public boolean isEnabled() {
        return true;
    }

    //Проверка, заблочен ли аккаунт
    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
