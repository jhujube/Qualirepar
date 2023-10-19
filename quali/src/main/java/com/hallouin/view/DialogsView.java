package com.hallouin.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class DialogsView extends JFrame{

	String filePath;

	public File showFileChooser(String filesPath, String filesType) {

		File fichier = null;
        // Créer un objet JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        File defaultDir = new File(filesPath);
        fileChooser.setCurrentDirectory(defaultDir);


        if (filesType.contentEquals("*"))
        	fileChooser.setAcceptAllFileFilterUsed(true);
        else {
	        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers "+filesType, filesType);
	        fileChooser.setFileFilter(filter);
        }
        // Afficher la boîte de dialogue pour sélectionner un fichier
        int resultat = fileChooser.showOpenDialog(null);

        if (resultat == JFileChooser.APPROVE_OPTION) {
	            // Récupérer le fichier sélectionné
	            fichier = fileChooser.getSelectedFile();
	            this.filePath = fichier.getAbsolutePath();
	            System.out.println("Le fichier sélectionné est : " + this.filePath);
            } else {
	            System.out.println("Aucun fichier sélectionné.");
        }
        return fichier;
	}

	public int choiceContinueCancel(String choiceText) {
		Object[] options = {"Continuer", "Abandonner"};
        int selection = JOptionPane.showOptionDialog(null, choiceText, "Attention!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (selection == JOptionPane.CLOSED_OPTION || selection == 1) {
            System.out.println("Boîte de dialogue fermée sans choix.");
        }
        return selection;
	}

	public int optionDialogue(Object[] options, String message, String title) {
        int selection = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        return selection;
	}

	public void deleteFile() {

        File fichier = new File(this.filePath);

        int choix = JOptionPane.showConfirmDialog(null, "Souhaitez-vous supprimer le fichier ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (choix == JOptionPane.YES_OPTION) {
            if (fichier.delete()) {
                JOptionPane.showMessageDialog(null, "Le fichier a été supprimé avec succès.");
            } else {
                JOptionPane.showMessageDialog(null, "Impossible de supprimer le fichier.");
            }
        }
	}

	public void simpleMessage(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
	}
}