package com.esi.msremboursement.Controller;

import com.esi.msremboursement.DTO.OrdonnanceDTO;
import com.esi.msremboursement.DTO.PatientDTO;
import com.esi.msremboursement.Entities.Remboursement;
import com.esi.msremboursement.Proxy.PatientProxy;
import com.esi.msremboursement.Repository.RemboursementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class RemboursementController {

    final RemboursementRepository remboursementRepository;
    final PatientProxy patientProxy;

    @Qualifier("OrdonnanceGraphQlClient")
    final HttpGraphQlClient ordonnanceClient;

    @GetMapping("/remboursement/{idOrdonnance}")
    public Object getRemboursementDetails(@PathVariable Long idOrdonnance) {

        Remboursement remboursement = remboursementRepository
                .findRemboursementByIdOrdonnance(idOrdonnance);

        // 1. Get ordonnance info (date + nombre medicaments) via GraphQL
        String query = "{ getOrdonnanceById(id: " + idOrdonnance + ") " +
                "{ idOrdonnance dateOrdonnance medicaments { nom cout } } }";

        OrdonnanceDTO ordonnance = ordonnanceClient.document(query)
                .retrieve("getOrdonnanceById")
                .toEntity(OrdonnanceDTO.class)
                .block();

        // 2. Get patient name via REST
        PatientDTO patient = patientProxy.getPatient(
                remboursement.getAssure().getIdAssure()
        );

        return new Object() {
            public final Long idRemboursement = remboursement.getIdREmboursement();
            public final Double montant = remboursement.getMontant();
            public final String dateOrdonnance = ordonnance != null ? ordonnance.getDateOrdonnance() : null;
            public final int nombreMedicaments = ordonnance != null ? ordonnance.getNombreMedicaments() : 0;
            public final String nomPatient = patient != null ? patient.getNom() : null;
        };
    }
}
