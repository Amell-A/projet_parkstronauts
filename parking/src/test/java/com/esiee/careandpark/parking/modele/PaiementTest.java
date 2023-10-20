package com.esiee.careandpark.parking.modele;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaiementTest {

    @Test
    public void testTextExtractionAndTimeTracking() {
        // Spécifiez le nom de l'image que vous souhaitez traiter
        String imageName = "C:\\Users\\amaim\\OneDrive\\Documents\\ESIEE\\E5\\Capgemini\\projet_parkstronauts\\parking\\src\\test\\java\\com\\esiee\\careandpark\\parking\\modele\\plaque.png";

        // Créez une instance de la classe Payement
        Paiement paiement = new Paiement();

        try {
            // extraire le texte de l'image
            String extractedText = paiement.ia(imageName);
            assertEquals("ABCD 012\n", extractedText);

            // Vérifiez si la clé existe dans la map
            Map<String, List<LocalDateTime>> plaqueTexteHeureMap = paiement.getPlaqueTexteHeureMap();
            
            assertTrue(plaqueTexteHeureMap.containsKey(extractedText));

            // Récupérez la liste contenant les dates/heures correspondante à la clé
            List<LocalDateTime> heureList = plaqueTexteHeureMap.get(extractedText);

            // Vérifiez que la liste contient un élément
            assertTrue(heureList.size()== 1);

            // Vérifiez que le premier élément correspond à la date et l'heure d'entrée dans le parking
            LocalDateTime premiereHeure = heureList.get(0);

            // Affichez le texte extrait et la date et l'heure d'entrée 
            System.out.println("Texte extrait : " + extractedText);
            System.out.println("Premiere heure : " + premiereHeure);
            
            // Pause de 10 minutes avant le deuxième appel 
            //Thread.sleep(600000);
            //extractedText = paiement.ia(imageName);
            //System.out.println("Deuxième horraire ajoutée : " + heureList );
            // Récupérez la liste d'heures mise à jour
            //heureList = plaqueTexteHeureMap.get(extractedText);

            // Créez une deuxième heure simulée, 10 heures après la première 
            LocalDateTime deuxiemeHeureSimulee = premiereHeure.plus(1, ChronoUnit.HOURS);
            // Ajoutez la date et l'heure correspondant à la sortie du parking à la liste
            heureList.add(deuxiemeHeureSimulee);

            // Vérifiez que la liste contient maintenant deux éléments (heure initiale + deuxième heure)
            assertTrue(heureList.size()==2);

            // Vérifiez que le deuxième élément est une heure ultérieure à la première
            LocalDateTime deuxiemeHeure = heureList.get(1);
            assertTrue(deuxiemeHeure.isAfter(premiereHeure));

            // Affichez la deuxième heure dans la console
            System.out.println("Date et heure de sortie : " + deuxiemeHeure);
            System.out.println("Heures d'entree et de sortie : " + heureList);

            // Appelez la nouvelle fonction calculerPrix pour obtenir le prix
            double prix = paiement.calculerPrix(extractedText);

            // Affichez le prix dans la console
            System.out.println("Prix calculé : " + prix+" euros");

            // Vérifiez que le prix est correct (50 euros pour 10 heures)
            assertEquals(10, prix);

        } catch (Exception e) {
            // Gérez les exceptions ici
            e.printStackTrace();
            fail("Une exception s'est produite");
        }
    }
}
