import hourlyWeather.HourlyPeriod;
import hourlyWeather.HourlyRoot;
import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class MyWeatherAPI extends WeatherAPI {

	public Double[] getCoords(){
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.mapbox.com/search/geocode/v6/forward?place=detroit&region=Michigan&country=us&limit=1&access_token="+System.getenv("MAPBOX_API_KEY")))
				//.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode coordsNode = null;
		try{
			JsonNode jsnode = mapper.readTree(response.body());
			coordsNode = jsnode.at("/features/0/geometry/coordinates");
		}catch (JsonProcessingException e){
			e.printStackTrace();
		}

		if(coordsNode == null){
			System.err.println("Failed to parse JSon");
			return null;
		}

		Double[] coords = new Double[2];
		coords[0] = coordsNode.get(0).asDouble();
		coords[1] = coordsNode.get(1).asDouble();
		return coords;
	}

	public static ArrayList<HourlyPeriod> getHourlyForecast(String region, int gridx, int gridy) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://api.weather.gov/gridpoints/"+region+"/"+String.valueOf(gridx)+","+String.valueOf(gridy)+"/forecast/hourly"))
				//.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		HourlyRoot r = getHourlyObject(response.body());
		if(r == null){
			System.err.println("Failed to parse JSon");
			return null;
		}
		return r.properties.periods;
	}
	public static HourlyRoot getHourlyObject(String json){
		ObjectMapper om = new ObjectMapper();
		HourlyRoot toRet = null;
		try {
			toRet = om.readValue(json, HourlyRoot.class);
			ArrayList<HourlyPeriod> p = toRet.properties.periods;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return toRet;

	}
}