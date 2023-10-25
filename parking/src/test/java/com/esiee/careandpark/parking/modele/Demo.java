package com.esiee.careandpark.parking.modele;

import java.time.LocalDateTime;
import java.util.List;
import com.esiee.careandpark.parking.modele.reference.TypePlace;
import java.util.Map;

public class Demo {

    public static void main(String[] args) throws Exception {
    	
    	Parking parking = new Parking(2, 40, 15, 5, 20,"4 Av. des Abbesses, 77500 Chelles");
    	
    	List<Place> placesDispoNominale = parking.searchPlaceLibre(TypePlace.NOMINALE, 0);
    	List<Place> placesDispoHandicape = parking.searchPlaceLibre(TypePlace.HANDICAPE, 0);
    	List<Place> placesDispoBus = parking.searchPlaceLibre(TypePlace.BUS, 0);
    	List<Place> placesDispo2roues = parking.searchPlaceLibre(TypePlace.DEUX_ROUES, 0);
    	
    	System.out.println("Nombre d'étages : " + parking.getPlaces().size());
    	System.out.println("Places nominales : " + placesDispoNominale.size());
    	System.out.println("Places handicapées : " + placesDispoHandicape.size());
    	System.out.println("Places de bus : " + placesDispoBus.size());
    	System.out.println("Places 2 roues : " + placesDispo2roues.size());
    	System.out.println("Nombre totale de place : " + parking.getNombreTotalPlaces());
    	
    	
        //License.setLicense("Aspose.OCR.lic");

        // Spécifiez le chemin de l'image.
        String imagePath = "C:\\Users\\evely\\Documents\\E5\\projet_parkstronauts\\parking\\src\\test\\java\\com\\esiee\\careandpark\\parking\\modele\\plaque.png"; // Remplacez par le chemin de votre image.

        /*
        // Demander à l'utilisateur de saisir le temps de stationnement.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Temps de stationnement (heures) : ");
        int parkingTime = scanner.nextInt();

        // Demander à l'utilisateur de choisir le type de place.
        System.out.print("Type de place (NOMINALE, HANDICAPE, DEUX_ROUES, BUS) : ");
        String parkingType = scanner.next();
        */
        
        // Crée une instance de la classe Payement
        Paiement paiement = new Paiement();
        
     // Détection du numéro de plaque d'immatriculation à partir de l'image.
        String extractedText = paiement.ia(imagePath);
        
        System.out.println("Numéro de plaque : " + extractedText);
        
        Map<String, List<LocalDateTime>> plaqueTexteHeureMap = paiement.getPlaqueTexteHeureMap();
        
        List<LocalDateTime> heureList = plaqueTexteHeureMap.get(extractedText);
        
        LocalDateTime premiereHeure = heureList.get(0);
        
        System.out.println("Heure d'entree dans le parking : " + premiereHeure);
        
        parking.occuperPlace(10, 1);
        
        System.out.println("Nombre de place libre : " + parking.getNombrePlacesLibres());
        
        extractedText=paiement.ia(imagePath);
        
        LocalDateTime deuxiemeHeure = heureList.get(1);
        
        System.out.println("Heure de sortie du parking : " + deuxiemeHeure );
        
     // Calcul du prix en utilisant la classe Paiement.
        double prix = paiement.calculerPrix(extractedText,TypePlace.BUS);
        
        System.out.println("Prix à payer : " + prix + " euros");
        
        parking.libererPlace(10, 1);
        
        System.out.println("Nombre de place libre : " + parking.getNombrePlacesLibres());

        //System.out.println("Temps de stationnement (heures): " + parkingTime);
        //System.out.println("Type de place: " + parkingType);
    }
} 