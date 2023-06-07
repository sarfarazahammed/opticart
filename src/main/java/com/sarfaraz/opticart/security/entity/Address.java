package com.sarfaraz.opticart.security.entity;

import com.sarfaraz.opticart.commons.domain.VersionedEntity;
import com.sarfaraz.opticart.security.enums.AddressTag;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "user", callSuper = false)
@ToString(exclude = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "address", schema = "app")
public class Address extends VersionedEntity {
    @Id
    private String id;

    private String address;
    private String city;
    private String state;
    private String country;
    private String zipcode;
    private Boolean isDefault;
    @Enumerated(EnumType.STRING)
    private AddressTag tag;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
