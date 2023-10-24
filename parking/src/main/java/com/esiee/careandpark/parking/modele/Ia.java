package com.esiee.careandpark.parking.modele;

import com.aspose.ocr.AsposeOCR;
import com.aspose.ocr.License;

public class Ia {
	
	public String extractTextFromImage(String imagePath) throws Exception {

		License.setLicense("Aspose.OCR.lic");

		// Create an instance of AsposeOcr class to apply OCR on an image
		AsposeOCR textExtractor = new AsposeOCR();

		// Read image using RecognizePage method for text extraction
		String extractedText = textExtractor.RecognizePage(imagePath);

		return extractedText;
	}

	public static void main(String[] args) {
		// Chemin de l'image que vous souhaitez traiter
		String imagePath = "C:\\Users\\evely\\Documents\\E5\\projet_parkstronauts\\parking\\src\\main\\java\\com\\esiee\\careandpark\\parking\\modele\\carplate2.jpg";

		// Créez une instance de la classe Ia
		Ia ia = new Ia();

		try {
			// Appelez la fonction extractTextFromImage pour extraire le texte de l'image
			String extractedText = ia.extractTextFromImage(imagePath);

			// Affichez le texte extrait
			System.out.println("Texte extrait de l'image :");
			System.out.println(extractedText);
		} catch (Exception e) {
			// Gérez les exceptions appropriées ici
			e.printStackTrace();
		}
	}
}