package com.hallouin.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.hallouin.model.bill.FileInformations;
import com.hallouin.model.ecosystem.api.pojo.FileInfos;

public class FilesFormPanel {

	JPanel panel;
	GridBagConstraints pos;

	private final double[] cell_weightx = {0.3, 0.5, 0.2}; // Définissez les proportions de largeur pour chaque cellule;
	private final String[] cell_name = {"Type","Fichier","Taille (Mo)"};


	public FilesFormPanel() {
		super();
	}

	public JPanel addFilesFormPanel() {
		List<FileInfos> filesList = null;

		panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2, true), "Fichiers Joints", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));

        pos = new GridBagConstraints();
        pos.fill = GridBagConstraints.HORIZONTAL;
        pos.insets = new Insets(0, 0, 0, 10);

        createPanel(filesList);

        return panel;
	}

	private void createPanel(List<FileInfos> FilesList) {
		panel.removeAll();

		int lineNumber = 0;
        lineNumber = showCreateFileLine(lineNumber);
        lineNumber = showFileLine(lineNumber, FilesList);

        int panelHeight = calculatePanelHeight(lineNumber);
        panel.setPreferredSize(new Dimension(400, panelHeight));
        panel.setMaximumSize(panel.getPreferredSize());
        panel.setMinimumSize(panel.getPreferredSize());

        panel.revalidate();
        panel.repaint();
	}
	private int calculatePanelHeight(int lineNumber) {
	    int lineHeight = 20; // Hauteur d'une ligne du panel
	    int padding = 20; // Espacement supplémentaire entre les lignes
	    int totalHeight = lineHeight + padding * lineNumber; // Calcul de la hauteur totale

	    return totalHeight;
	}

	private int showCreateFileLine(int lineNumber) {

		for (int i=0; i<cell_name.length; i++) {
        	pos.gridx = i;
        	pos.gridy = lineNumber;
        	pos.weightx = cell_weightx[i];
        	//
            panel.add(new JLabel(cell_name[i]),pos);
        }
		lineNumber++;

        return lineNumber;
	}

	private int showFileLine(int lineNumber, List<FileInfos> filesList) {

		if (filesList != null ) {

			if (filesList.isEmpty()){
				//ligne vide
				pos.gridx = 0;
	        	pos.gridy = lineNumber;
	        	pos.weightx = cell_weightx[0];
	        	JLabel label = new JLabel("  ");
	            panel.add(label, pos);
			} else {
		    	for (FileInfos file : filesList) {
		    		String fileType = getFileType(file.getFileType());
		    		pos.gridx = 0;
		        	pos.gridy = lineNumber;
		        	pos.weightx = cell_weightx[0];
		        	JLabel label = new JLabel(fileType);
		            panel.add(label, pos);

		            String[] filenameStrings = file.getFileName().split("/");
		            pos.gridx = 1;
		        	pos.gridy = lineNumber;
		        	pos.weightx = cell_weightx[1];
		        	JLabel label1 = new JLabel(filenameStrings[filenameStrings.length-1]);
		            panel.add(label1, pos);

		            pos.gridx = 2;
		        	pos.gridy = lineNumber;
		        	pos.weightx = cell_weightx[2];
		        	JLabel label2 = new JLabel(""+file.getFileSizeInMB());
		            panel.add(label2, pos);
		            lineNumber++;
		    	}
	            //lineNumber++;
	    	}
		}

    	return lineNumber;
	}

	private String getFileType(String type) {
		String fileType = "";

		switch (type) {
		case "invoice":
			fileType = "Facture";
			break;
		case "serial_tag":
			fileType = "N° de série";
			break;
		case "device_picture":
			fileType = "Photo du produit";
			break;
		case "certificate_client":
			fileType = "Attestation";
			break;
		default:
			fileType = "?";
			break;
		}

		return fileType;
	}

	public void updateFiles(List<FileInfos> filesList) {
    	createPanel(filesList);
    }
	public void eclUpdateFiles(List<FileInformations> files) {
		List<FileInfos> filesList = new ArrayList<>();

		for (FileInformations fileInfos : files) {
			FileInfos file_Infos = new FileInfos(fileInfos.getName(), fileInfos.getType(), fileInfos.getSize());
			filesList.add(file_Infos);
		}
    	createPanel(filesList);
    }
}
