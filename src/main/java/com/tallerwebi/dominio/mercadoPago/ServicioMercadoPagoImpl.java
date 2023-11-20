package com.tallerwebi.dominio.mercadoPago;

import com.tallerwebi.dominio.pedido.Pedido;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.OutputStream;
import java.net.URL;
import org.json.JSONObject;

@Service("ServicioPago")
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago{
    private final String API_URL = "https://api.mercadopago.com/checkout/preferences";
    private final String ACCESS_TOKEN = "TEST-2591301662628250-112013-bef180cd58f4d64d50d056542d3f0d85-151386142";


    @Override
    public String pagarPedido(Double precioTotal) throws IOException {
        String apiKey = "TEST-2591301662628250-112013-bef180cd58f4d64d50d056542d3f0d85-151386142";
        System.out.println("aca");
        // Replace with the Mercado Pago API endpoint URL
        URL url = new URL("https://api.mercadopago.com/checkout/preferences");

        // Create an HTTP connection to the API endpoint
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setDoOutput(true);
        System.out.println("aca2");
        // Create a JSON object with the data to send to the API
        String jsonInputString = "{\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"title\": \"Libros\",\n" +
                "      \"description\": \"LibrArte\",\n" +
                "      \"category_id\": \"libros\",\n" +
                "      \"quantity\": 1,\n" +
                "      \"currency_id\": \"ARS\",\n" +
                "      \"unit_price\": " + precioTotal + "\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Write the JSON object to the output stream of the HTTP connection
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Read the response from the API
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // Parse the response JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Extract the preference ID
            String preferenceId = jsonResponse.getString("id");
            System.out.println("JSON enviado: " + jsonInputString);

            // Print the preference ID
            return preferenceId;
        }
    }
}
