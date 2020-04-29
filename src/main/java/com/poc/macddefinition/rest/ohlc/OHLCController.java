package com.poc.macddefinition.rest.ohlc;

import com.poc.macddefinition.persistence.ohlc.OHLCEntity;
import com.poc.macddefinition.persistence.ohlc.OHLCRepository;
import com.poc.macddefinition.rest.macd.Macd;
import fr.xebia.extras.selma.Selma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ohlc")
public class OHLCController {

    @Autowired
    private OHLCRepository ohlcRepository;

    private OHLCMapper ohlcMapper = Selma.builder(OHLCMapper.class).build();

    @GetMapping("/")
    @ResponseBody
    public List<OHLC> search(@RequestParam Long chartEntityId, @RequestParam(required = false) Integer last) {

        List<OHLCEntity> liste = new ArrayList<>();
        if(last != null) {
            liste = ohlcRepository.getByChartEntityId(chartEntityId, last);
        } else {
            liste = ohlcRepository.getByChartEntityId(chartEntityId);
        }

        return liste.stream()
                .map(ohlcMapper::asOHLC)
                .collect(Collectors.toList());
    }
}
