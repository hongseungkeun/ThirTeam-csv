package com.sparta.csv.domain.theater.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Entity
@Table(name="theaters")
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterId;

    @Column(nullable = false)
    @Size(max = 10)
    private String theaterName;

    public static Theater newTheater(String theaterName){
        return Theater.builder()
                .theaterName(theaterName)
                .build();
    }
}
