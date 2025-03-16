import hourlyWeather.HourlyPeriod;
import hourlyWeather.HourlyRoot;
import javafx.util.Pair;
import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class MyWeatherAPI extends WeatherAPI {

	public static ArrayList<Pair<String, Double[]>> getCoords(String city, String state){
		city = city.replace(" ", "&");
		state = state.replace(" ", "&");
		String requestString = "https://api.mapbox.com/search/geocode/v6/forward?"+
		"place=" + city + "&" + "region=" + state + "&country=United&States&access_token=" + System.getenv("MAPBOX_API_KEY");

		System.out.println(requestString);

		//https://api.mapbox.com/search/geocode/v6/forward?place=&region=&country=&access_token=
		//https://api.mapbox.com/search/geocode/v6/forward?country=us&address_number=1600&street=pennsylvania%20ave%20nw&postcode=20500&place=Washington%20dc&access_token=???
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(requestString))
				//.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList<Pair<String, Double[]>> coordsArray = new ArrayList<>();
		try{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.body());

			Iterator<JsonNode> iterator = root.at("/features").elements();
			int index = 0;
			while(iterator.hasNext()){
				JsonNode countryNode = root.at("/features/" + index + "/properties/context/country/name");
				if(!countryNode.asText().equals("United States")){
					iterator.next();
					index++;
					continue;
				}

				JsonNode latitudeNode = root.at("/features/" + index + "/properties/coordinates/latitude");
				JsonNode longitudeNode = root.at("/features/" + index + "/properties/coordinates/longitude");
				JsonNode addressNode = root.at("/features/" + index + "/properties/full_address");

				if(longitudeNode == null || latitudeNode == null || addressNode == null){
					System.out.println("Failed to parse features JSon");
					return null;
				}

				Double[] coords = new Double[2];
				String address = addressNode.asText();

				String lat = String.format("%.4f", latitudeNode.asDouble());
				coords[0] = Double.parseDouble(lat);

				String lon = String.format("%.4f", longitudeNode.asDouble());
				coords[1] = Double.parseDouble(lon);

				Pair<String, Double[]> coordsPair = new Pair<>(address, coords);
				coordsArray.add(coordsPair);

				iterator.next();
				index++;
			}

		}catch (JsonProcessingException e){
			e.printStackTrace();
		}

		if(coordsArray.isEmpty()){
			System.err.println("Failed to parse MapBox JSon");
			return null;
		}

		return coordsArray;
	}

	public static Pair<String, int[]> getGridInfo(Double latitude, Double longitude){
		String requestString = "https://api.weather.gov/points/" + latitude + "," + longitude;

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(requestString))
				//.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Pair<String, int[]> gridPair = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.body());

			JsonNode gridIDNode = root.at("/properties/gridId");
			JsonNode gridXNode = root.at("/properties/gridX");
			JsonNode gridYNode = root.at("/properties/gridY");

			if(gridIDNode == null || gridXNode == null || gridYNode == null){
				System.out.println("Failed to parse properties JSon");
				return null;
			}

			int[] gridCoords = {gridXNode.asInt(), gridYNode.asInt()};
			gridPair = new Pair<>(gridIDNode.asText(), gridCoords);

		}catch (JsonProcessingException e){
			e.printStackTrace();
		}

		if(gridPair == null){
			System.err.println("Failed to parse points JSon");
			return null;
		}

		System.out.println(gridPair.getKey() + " " + Arrays.toString(gridPair.getValue()));
		return gridPair;
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