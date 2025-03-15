import hourlyWeather.HourlyPeriod;
import hourlyWeather.HourlyRoot;
import javafx.util.Pair;
import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class MyWeatherAPI extends WeatherAPI {

	public static ArrayList<Pair<Double[], String>> getCoords(String city, String state, String country){
		String requestString = "https://api.mapbox.com/search/geocode/v6/forward?"+
		"place=" + city + "&" + "region=" + state + "&" + "country=" + country + "&" + "access_token=" + System.getenv("MAPBOX_API_KEY");

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

		ObjectMapper mapper = new ObjectMapper();

		ArrayList<Pair<Double[], String>> coordsArray = new ArrayList<>();
		try{
			JsonNode root = mapper.readTree(response.body());
//			for(int i = 0; i < 3; i++){
//
//				JsonNode coordsNode = root.at("/features/" + i + "/geometry/coordinates");
//				JsonNode addressNode = root.at("/features/" + i + "/properties/full_address");
//
//				if(coordsNode == null){
//					System.out.println("No coordinate found");
//				}
//
//				if(addressNode == null){
//					System.out.println("No address found");
//				}
//
//				Double[] coords = new Double[2];
//				String address = addressNode.asText();
//
//				coords[0] = coordsNode.get(0).asDouble();
//				coords[1] = coordsNode.get(1).asDouble();
//
//				Pair<Double[], String> coordsPair = new Pair<>(coords, address);
//				coordsArray.add(coordsPair);
//			}

			//[{"type":"Feature","id":"dXJuOm1ieHBsYzpBNGxJN0E","geometry":{"type":"Point","coordinates":[-87.63236,41.881954]},"properties":{"mapbox_id":"dXJuOm1ieHBsYzpBNGxJN0E","feature_type":"place","full_address":"Chicago, Illinois, United States","name":"Chicago","name_preferred":"Chicago","coordinates":{"longitude":-87.63236,"latitude":41.881954},"place_formatted":"Illinois, United States","bbox":[-87.869226,41.632652,-87.496959,42.034895],"context":{"district":{"mapbox_id":"dXJuOm1ieHBsYzpUc2Jz","name":"Cook County","wikidata_id":"Q108418"},"region":{"mapbox_id":"dXJuOm1ieHBsYzpST3c","name":"Illinois","wikidata_id":"Q1204","region_code":"IL","region_code_full":"US-IL"},"country":{"mapbox_id":"dXJuOm1ieHBsYzpJdXc","name":"United States","wikidata_id":"Q30","country_code":"US","country_code_alpha_3":"USA"},"place":{"mapbox_id":"dXJuOm1ieHBsYzpBNGxJN0E","name":"Chicago","wikidata_id":"Q1297"}}}},
			// {"type":"Feature","id":"dXJuOm1ieHBsYzpBNG1JN0E","geometry":{"type":"Point","coordinates":[-87.640564,41.50247]},"properties":{"mapbox_id":"dXJuOm1ieHBsYzpBNG1JN0E","feature_type":"place","full_address":"Chicago Heights, Illinois, United States","name":"Chicago Heights","name_preferred":"Chicago Heights","coordinates":{"longitude":-87.640564,"latitude":41.50247},"place_formatted":"Illinois, United States","bbox":[-87.674425,41.484211,-87.586311,41.542567],"context":{"district":{"mapbox_id":"dXJuOm1ieHBsYzpUc2Jz","name":"Cook County","wikidata_id":"Q108418"},"region":{"mapbox_id":"dXJuOm1ieHBsYzpST3c","name":"Illinois","wikidata_id":"Q1204","region_code":"IL","region_code_full":"US-IL"},"country":{"mapbox_id":"dXJuOm1ieHBsYzpJdXc","name":"United States","wikidata_id":"Q30","country_code":"US","country_code_alpha_3":"USA"},"place":{"mapbox_id":"dXJuOm1ieHBsYzpBNG1JN0E","name":"Chicago Heights","wikidata_id":"Q578277"}}}},
			// {"type":"Feature","id":"dXJuOm1ieHBsYzpBNG1vN0E","geometry":{"type":"Point","coordinates":[-87.77926,41.701492]},"properties":{"mapbox_id":"dXJuOm1ieHBsYzpBNG1vN0E","feature_type":"place","full_address":"Chicago Ridge, Illinois, United States","name":"Chicago Ridge","name_preferred":"Chicago Ridge","coordinates":{"longitude":-87.77926,"latitude":41.701492},"place_formatted":"Illinois, United States","bbox":[-87.798513,41.690398,-87.75921,41.720345],"context":{"district":{"mapbox_id":"dXJuOm1ieHBsYzpUc2Jz","name":"Cook County","wikidata_id":"Q108418"},"region":{"mapbox_id":"dXJuOm1ieHBsYzpST3c","name":"Illinois","wikidata_id":"Q1204","region_code":"IL","region_code_full":"US-IL"},"country":{"mapbox_id":"dXJuOm1ieHBsYzpJdXc","name":"United States","wikidata_id":"Q30","country_code":"US","country_code_alpha_3":"USA"},"place":{"mapbox_id":"dXJuOm1ieHBsYzpBNG1vN0E","name":"Chicago Ridge"}}}},
			// {"type":"Feature","id":"dXJuOm1ieHBsYzpBNVZJN0E","geometry":{"type":"Point","coordinates":[-92.88973,45.373726]},"properties":{"mapbox_id":"dXJuOm1ieHBsYzpBNVZJN0E","feature_type":"place","full_address":"Chisago City, Minnesota, United States","name":"Chisago City","name_preferred":"Chisago City","coordinates":{"longitude":-92.88973,"latitude":45.373726},"place_formatted":"Minnesota, United States","bbox":[-92.937892,45.296244,-92.817189,45.438657],"context":{"district":{"mapbox_id":"dXJuOm1ieHBsYzpQbWJz","name":"Chisago County","wikidata_id":"Q486309"},"region":{"mapbox_id":"dXJuOm1ieHBsYzpBNFRz","name":"Minnesota","wikidata_id":"Q1527","region_code":"MN","region_code_full":"US-MN"},"country":{"mapbox_id":"dXJuOm1ieHBsYzpJdXc","name":"United States","wikidata_id":"Q30","country_code":"US","country_code_alpha_3":"USA"},"place":{"mapbox_id":"dXJuOm1ieHBsYzpBNVZJN0E","name":"Chisago City","wikidata_id":"Q2195827"}}}},
			// {"type":"Feature","id":"dXJuOm1ieHBsYzpiNGor","geometry":{"type":"Point","coordinates":[28.060133,-32.683223]},"properties":{"mapbox_id":"dXJuOm1ieHBsYzpiNGor","feature_type":"place","full_address":"Chicargo, Eastern Cape, South Africa","name":"Chicargo","name_preferred":"Chicargo","coordinates":{"longitude":28.060133,"latitude":-32.683223},"place_formatted":"Eastern Cape, South Africa","bbox":[28.05394,-32.686416,28.06491,-32.68005],"context":{"region":{"mapbox_id":"dXJuOm1ieHBsYzpSUDQ","name":"Eastern Cape","wikidata_id":"Q130840","region_code":"EC","region_code_full":"ZA-EC"},"country":{"mapbox_id":"dXJuOm1ieHBsYzpJdjQ","name":"South Africa","wikidata_id":"Q258","country_code":"ZA","country_code_alpha_3":"ZAF"},"place":{"mapbox_id":"dXJuOm1ieHBsYzpiNGor","name":"Chicargo"}}}}]

			Iterator<JsonNode> iterator = root.at("/features").elements();
			int index = 0;
			while(iterator.hasNext()){
				JsonNode coordsNode = root.at("/features/" + index + "/geometry/coordinates");
				JsonNode addressNode = root.at("/features/" + index + "/properties/full_address");

				if(coordsNode == null || addressNode == null){
					System.out.println("Failed to parse JSon");
					return null;
				}

				Double[] coords = new Double[2];
				String address = addressNode.asText();

				coords[0] = coordsNode.get(0).asDouble();
				coords[1] = coordsNode.get(1).asDouble();

				Pair<Double[], String> coordsPair = new Pair<>(coords, address);
				coordsArray.add(coordsPair);

				iterator.next();
				index++;
			}

		}catch (JsonProcessingException e){
			e.printStackTrace();
		}

		if(coordsArray.isEmpty()){
			System.err.println("Failed to parse JSon");
			return null;
		}

		return coordsArray;
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