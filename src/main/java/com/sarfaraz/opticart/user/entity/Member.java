package com.sarfaraz.opticart.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sarfaraz.opticart.security.entity.User;
import com.sarfaraz.opticart.user.enums.Gender;
import com.sarfaraz.opticart.user.enums.MemberTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member", schema = "app")
public class Member {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String name;
    private LocalDate dob;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MemberTag tag = MemberTag.SELF;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("member")
    private Set<Prescription> prescriptions = new HashSet<>();
}
