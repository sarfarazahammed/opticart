package com.sarfaraz.opticart.security.entity;

import com.google.common.collect.ImmutableSet;
import com.sarfaraz.opticart.commons.domain.AuditableEntity;
import com.sarfaraz.opticart.security.enums.UserState;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "app_user", schema = "app")
public class User extends AuditableEntity {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "profile_img_url")
    private String profileImageUrl;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private UserState state;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_map",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"))
    private Set<Role> roles = new HashSet<>();

    public void assignRole(Role role) {
        this.roles.add(role);
    }

    public ImmutableSet<String> getPermissions() {
        return this.roles
                .stream()
                .flatMap(role -> role.getPermissions().stream().map(Permission::getName)).collect(collectingAndThen(toSet(), ImmutableSet::copyOf));
    }

}
