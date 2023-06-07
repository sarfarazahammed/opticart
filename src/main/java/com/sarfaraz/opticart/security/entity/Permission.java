package com.sarfaraz.opticart.security.entity;

import com.sarfaraz.opticart.commons.domain.VersionedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@Table(name = "permission", schema = "app")
public class Permission extends VersionedEntity {
    @Id
    @Column(name = "name")
    private String name;


}
