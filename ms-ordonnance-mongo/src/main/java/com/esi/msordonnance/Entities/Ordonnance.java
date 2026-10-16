package com.esi.msordonnance.Entities;
import com.esi.msordonnance.DTO.PatientDTO;
import com.esi.msordonnance.DTO.RemboursementDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection="ordonnances")
public class Ordonnance {
    @Id
    private String idOrdonnance;
    private LocalDate dateOrdonnance;
    @JsonIgnore
    @DBRef
    private List<Medicament> medicaments;
    private Long idPatient;
    private Long idRemboursement;
    @Transient
    private PatientDTO patient;
    @Transient
    private RemboursementDTO remboursement;

    public Ordonnance() {}

    public Ordonnance(String idOrdonnance, LocalDate dateOrdonnance, List<Medicament> medicaments,
                      Long idPatient, Long idRemboursement, PatientDTO patient, RemboursementDTO remboursement) {
        this.idOrdonnance = idOrdonnance;
        this.dateOrdonnance = dateOrdonnance;
        this.medicaments = medicaments;
        this.idPatient = idPatient;
        this.idRemboursement = idRemboursement;
        this.patient = patient;
        this.remboursement = remboursement;
    }

    public String getIdOrdonnance() { return idOrdonnance; }
    public void setIdOrdonnance(String idOrdonnance) { this.idOrdonnance = idOrdonnance; }
    public LocalDate getDateOrdonnance() { return dateOrdonnance; }
    public void setDateOrdonnance(LocalDate dateOrdonnance) { this.dateOrdonnance = dateOrdonnance; }
    public List<Medicament> getMedicaments() { return medicaments; }
    public void setMedicaments(List<Medicament> medicaments) { this.medicaments = medicaments; }
    public Long getIdPatient() { return idPatient; }
    public void setIdPatient(Long idPatient) { this.idPatient = idPatient; }
    public Long getIdRemboursement() { return idRemboursement; }
    public void setIdRemboursement(Long idRemboursement) { this.idRemboursement = idRemboursement; }
    public PatientDTO getPatient() { return patient; }
    public void setPatient(PatientDTO patient) { this.patient = patient; }
    public RemboursementDTO getRemboursement() { return remboursement; }
    public void setRemboursement(RemboursementDTO remboursement) { this.remboursement = remboursement; }
}