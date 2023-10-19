package com.hallouin.model.ecologic.api;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EcologicApiClient {
	private static String API_KEY;
	OkHttpClient client;

	public EcologicApiClient(String apiKey) {
		super();
		API_KEY = apiKey;
		client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS) // Augmentez le timeout de connexion
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)    // Augmentez le timeout de lecture
                .build();
	}

	public String sendGetRequest (String url) throws IOException {
		System.out.println("ecologicClient get url :"+url );
		String responseBody = "";

	        Request request = new Request.Builder()
	                .url(url)
	                .addHeader("Accept", "application/json")
	                .addHeader("api_key", API_KEY)
	                .build();

	        Call call = client.newCall(request);
	        Response response = call.execute(); // Synchronously execute the request

	        //int statusCode = response.code();
	        //Headers headers = response.headers();
	        responseBody = response.body().string().trim();

		return responseBody;
	}

	public String sendPostRequest (String url, String jsonBody) throws IOException {
		String responseBody = "";

		// Créer le contenu JSON à envoyer dans la requête POST
        MediaType mediaType = MediaType.parse("application/json");

        // Créer la requête POST avec le contenu JSON
        RequestBody requestBody = RequestBody.create(jsonBody,mediaType);


	        Request request = new Request.Builder()
	                .url(url)
	                .addHeader("Accept", "application/json")
	                .addHeader("api_key", API_KEY)
	                .post(requestBody)
	                .build();

	        Call call = client.newCall(request);
	        Response response = call.execute(); // Synchronously execute the request

	        //int statusCode = response.code();
	        //Headers headers = response.headers();
	        responseBody = response.body().string();

		return responseBody;
	}
}