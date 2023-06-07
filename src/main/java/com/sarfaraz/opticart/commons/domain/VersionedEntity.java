package com.sarfaraz.opticart.commons.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
@Data
public abstract class VersionedEntity extends AuditableEntity {
    @Version
    @Column(
            name = "version",
            nullable = false
    )
    protected long version = 1L;

}
