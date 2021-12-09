package com.neosoft.poc1.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
@SQLDelete(sql = "UPDATE user SET IS_DELETED = true WHERE id=?")
@Where(clause = "IS_DELETED=false")
public class User {

    @Id
    @Column(name ="ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Enter valid pincode")
    @Column(name = "PINCODE", nullable = false)
    @NotBlank(message = "Pincode cannot be blank")
    private String pincode;

    @Past
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "DATE_OF_JOINING", nullable = false)
    private LocalDate dateOfJoining;

    @Column(name = "IS_DELETED")
    private Boolean isDeleted  = Boolean.FALSE;
}
