package com.sarfaraz.opticart.security.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_login_tracker", schema = "app")
public class UserLoginTracker {

    @Id
    @Column(name = "user_id")
    private String userId;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private User user;
    @Column(name = "last_successful_timestamp")
    private LocalDateTime lastSuccessfulTimestamp;
    @Column(name = "last_successful_ip")
    private String lastSuccessfulIP;
    @Column(name = "last_successful_agent", columnDefinition = "text")
    private String lastSuccessfulAgent;
    @Column(name = "last_failure_timestamp")
    private LocalDateTime lastFailureTimestamp;
    @Column(name = "last_failure_ip")
    private String lastFailureIP;
    @Column(name = "last_failure_agent", columnDefinition = "text")
    private String lastFailureAgent;
    @Column(name = "failure_count_from_last_success")
    private long faillureCountFromLastSuccess;
    @Column(name = "is_locked")
    private boolean locked;
    @Column(name = "locked_timestamp")
    private LocalDateTime lockedTimestamp;

}
