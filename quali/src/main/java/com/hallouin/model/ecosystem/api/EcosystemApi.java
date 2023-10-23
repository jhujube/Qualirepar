package com.hallouin.model.ecosystem.api;


import java.io.IOException;

import com.google.gson.Gson;
import com.hallouin.model.bill.FileInformations;
import com.hallouin.model.ecosystem.api.pojo.FileInfos;
import com.hallouin.model.ecosystem.api.request_pojo.Connect;
import com.hallouin.model.ecosystem.api.request_pojo.NewClaim;
import com.hallouin.model.ecosystem.api.response_pojo.LoginInformations;

public class EcosystemApi {
	private final String user;
	private final String pwd;
	private final String urlEcosystem;

	private EcosystemApiClient ecosystemApiClient;
	private String idToken;

	public EcosystemApi (String user, String pwd, String url) {
		super();
		this.ecosystemApiClient = new EcosystemApiClient();
		this.user = user;
		this.pwd = pwd;
		this.urlEcosystem = url;

	}

	private LoginInformations connectEcosystemApi(String username, String password) {

		Connect connect = new Connect(username, password);

		Gson gson = new Gson();
    	String jsonBody = gson.toJson(connect);

		String jsonResponse="";
		try {
			System.out.println("request json :"+jsonBody);
			jsonResponse = ecosystemApiClient.sendConnectPostRequest(urlEcosystem+"/login", jsonBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("connect ecosystemApi response json :"+jsonResponse);
		LoginInformations loginInformations = gson.fromJson(jsonResponse, LoginInformations.class);

		this.idToken = "Bearer "+loginInformations.getIdToken();
		return loginInformations;
	}

	public String getDatas(String query) {
		if (idToken == null)
			connectEcosystemApi(user,pwd);

		String jsonResponse="";

    	String url = urlEcosystem+query;
		try {
			jsonResponse = ecosystemApiClient.sendGetRequest(url, idToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("response json for connection:"+jsonResponse);

		return jsonResponse;
	}
	
	public String postNewClaim(NewClaim newClaim) {
		if (idToken == null)
			connectEcosystemApi(user,pwd);

		String jsonResponse="";

		String url = urlEcosystem+"/reimbursement-claims/new_claim";

		Gson gson = new Gson();
    	String jsonBody = gson.toJson(newClaim);
    	System.out.println(jsonBody);

    	try {
			jsonResponse = ecosystemApiClient.sendPostRequest(url, idToken, jsonBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("response json :"+jsonResponse);

		return jsonResponse;

	}

	public String postUploadRequest(String claimId, FileInformations fileInfo) {
		if (idToken == null)
			connectEcosystemApi(user,pwd);

		String jsonResponse="";

		String url = urlEcosystem+"/reimbursement-claims/"+ claimId +"/upload-file";
		FileInfos fileInfos = new FileInfos(fileInfo.getName()+"."+fileInfo.getExtension(), fileInfo.getType(), fileInfo.getSize());
		Gson gson = new Gson();
    	String jsonBody = gson.toJson(fileInfos);
    	System.out.println(jsonBody);

    	try {
			jsonResponse = ecosystemApiClient.sendPostRequest(url, idToken, jsonBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("response json :"+jsonResponse);

		return jsonResponse;
	}

	public Boolean putUploadFile(String uploadUrl, FileInformations fileInfo) {
		if (idToken == null)
			connectEcosystemApi(user,pwd);

		boolean response = false;

		String url = uploadUrl;
		String path = fileInfo.getPath();
		System.out.println("file Path :"+path);
		try {
			int responseCode = ecosystemApiClient.sendPutRequest(url, path);
			System.out.println("response code :"+responseCode);
			if (responseCode == 200)
					response = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return response;
	}

	public String postConfirmClaim(String claimId) {
		if (idToken == null)
			connectEcosystemApi(user,pwd);

		String jsonResponse="";

		String url = urlEcosystem+"/reimbursement-claims/"+claimId+"/confirm-claim";

    	try {
			jsonResponse = ecosystemApiClient.sendPostRequest(url, idToken,"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("response json :"+jsonResponse);

		return jsonResponse;
	}

	public Boolean deleteClaim(String claimId) {
		if (idToken == null)
			connectEcosystemApi(user,pwd);

		Boolean response = false;

		String url = urlEcosystem+"/reimbursement-claims/"+claimId;

    	try {
			response = ecosystemApiClient.sendDeleteRequest(url, idToken);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("response json :"+response);

		return response;
	}

}