package com.esi.msremboursement.DTO;

import lombok.Data;
import java.util.List;

@Data
public class OrdonnanceDTO {
    private Long idOrdonnance;
    private String dateOrdonnance;
    private List<MedicamentDTO> medicaments;

    public int getNombreMedicaments() {
        return medicaments != null ? medicaments.size() : 0;
    }
}
