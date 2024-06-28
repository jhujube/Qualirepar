package com.hallouin;

import com.hallouin.controler.claim.ClaimController;
import com.hallouin.controler.ecologic.EcologicController;
import com.hallouin.controler.ecosystem.EcosystemController;
import com.hallouin.model.claim.ClaimModel;
import com.hallouin.model.datas.AppDatas;
import com.hallouin.model.ecologic.EcologicModel;
import com.hallouin.model.ecosystem.EcosystemModel;
import com.hallouin.model.ecosystem.api.EcosystemApi;
import com.hallouin.view.DialogsView;
import com.hallouin.view.MainView;
import com.hallouin.view.claimCreation.ClaimView;
import com.hallouin.view.ecologic.EcologicView;
import com.hallouin.view.ecosystem.EcosystemView;

public class MainApp {
	static DialogsView dialogsView;
	private static EcosystemApi ecosystemApi;

	public static void main(String[] args) {
		dialogsView = new DialogsView();

		AppDatas appDatas = GetAppDatas.getDatas(dialogsView);

		ecosystemApi = new EcosystemApi(appDatas.getEcosystemUser(), appDatas.getEcosystemPwd(), appDatas.getEcosystemUrl());

		ClaimModel claimModel = new ClaimModel(appDatas,ecosystemApi);
		ClaimView claimView = new ClaimView(claimModel);

		EcosystemModel ecosystemModel = new EcosystemModel(ecosystemApi);
		EcosystemView ecosystemView = new EcosystemView(ecosystemModel);

		EcologicModel ecologicModel = new EcologicModel(appDatas);
		EcologicView ecologicView = new EcologicView(ecologicModel);

		new MainView(ecosystemView, ecologicView, claimView);

		ClaimController claimController =new ClaimController(claimView,dialogsView,claimModel,appDatas);
		EcosystemController ecosystemController = new EcosystemController(ecosystemView,dialogsView,ecosystemModel);
		EcologicController ecologicController = new EcologicController(ecologicView, dialogsView, ecologicModel);

		// Permet à ecosystemController et ecologicController d'être informés par claimController qu'il doivent mettre leur vue à jour
		ecosystemController.setClaimController(claimController);
		ecologicController.setClaimController(claimController);
	}
}
