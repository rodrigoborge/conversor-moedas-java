import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ConsultaValor {

    public ExchangeRateApi consultaValor(String codigoMoeda) throws RuntimeException {
        URI valorConsultado =
                URI.create("https://v6.exchangerate-api.com/v6/45592de414b9171022b43939/latest/" + codigoMoeda);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(valorConsultado)
                .build();

        try {
            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return parseExchangeRate(response.body());
        } catch (Exception e) {
            throw new RuntimeException("Moeda inválida!");
        }
    }

    private ExchangeRateApi parseExchangeRate(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        String baseCode = jsonObject.get("base_code").getAsString();
        Map<String, Double> filteredRates = filterRates(jsonObject.getAsJsonObject("conversion_rates"));
        return new ExchangeRateApi(baseCode, filteredRates);
    }

    private Map<String, Double> filterRates(JsonObject conversionRatesObject) {
        Map<String, Double> filteredRates = new HashMap<>();
        for (String currency : new String[]{"USD", "EUR", "GBP", "BRL", "JPY", "CHF"}) {
            if (conversionRatesObject.has(currency)) {
                filteredRates.put(currency, conversionRatesObject.get(currency).getAsDouble());
            }
        }
        return filteredRates;
    }
}
