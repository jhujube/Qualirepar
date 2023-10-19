package com.hallouin.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.hallouin.view.claimCreation.ClaimView;
import com.hallouin.view.ecologic.EcologicView;
import com.hallouin.view.ecosystem.EcosystemView;


@SuppressWarnings("serial")
public class MainView extends JFrame{
	private EcosystemView tabbedPane_ecosystem;
	private EcologicView tabbedPane_ecologic;
	private ClaimView tabbedPane_createClaim;
	private JPanel ecosystem;
	private JPanel createClaim;
	private JPanel ecologic;

	public MainView(EcosystemView ecosystemView, EcologicView ecologicView, ClaimView claimView) {
		super();

		tabbedPane_createClaim = claimView;
		tabbedPane_ecosystem = ecosystemView;
		tabbedPane_ecologic = ecologicView;
		//new ConsultClaimView(ecosystemModel);

		setMainView();
	}

	private void setMainView(){
		JFrame frame = new JFrame("Qualirépar");
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    // Maximiser la fenêtre pour qu'elle prenne tout l'écran au démarrage
	    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	    Dimension minimizedSize = new Dimension(1000,600);
	    frame.setMinimumSize(minimizedSize);

	    JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);


		createClaim = tabbedPane_createClaim.setClaimTabbedPane();
		tabbedPane.addTab("Demande",null,createClaim,null);

		ecosystem = tabbedPane_ecosystem.setEcsTabbedPane();
		tabbedPane.addTab("Ecosystem", null, ecosystem, null);

		ecologic = tabbedPane_ecologic.setEclTabbedPane();
		tabbedPane.addTab("Ecologic", null, ecologic, null);

        frame.add(tabbedPane);
        frame.setVisible(true);
	}

}
