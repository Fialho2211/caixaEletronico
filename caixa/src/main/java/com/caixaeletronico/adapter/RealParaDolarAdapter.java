package com.caixaeletronico.adapter;

public class RealParaDolarAdapter {
    public ApiCotacaoDolar apiCotacaoDolar = new ApiCotacaoDolar();
    public JsonApiCotacaoDolar jsonApiCotacaoDolar = new JsonApiCotacaoDolar();    
    
    public double convertToDolar(double amount) {        
        
        String jsonCotacao = apiCotacaoDolar.obterCotacaoDolar();
        String valorAtualDolarStr = jsonApiCotacaoDolar.obterValorAtualDolar(jsonCotacao);        

        if (valorAtualDolarStr != null) {
            double valorAtualDolar = Double.parseDouble(valorAtualDolarStr);
            return amount / valorAtualDolar;
        }
        return 0;
    }
}
