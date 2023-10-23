package com.hallouin.model.ecosystem.api;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EcosystemApiClient {

	private final OkHttpClient client;

	public EcosystemApiClient() {
		super();
		client = new OkHttpClient();
	}
	
	public String sendGetRequest (String url, String token) throws IOException {
		String responseBody = "";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", token)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute(); // Synchronously execute the request

        //int statusCode = response.code();
        //Headers headers = response.headers();
        responseBody = response.body().string().trim();

		return responseBody;
	}
	
	public String sendConnectPostRequest (String url, String jsonBody) throws IOException {
		String responseBody = "";

		// Créer le contenu JSON à envoyer dans la requête POST
        MediaType mediaType = MediaType.parse("application/json");

        // Créer la requête POST avec le contenu JSON
        RequestBody requestBody = RequestBody.create(jsonBody,mediaType);


	        Request request = new Request.Builder()
	                .url(url)
	                .addHeader("Accept", "application/json")
	                .post(requestBody)
	                .build();

	        Call call = client.newCall(request);
	        Response response = call.execute(); // Synchronously execute the request

	        responseBody = response.body().string();

		return responseBody;
	}

	public String sendPostRequest (String url, String token, String jsonBody) throws IOException {
		String responseBody = "";

		// Créer le contenu JSON à envoyer dans la requête POST
        MediaType mediaType = MediaType.parse("application/json");

        // Créer la requête POST avec le contenu JSON
        RequestBody requestBody = RequestBody.create(jsonBody,mediaType);


	        Request request = new Request.Builder()
	                .url(url)
	                .addHeader("Accept", "application/json")
	                .addHeader("Authorization", token)
	                .post(requestBody)
	                .build();

	        Call call = client.newCall(request);
	        Response response = call.execute(); // Synchronously execute the request

	        //int statusCode = response.code();
	        //Headers headers = response.headers();
	        responseBody = response.body().string();

		return responseBody;
	}

	public int sendPutRequest (String url, String path) throws IOException {

        File file = new File(path);

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody requestBody = RequestBody.create(file,mediaType);


	        Request request = new Request.Builder()
	                .url(url)
	                .addHeader("Accept", "application/json")
	                .put(requestBody)
	                .build();

	        Call call = client.newCall(request);
	        Response response = call.execute(); // Synchronously execute the request

	        int statusCode = response.code();

		return statusCode;
	}
	public Boolean sendDeleteRequest (String url, String token) throws IOException {

	        Request request = new Request.Builder()
	                .url(url)
	                .addHeader("Accept", "application/json")
	                .addHeader("Authorization", token)
	                .delete()
	                .build();

	        Call call = client.newCall(request);
	        Response response = call.execute(); // Synchronously execute the request

	        // Assurez-vous de fermer la réponse pour libérer les ressources
            response.close();

	        // Vérifiez si la requête a réussi (code de statut 200 à 299 est considéré comme succès)
            if (response.isSuccessful()) {
                System.out.println("La requête DELETE a réussi !");
                return true;
            } else {
                System.out.println("La requête DELETE a échoué. Code de statut : " + response.code());
                return false;
            }


	}
}