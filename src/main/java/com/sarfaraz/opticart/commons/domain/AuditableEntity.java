package com.sarfaraz.opticart.commons.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@Getter
@Setter
public abstract class AuditableEntity implements Serializable {

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "last_modified_by")
    private String lastModifiedBy;
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now(ZoneOffset.UTC);
        this.lastModifiedDate = LocalDateTime.now(ZoneOffset.UTC);
    }

    @PreUpdate
    public void onPreUpdate() {
        this.lastModifiedDate = LocalDateTime.now(ZoneOffset.UTC);
    }


}
