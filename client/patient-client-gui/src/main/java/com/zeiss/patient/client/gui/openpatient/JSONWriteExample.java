package com.zeiss.patient.client.gui.openpatient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.LinkedHashMap;
import java.util.Map;

public class JSONWriteExample {
    public static void main(String[] args)  {
        // creating JSONObject
        JSONObject jo = new JSONObject();

        JSONArray linesArray = new JSONArray();


        // store line from (10/22) to (133/256)
        {
            Map<String, Map<String, String>> lineMap = new LinkedHashMap<>();

            Map<String, String> startPointMap = new LinkedHashMap<>();
            startPointMap.put("x", "10.0");
            startPointMap.put("y", "22.0");
            lineMap.put("startPoint", startPointMap);

            Map<String, String> endPointMap = new LinkedHashMap<>();
            endPointMap.put("x", "133.0");
            endPointMap.put("y", "256.0");
            lineMap.put("endPoint", endPointMap);
            linesArray.add(lineMap);
        }


        // store line from (44/78) to (873/996)
        {
            Map<String, Map<String, String>> lineMap = new LinkedHashMap<>();

            Map<String, String> startPointMap = new LinkedHashMap<>();
            startPointMap.put("x", "44.0");
            startPointMap.put("y", "78.0");
            lineMap.put("startPoint", startPointMap);

            Map<String, String> endPointMap = new LinkedHashMap<>();
            endPointMap.put("x", "873.0");
            endPointMap.put("y", "996.0");
            lineMap.put("endPoint", endPointMap);
            linesArray.add(lineMap);
        }


        jo.put("lines", linesArray);


        System.out.println("Convert data to String: ");

        System.out.println("lines="+jo.toJSONString());

        System.out.println("----------------------------------------------");

        System.out.println("Convert String back to data: ");

        try {
            Object obj = new JSONParser().parse(jo.toJSONString());

            // typecasting obj to JSONObject
            JSONObject jo1 = (JSONObject) obj;

            JSONArray lines = (JSONArray) jo1.get("lines");

            lines.forEach(line->{

                System.out.println("There is one more line .... ");

                Map<String, Map<String, String>> lineMap = (Map<String, Map<String, String>>) line;

                Map<String, String> map = lineMap.get("startPoint");
                System.out.println("\tThe line starts with the point: ");
                System.out.println("\t\tx value is " + map.get("x"));
                System.out.println("\t\ty value is " + map.get("y"));

                Map<String, String> map2 = lineMap.get("endPoint");
                System.out.println("\tThe line ends with the point: ");
                System.out.println("\t\tx value is " + map2.get("x"));
                System.out.println("\t\ty value is " + map2.get("y"));

            });

        } catch (ParseException e) {

            e.printStackTrace();
        }

    }
}
