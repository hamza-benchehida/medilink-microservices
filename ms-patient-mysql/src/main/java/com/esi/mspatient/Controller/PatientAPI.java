package com.esi.mspatient.Controller;

import com.esi.mspatient.DTO.OrdonnanceDTO;
import com.esi.mspatient.DTO.RemboursementDTO;
import com.esi.mspatient.Entities.Patient;
import com.esi.mspatient.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api")
public class PatientAPI {

    final PatientRepository patientRepository;
    final HttpGraphQlClient ordonnanceClient;
    final HttpGraphQlClient remboursementClient;
    final ReactiveCircuitBreakerFactory circuitBreakerFactory;

    public PatientAPI(
            PatientRepository patientRepository,
            @Qualifier("OrdonnanceGraphQlClient") HttpGraphQlClient ordonnanceClient,
            @Qualifier("RemboursementGraphQlClient") HttpGraphQlClient remboursementClient,
            ReactiveCircuitBreakerFactory circuitBreakerFactory
    ) {
        this.ordonnanceClient = ordonnanceClient;
        this.remboursementClient = remboursementClient;
        this.patientRepository = patientRepository;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping("patient/{idp}")
    public Patient getPatientWithOrdonnance(@PathVariable("idp") Long idPatient) {

        Patient patient = patientRepository.findById(idPatient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        String queryOrdonnance = " { getOrdonnanceByIdPatient(idPatient: " + idPatient + ") " +
                " { idOrdonnance medicaments { nom cout } } }";

        ReactiveCircuitBreaker cbOrdonnance = circuitBreakerFactory.create("ordonnance-cb");

        List<OrdonnanceDTO> ordonnances = cbOrdonnance.run(
                ordonnanceClient.document(queryOrdonnance)
                        .retrieve("getOrdonnanceByIdPatient")
                        .toEntityList(OrdonnanceDTO.class),
                throwable -> {
                    System.out.println("⚠️ MS-Ordonnance indisponible : " + throwable.getMessage());
                    return Mono.just(Collections.emptyList());
                }
        ).block();

        if (ordonnances != null) {
            ordonnances.forEach(o -> {
                String queryRemboursement = "{ getRemboursementByIdOrdonnance(idOrdonnance: " + o.getIdOrdonnance() + ")" +
                        "{ montant dateRemboursement } }";

                ReactiveCircuitBreaker cbRemboursement = circuitBreakerFactory.create("remboursement-cb");

                RemboursementDTO remboursementDTO = cbRemboursement.run(
                        remboursementClient.document(queryRemboursement)
                                .retrieve("getRemboursementByIdOrdonnance")
                                .toEntity(RemboursementDTO.class),
                        throwable -> {
                            System.out.println("⚠️ MS-Remboursement indisponible : " + throwable.getMessage());
                            return Mono.empty();
                        }
                ).block();

                o.setRemboursementDTO(remboursementDTO);
            });
        }

        patient.setOrdonnances(ordonnances);
        return patient;
    }
}
