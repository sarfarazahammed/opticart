package com.sarfaraz.opticart.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sarfaraz.opticart.commons.domain.AuditableEntity;
import com.sarfaraz.opticart.user.entity.power.Power;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "prescription", schema = "app")
@Builder(toBuilder = true)
public class Prescription extends AuditableEntity {
    @Id
    private String id;
    private String typeId;
    @Transient
    private PrescriptionType type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "power_id", referencedColumnName = "id", nullable = false)
    private Power power;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("prescription")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Member member;

}
