package com.example.pract7;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("hotels")
public class Hotel {
    @Id
    private Long id;
    private String name;
    private String city;
    private Integer stars;
}
