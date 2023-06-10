package com.sarfaraz.opticart.user.entity.power;

import com.sarfaraz.opticart.user.enums.BaseHorizontalDirection;
import com.sarfaraz.opticart.user.enums.BaseVerticalDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RightEyePower {
    @Column(name = "od_sphere")
    private float sphere;
    @Column(name = "od_cylinder")
    private float cylinder;
    @Column(name = "od_add")
    private float add;
    @Column(name = "od_axis")
    private int axis;
    @Column(name = "pd_right")
    private int pupliaryDistance;
    @Column(name = "od_prism_horizontal")
    private float prismHorizontal;
    @Column(name = "od_prism_vertical")
    private float prismVertical;
    @Enumerated(EnumType.STRING)
    @Column(name = "od_base_direction_horizontal")
    private BaseHorizontalDirection baseHorizontal;
    @Enumerated(EnumType.STRING)
    @Column(name = "od_base_direction_vertical")
    private BaseVerticalDirection baseVertical;
}
