package com.esi.mspatient.Proxy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GraphQlClientConfig {

    @Bean
    @Qualifier("loadBalancedWebClientBuilder")
    @LoadBalanced
    WebClient.Builder loadBalancedWebClientBuilder() {

        return WebClient.builder();
    }

    @Bean
    @Qualifier("OrdonnanceGraphQlClient")
    HttpGraphQlClient OrdonnanceGraphQlClient(@Qualifier("loadBalancedWebClientBuilder") WebClient.Builder webClientBuilder) {

        WebClient webClient = webClientBuilder
                .baseUrl("http://ms-ordonnance/graphql")
                .build();

        return HttpGraphQlClient.builder(webClient).build();
    }

    @Bean
    @Qualifier("RemboursementGraphQlClient")
    HttpGraphQlClient RemboursementGraphQlClient(@Qualifier("loadBalancedWebClientBuilder") WebClient.Builder webClientBuilder) {

        WebClient webClient =webClientBuilder
                .baseUrl("http://ms-remboursement/graphql")
                .build();

        return HttpGraphQlClient.builder(webClient).build();
    }


}