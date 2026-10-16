package com.esi.msordonnance.Entities;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="medicaments")
@Data
public class Medicament {
    @Id
    private String idMed;
    private String nom;
    private int duree;
    private String dosage;
    private double cout;
    @DBRef
    private Ordonnance ordonnance;

    public Medicament() {}

    public Medicament(String idMed, String nom, int duree, String dosage, double cout, Ordonnance ordonnance) {
        this.idMed = idMed;
        this.nom = nom;
        this.duree = duree;
        this.dosage = dosage;
        this.cout = cout;
        this.ordonnance = ordonnance;
    }
}