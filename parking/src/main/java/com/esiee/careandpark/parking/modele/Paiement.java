package com.esiee.careandpark.parking.modele;

import com.aspose.ocr.AsposeOCR;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paiement{

    private Map<String, List<LocalDateTime>> plaqueTexteHeureMap = new HashMap<>();

    public String ia(String imageName) throws Exception{

        // Create an instance of AsposeOcr class to apply OCR on an image
        AsposeOCR TextExtractFromImage = new AsposeOCR();

        // Read image using RecognizePage method for text extraction
        String extractedText = TextExtractFromImage.RecognizePage("C:\\Users\\amaim\\OneDrive\\Documents\\ESIEE\\E5\\Capgemini\\projet_parkstronauts\\parking\\src\\test\\java\\com\\esiee\\careandpark\\parking\\modele\\plaque.png");

        // Récupérez la liste correspondante à la clé ou créez une nouvelle liste si la clé n'existe pas encore
        List<LocalDateTime> heureList = plaqueTexteHeureMap.computeIfAbsent(extractedText, key -> new ArrayList<>());

        // Ajoutez l'heure actuelle à la liste
        heureList.add(LocalDateTime.now());

        return extractedText;
    }

    public Map<String, List<LocalDateTime>> getPlaqueTexteHeureMap() {
        return plaqueTexteHeureMap;
    }

    public double calculerPrix(String plaqueImmatriculation) {
        // Récupérez la liste d'heures correspondante à la clé (plaque d'immatriculation)
        List<LocalDateTime> heureList = plaqueTexteHeureMap.get(plaqueImmatriculation);

        // Récupérez la première et la deuxième heure
        LocalDateTime premiereHeure = heureList.get(0);
        LocalDateTime deuxiemeHeure =heureList.get(1);

        // Calculez la durée en heures entre l'huere d'entrée et de sortie
        long dureeEnHeures = ChronoUnit.HOURS.between(premiereHeure, deuxiemeHeure)+1;

        // Calculez le prix (5 euros par heure)
        double prix = dureeEnHeures * 5;

        return prix;
    }
}
