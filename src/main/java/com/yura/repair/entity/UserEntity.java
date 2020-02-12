package com.yura.repair.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "surname", nullable = false, length = 45)
    private String surname;

    @Column(name = "email", nullable = false, length = 60)
    private String email;

    @ToString.Exclude
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phone_number", length = 45)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 45)
    private Role role;
}