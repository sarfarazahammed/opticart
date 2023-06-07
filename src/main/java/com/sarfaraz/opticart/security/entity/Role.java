package com.sarfaraz.opticart.security.entity;

import com.google.common.collect.ImmutableSet;
import com.sarfaraz.opticart.commons.domain.VersionedEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@Table(name = "role", schema = "app")
public class Role extends VersionedEntity {
    @Id
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission_map",
            joinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "permission_name", referencedColumnName = "name"))
    private Set<Permission> permissions = new HashSet<>();

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }

    public ImmutableSet<Permission> getPermissions() {
        return ImmutableSet.copyOf(permissions);
    }

}
