package com.stockportfolio.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Util {
	
	public static HashMap<Double,String> getTotalPrice(String symbol, int quantity) throws Exception{
		
		double total=getLatestPrice(symbol)*quantity;
		String now = LocalDateTime.now().toString();
		HashMap<Double,String> datamap =new HashMap<>();
		datamap.put(total, now);
		return datamap;
	}
	public static HashMap<Double,String> getTotalPrice(String symbol) throws Exception{
			
			double total=getLatestPrice(symbol);
			String now = LocalDateTime.now().toString();
			HashMap<Double,String> datamap =new HashMap<>();
			datamap.put(total, now);
			return datamap;
		}
	public static double getLatestPrice(String symbol) throws Exception {
		
		
	
        String url = "https://api.twelvedata.com/price?symbol="+symbol+"&apikey=756d906150354287982c5446c03d8658";

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        conn.disconnect();
        
        
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(content.toString());
        String priceStr = (String) jsonObject.get("price");
        if(priceStr == null) {
        	throw new IllegalArgumentException("not found");
        }
        double price = Double.parseDouble(priceStr);
        return price;
        

        }
	
     
    }