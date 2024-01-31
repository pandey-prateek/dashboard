package com.orange.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.sql.Timestamp;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.models.RowRecord;
@CrossOrigin(origins = "http://localhost:5000", allowedHeaders = "*")
@RestController
public class RestControllerAll {
    @RequestMapping("/")
    public String hello() {
        return "Hello User";
    }

    @RequestMapping("/csv")
    public String parseCSV() throws FileNotFoundException, IOException, ParseException {
        int i=0;
        List<RowRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("dataset.csv"))) {
            String line=br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.parse(values[0], formatter);
                RowRecord row=new RowRecord(i++,now,Double.parseDouble(values[1]));
                records.add(row);
            }
        }
        Map<String,Double> groups=group(records);
        String json = new ObjectMapper().writeValueAsString(groups);
        return json;
    }
    public Map<String,Double> group(List<RowRecord> records){
        Map<String,Double> map=new TreeMap<>();
        Map<String,Integer> freq=new HashMap();
        for(RowRecord row:records){
            String key=row.getTs().getYear()+"-"+(row.getTs().getMonthValue()<10?("0"+row.getTs().getMonthValue()):row.getTs().getMonthValue());
            map.put(key,map.getOrDefault(key,0.0)+row.getValue());
            freq.put(key,freq.getOrDefault(key,0)+1);
        }
        for(String key:map.keySet()){
            map.put(key,map.getOrDefault(key,0.0)/freq.getOrDefault(key,1));
        }

       return map;
    }

}
