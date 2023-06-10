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
public class LeftEyePower {
    @Column(name = "os_sphere")
    private float sphere;
    @Column(name = "os_cylinder")
    private float cylinder;
    @Column(name = "os_add")
    private float add;
    @Column(name = "os_axis")
    private int axis;
    @Column(name = "pd_left")
    private int pupliaryDistance;
    @Column(name = "os_prism_horizontal")
    private float prismHorizontal;
    @Column(name = "os_prism_vertical")
    private float prismVertical;
    @Enumerated(EnumType.STRING)
    @Column(name = "os_base_direction_horizontal")
    private BaseHorizontalDirection baseHorizontal;
    @Enumerated(EnumType.STRING)
    @Column(name = "os_base_direction_vertical")
    private BaseVerticalDirection baseVertical;
}
