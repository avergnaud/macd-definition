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

    @GetMapping(value = "/extract.csv")
    public void fooAsCSV(HttpServletResponse response, @RequestParam Long chartEntityId) throws IOException {

        List<OHLCEntity> liste = ohlcRepository.getByChartEntityId(chartEntityId);
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        int index = 0;
        for(int i=liste.size()-1; i>=0; i--) {
            OHLCEntity ohlcEntity = liste.get(i);
            java.util.Date time=new java.util.Date((long) ohlcEntity.getTimeEpochTimestamp()*1000);
            sb.append(ohlcEntity.getTimeEpochTimestamp());
            sb.append(",");
            sb.append(dateFormat.format(time));
            sb.append(",");
            sb.append(++index);
            sb.append(",");
            sb.append(ohlcEntity.getClosingPrice());
            sb.append(System.getProperty("line.separator"));
        }

        response.setContentType("text/plain; charset=utf-8");
        response.getWriter().print(sb.toString());
    }
}
