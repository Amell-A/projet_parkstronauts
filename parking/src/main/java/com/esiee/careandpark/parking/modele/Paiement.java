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

    public String ia(String imageName) throws Exception {
        // Create an instance of AsposeOcr class to apply OCR on an image
        AsposeOCR textExtractFromImage = new AsposeOCR();

        // Read image usin g RecognizePage method for text extraction
        String extractedText = textExtractFromImage.RecognizePage(imageName);
    
        // Récupére la liste correspondante à la clé
        List<LocalDateTime> heureList = plaqueTexteHeureMap.get(extractedText);

        // Ajoutez l'heure actuelle à la liste ou créez une nouvelle liste si la clé n'existe pas encore
        if (heureList == null) {
            heureList = new ArrayList<>();
            plaqueTexteHeureMap.put(extractedText, heureList);
        }

        // Ajoute l'heure actuelle à la liste seulement si la liste a moins de deux éléments
        if (heureList.size() < 2) {
            heureList.add(LocalDateTime.now());
        } else {
            // Si la liste a déjà deux éléments on crée une nouvelle liste (et rempalce l'ancienne)
            heureList = new ArrayList<>();
            heureList.add(LocalDateTime.now());
            plaqueTexteHeureMap.put(extractedText, heureList);
        }

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
