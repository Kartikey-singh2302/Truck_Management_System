package com.Kartikey_Singh.TMS.entity;
import com.vladmihalcea.hibernate.type.json.JsonType;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transporter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private UUID transporterId;
    private String companyName;
    private Double Rating;
    private String truckType;
    @Convert(converter = MapToStringConverter.class)

    private Map<String,Integer> availableTrucks;

    private int count;


}
