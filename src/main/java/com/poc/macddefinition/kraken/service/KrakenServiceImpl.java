package com.poc.macddefinition.kraken.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

@Service
public class KrakenServiceImpl implements KrakenService {

    private static final Logger log = LoggerFactory.getLogger(KrakenServiceImpl.class);

    private final WebClient webClient;

    private String krakenPublicUrl;

    public KrakenServiceImpl(@Value("${kraken.public.url}") String krakenPublicUrl) {

        this.krakenPublicUrl = krakenPublicUrl;
        this.webClient = WebClient.builder()
                .baseUrl(krakenPublicUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .filter(logRequest())
                .build();
    }

    @Override
    public Mono<JSONObject> queryPublicAPI(Function<UriBuilder, URI> uri) {

        return webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(stringData -> new JSONObject(stringData));
    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }
}
