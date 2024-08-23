package com.caixaeletronico.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCotacaoDolar {
    public String obterCotacaoDolar() {
        String urlStr = "https://economia.awesomeapi.com.br/json/USD-BRL";
        StringBuilder respostaJson = new StringBuilder();

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    respostaJson.append(inputLine);
                }
                in.close();
                return respostaJson.toString();
            } else {
                System.out.println("Erro ao obter resposta da API. Código de resposta: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

