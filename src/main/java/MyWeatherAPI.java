import weather.WeatherAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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



}