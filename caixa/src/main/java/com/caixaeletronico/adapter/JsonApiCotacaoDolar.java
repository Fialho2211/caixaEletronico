package com.caixaeletronico.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonApiCotacaoDolar {

    public String obterValorAtualDolar(String jsonResposta) {
        try {
            if (jsonResposta != null) {
                JSONArray jsonArray = new JSONArray(jsonResposta);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getString("bid");
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
