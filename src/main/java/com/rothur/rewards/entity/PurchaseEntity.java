package com.rothur.rewards.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "Purchase")
@Data
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double price;


    private Long points;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "FK_Purchase_User"))
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private UserEntity userEntity;

}
