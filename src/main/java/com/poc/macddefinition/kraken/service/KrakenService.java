package com.poc.macddefinition.kraken.service;

import org.json.JSONObject;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

public interface KrakenService {

    public Mono<JSONObject> queryPublicAPI(Function<UriBuilder, URI> uri);
}
