package com.sarfaraz.opticart.security.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "password_recovery", schema = "app")
@Builder
public class PasswordRecovery {
    @Id
    private String id;
    private String userId;
    private LocalDateTime usedTime;
    private String code;
    private LocalDateTime expiryTime;
}
