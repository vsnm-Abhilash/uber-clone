package com.abhilash.project.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO {
    private  Double[] coordinates;
    private  String type="Point";

    public PointDTO(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}
