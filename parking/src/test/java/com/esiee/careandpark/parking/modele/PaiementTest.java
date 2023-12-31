package com.esiee.careandpark.parking.modele;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.Test;
import com.esiee.careandpark.parking.modele.reference.TypePlace;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class PaiementTest {

    @Test
    public void iaTest() {
        // Plaque d'immatriculation
        String imageName = "C:\\Users\\evely\\Documents\\E5\\projet_parkstronauts\\parking\\src\\test\\java\\com\\esiee\\careandpark\\parking\\modele\\plaque.png";

        // Crée une instance de la classe Payement
        Paiement paiement = new Paiement();

        try {
            // extraire le texte de l'image
            String extractedText = paiement.ia(imageName);
            assertEquals("ABCD 012\n", extractedText);

            Map<String, List<LocalDateTime>> plaqueTexteHeureMap = paiement.getPlaqueTexteHeureMap();
            // Affichele texte extrait et la date et l'heure d'entrée 
            System.out.println("Heure d'entree dans le parking : " + plaqueTexteHeureMap);
            // Vérifie si la clé existe dans la map
            assertTrue(plaqueTexteHeureMap.containsKey(extractedText));

            // Récupére la liste contenant les dates/heures correspondante à la clé
            List<LocalDateTime> heureList = plaqueTexteHeureMap.get(extractedText);
            // Vérifie que la liste contient un élément
            assertTrue(heureList.size() == 1);

            LocalDateTime premiereHeure = heureList.get(0);
            
            // Heure de sortie (2 élements dans la liste)________________________________________________________________________________

            // Pause de 10 minutes avant le deuxième appel 
            //Thread.sleep(600000);
            extractedText=paiement.ia(imageName);
            // Récupérez la liste d'heures mise à jour
            heureList =plaqueTexteHeureMap.get(extractedText);

            // Créez une deuxième heure simulée, 10 heures après la première 
            //LocalDateTime deuxiemeHeureSimulee = premiereHeure.plus(1, ChronoUnit.HOURS);
            // Ajoutez la date et l'heure correspondant à la sortie du parking à la liste
            //heureList.add(deuxiemeHeureSimulee);

            // Vérifiez que la liste contient maintenant deux éléments (heure initiale + deuxième heure)
            assertTrue(heureList.size()==2);
            // Vérifiez que le deuxième élément est une heure ultérieure à la première
            LocalDateTime deuxiemeHeure = heureList.get(1);
            assertTrue(deuxiemeHeure.isAfter(premiereHeure));
            // Affichez la deuxième heure dans la console
            System.out.println("Date/Heure d'entree et de sortie du parking : " + heureList );

            // Test dans le cas où une fois sortie la voiture re-rentre________________________________________________________________
            extractedText = paiement.ia(imageName);
            heureList = plaqueTexteHeureMap.get(extractedText);
            assertTrue(heureList.size()==1);
            System.out.println("Test voiture rerentre - contenu de la liste : " + heureList );

        } catch (Exception e) {
            // Gérez les exceptions ici
            e.printStackTrace();
            fail("Une exception s'est produite");
        }
    }

    @Test
    public void calculerPrixTest() {
        // Plaque d'immatriculation
        String imageName = "C:\\Users\\amaim\\OneDrive\\Documents\\ESIEE\\E5\\Capgemini\\projet_parkstronauts\\parking\\src\\test\\java\\com\\esiee\\careandpark\\parking\\modele\\plaque.png";

        // Crée une instance de la classe Payement
        Paiement paiement = new Paiement();

        try {
            // extraire le texte de l'image
            String extractedText = paiement.ia(imageName);
            // Vérifie si la clé existe dans la map
            Map<String, List<LocalDateTime>> plaqueTexteHeureMap = paiement.getPlaqueTexteHeureMap();
            // Récupére la liste contenant les dates/heures correspondante à la clé
            List<LocalDateTime> heureList = plaqueTexteHeureMap.get(extractedText);
            LocalDateTime premiereHeure = heureList.get(0);
            // Affichele texte extrait et la date et l'heure d'entrée 
            System.out.println("Heure d'entree dans le parking : " + premiereHeure);
            
            // Heure de sortie (2 élements dans la liste)________________________________________________________________________________
            // Créez une deuxième heure simulée, 5 heures après la première 
            LocalDateTime deuxiemeHeureSimulee = premiereHeure.plus(5, ChronoUnit.HOURS);
            // Ajoutez la date et l'heure correspondant à la sortie du parking à la liste
            heureList.add(deuxiemeHeureSimulee);

            // Calcul du prix______________________________________________________________________________________

            // CALCUL DU PRIX POUR UN BUS
            // Appelez la nouvelle fonction calculerPrix pour obtenir le prix
            double prixBus = paiement.calculerPrix(extractedText,TypePlace.BUS);
            // Affichez le prix dans la console
            System.out.println("Prix calcule : " + prixBus+" euros");
            assertEquals(8.91*6, prixBus);

            // CALCUL DU PRIX POUR UNE PLACE HANDICAPE
            double prixHandicape = paiement.calculerPrix(extractedText,TypePlace.HANDICAPE);
            // Affichez le prix dans la console
            System.out.println("Prix calcule : " + prixHandicape+" euros");
            assertEquals(2.97*6, prixHandicape);

            // CALCUL DU PRIX POUR UNE PLACE Deux roues
            double prixDeuxRoues = paiement.calculerPrix(extractedText,TypePlace.DEUX_ROUES);
            // Affichez le prix dans la console
            System.out.println("Prix calcule : " + prixDeuxRoues+" euros");
            assertEquals(1.485*6, prixDeuxRoues);

            // CALCUL DU PRIX POUR UNE PLACE NORMALE
            double prixNominale = paiement.calculerPrix(extractedText,TypePlace.NOMINALE);
            // Affichez le prix dans la console
            System.out.println("Prix calcule : " + prixNominale+" euros");
            assertEquals(4.455*6, prixNominale);

        } catch (Exception e) {
            // Gérez les exceptions ici
            e.printStackTrace();
            fail("Une exception s'est produite");
        }
    }
}
