package com.api.parkingcontrol.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponsibleDto {
    @NotBlank
    private String parkingSpotNumber;
    @NotBlank
    private String apartment;
    @NotBlank
    private String block;
    @NotBlank
    @Size(max = 11)
    private String responsibleCpf;
    @NotBlank
    private String responsibleEmail;
    @NotBlank
    private String responsibleName;
}
