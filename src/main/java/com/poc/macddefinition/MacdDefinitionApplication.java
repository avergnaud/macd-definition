package com.poc.macddefinition;

import com.poc.macddefinition.kraken.TradingPairs;
import com.poc.macddefinition.kraken.service.KrakenService;
import com.poc.macddefinition.persistence.chart.ChartEntity;
import com.poc.macddefinition.persistence.chart.ChartRepository;
import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionEntity;
import com.poc.macddefinition.persistence.macddefinition.MacdDefinitionRepository;
import com.poc.macddefinition.persistence.utilisateur.UtilisateurEntity;
import com.poc.macddefinition.persistence.utilisateur.UtilisateurRepository;
import com.poc.macddefinition.kraken.Intervals;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@SpringBootApplication
@EnableScheduling
public class MacdDefinitionApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(MacdDefinitionApplication.class);

	@Autowired
	private KrakenService krakenService;

	@Autowired
	ChartRepository chartRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private MacdDefinitionRepository macdDefinitionRepository;

	@Autowired
	private TradingPairs tradingPairs;

	@Bean
	public TradingPairs getTradingPairs() {

		final TradingPairs tradingPairs = new TradingPairs();

		Function<UriBuilder, URI> uri = uriBuilder -> uriBuilder.path("/AssetPairs")
				.build();
		Mono<JSONObject> pairsMono = krakenService.queryPublicAPI(uri);
		JSONObject pairs = pairsMono.block();
		JSONObject krakenPairs = pairs.getJSONObject("result");
		tradingPairs.setValues(krakenPairs.keySet());

		return tradingPairs;
	}

	/**
	 * temporaire pour test ?
	 * @param args
	 */
	@Override
	public void run(String... args) {

		log.info("StartApplication...");

		/*
		String pair = "XETHZEUR";
		if(!tradingPairs.getValues().contains(pair)) {
			return;
		}
		//1
		ChartEntity chartEntity = new ChartEntity();
		chartEntity.setExchange("Kraken");
		chartEntity.setTradingPair(pair);
		chartEntity.setTimeFrameInterval(Intervals.ONE_HOUR.getMinutes());
		chartRepository.create(chartEntity);
		//2
		chartEntity = new ChartEntity();
		chartEntity.setExchange("Kraken");
		chartEntity.setTradingPair(pair);
		chartEntity.setTimeFrameInterval(Intervals.ONE_DAY.getMinutes());
		chartRepository.create(chartEntity);
		//3
		chartEntity = new ChartEntity();
		chartEntity.setExchange("Kraken");
		chartEntity.setTradingPair(pair);
		chartEntity.setTimeFrameInterval(Intervals.FOUR_HOURS.getMinutes());
		chartRepository.create(chartEntity);

		MacdDefinitionEntity macdDefinitionEntity = new MacdDefinitionEntity();
		macdDefinitionEntity.setShortPeriodEma(12);
		macdDefinitionEntity.setLongPeriodEma(26);
		macdDefinitionEntity.setMacdEma(9);
		macdDefinitionEntity.setChart(chartEntity);
		macdDefinitionRepository.create(macdDefinitionEntity);

		UtilisateurEntity utilisateurEntity = new UtilisateurEntity();
		utilisateurEntity.setEmail("a.vergnaud@gmail.com");
		utilisateurEntity.setWirePusherId("bpfxmpnbJ");
		Set<MacdDefinitionEntity> macdDefinitionEntities = new HashSet<>();
		macdDefinitionEntities.add(macdDefinitionEntity);
		utilisateurEntity.setMacdDefinitions(macdDefinitionEntities);
		utilisateurRepository.create(utilisateurEntity);
		*/
	}

	public static void main(String[] args) {
		SpringApplication.run(MacdDefinitionApplication.class, args);
	}

}
