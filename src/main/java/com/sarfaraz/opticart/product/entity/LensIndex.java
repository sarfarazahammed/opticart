package com.sarfaraz.opticart.product.entity;

import com.sarfaraz.opticart.commons.domain.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "lens_index", schema = "app")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LensIndex extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @Column(name = "index")
    private double index;
    private double sphMinPos;
    private double sphMinNeg;
    private double sphMaxPos;
    private double sphMaxNeg;
    private double cylMinPos;
    private double cylMinNeg;
    private double cylMaxPos;
    private double cylMaxNeg;
    private String features;
    private String imgUrl;

}
