package commands;
import java.net.*;
import java.io.*;
import java.io.BufferedReader;

import org.json.JSONObject;


public class get_crypto_data {

    public String get_json() throws Exception{

        String json_url = "https://api.coindesk.com/v1/bpi/currentprice.json";

        URL url = new URL(json_url);
        URLConnection conn = url.openConnection();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String jsonText = reader.readLine();
        JSONObject json = new JSONObject(jsonText);

        String time = json.getJSONObject("time").getString("updated");

        String eur = json.getJSONObject("bpi").getJSONObject("EUR").getString("rate");
        String usd = json.getJSONObject("bpi").getJSONObject("USD").getString("rate");
        String gbp = json.getJSONObject("bpi").getJSONObject("GBP").getString("rate");

        String r = "EUR:    **" + eur + "€**\n\nUSD:   **" + usd + "$**\n\nGBP:  **" + gbp + "£**\n\n" +
                "_updated: " + time + "_";

        reader.close();

        return r;
    }
}
