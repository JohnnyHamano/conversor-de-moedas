import com.google.gson.Gson;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Exchanger
{
	private static final String API_URL = "https://v6.exchangerate-api.com/v6/";
	private static final String API_KEY = "YOUR_API_KEY";
	private String base_code;
	private JSONObject conversion_rates;

	public Exchanger(String base_code)
	{
		this.base_code = base_code;
		retrieveData();
	}
	
	public Double convertTo(String target_code)
	{
		try
		{
			double exchange_rate = conversion_rates.getDouble(target_code);
			return exchange_rate;
		}
		catch (Exception e)
		{
			System.out.println("Código inválido.");
			throw new RuntimeException(e);
		}
	}
	
	private void retrieveData()
	{
		String uri = API_URL + API_KEY + "/latest/" + base_code;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();

		try
		{
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();
			JSONObject json = new JSONObject(responseBody);
			
			if (!(json.getString("result")).equals("error"))
			{
				Currency currency = new Gson().fromJson(responseBody, Currency.class);

				String data = new Gson().toJson(currency.conversion_rates());
				conversion_rates = new JSONObject(data);
			}
			else
			{
				throw new RuntimeException(json.getString("error-type"));
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public String getBaseCode() {
		return base_code;
	}

	public JSONObject getConversionRates() {
		return conversion_rates;
	}
}