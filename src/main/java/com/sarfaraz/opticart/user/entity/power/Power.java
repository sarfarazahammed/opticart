package com.sarfaraz.opticart.user.entity.power;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "power", schema = "app")
public class Power {
    @Id
    private String id;

    @Embedded
    private LeftEyePower leftEyePower;
    @Embedded
    private RightEyePower rightEyePower;

    private float baseCurve;
    private float diameter;

}
