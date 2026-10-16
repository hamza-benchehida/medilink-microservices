package com.esi.msremboursement.Proxy;

import com.esi.msremboursement.DTO.PatientDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-patient")
@LoadBalancerClient(name="ms-patient")
public interface PatientProxy {

    @GetMapping("/api/patient/{idp}")
    public PatientDTO getPatient(@PathVariable("idp") Long idp);
}
