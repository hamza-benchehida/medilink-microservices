package com.esi.msremboursement.DTO;

import lombok.Data;

@Data
public class MedicamentDTO {
    private Long idMed;
    private String nom;
    private String dosage;
    private Double cout;
}
