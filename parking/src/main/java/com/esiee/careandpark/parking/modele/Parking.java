package com.esiee.careandpark.parking.modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.Duration;

import com.esiee.careandpark.parking.modele.exceptions.PlaceNotFoundException;
import com.esiee.careandpark.parking.modele.reference.EtatPlace;
import com.esiee.careandpark.parking.modele.reference.TypePlace;

public class Parking {
	
	private String nom;
	private List<Place> places;
	private String adresse;
	
	

	/**
	 * Initie un parking avec le nombres de place par type d�finis par les param�tres.
	 * Toutes les places se voient affect�e un num�ro unique et tous les num�ros se suivent	 *  
	 * @param nbPlaceNominale
	 * @param nbPlaceHandicape
	 * @param nbPlacebus
	 * @param nbPlace2roues
	 */
	public Parking(int nbPlaceNominale,int nbPlaceHandicape,int nbPlacebus,int nbPlace2roues, String adresse) {
		if (nbPlaceNominale<0) {
			throw new InstantiationError("interdit de mettre des nombres negatifs");
		}
		if (adresse==null) {
			throw new InstantiationError("interdit adresse null");
		}
		this.places = new ArrayList<Place>();
		
		
		List<Place> placesNominale = createListePlaceForType(nbPlaceNominale, TypePlace.NOMINALE, 1);
		places.addAll(placesNominale);
		
		List<Place> placesHandicape = createListePlaceForType(nbPlaceHandicape, TypePlace.HANDICAPE,places.size()+1);
		places.addAll(placesHandicape);
		
		List<Place> placesBus = createListePlaceForType(nbPlacebus, TypePlace.BUS, places.size()+1);
		places.addAll(placesBus);
		
		List<Place> places2roues = createListePlaceForType(nbPlace2roues, TypePlace.DEUX_ROUES, places.size()+1);
		places.addAll(places2roues);
	}
	
	private List<Place> createListePlaceForType(int nombre,TypePlace typePlace,int numeroDepart){
		if (nombre<0) {
			throw new InstantiationError("le nombre de place pour le type"+typePlace+" doit etre >= 0");
		}
		List<Place> places = new ArrayList<Place>();
		for (int i = 0; i<nombre; i++) {
			Place place = new Place(typePlace, i+numeroDepart);
			places.add(place);
		}
		
		return places;
		
	}
	
	/**
	 * renvoie toutes les places libre qui correspondent au type de place recherch�
	 * @param type
	 * @return
	 */
	public List<Place> searchPlaceLibre(TypePlace type){
		// Création d'une liste pour stocker toutes lees places libres
		List<Place> placesDispo =new ArrayList<>();
		// Parcours touetes les places 
		for (int i = 0; i < places.size(); i++){
			Place place= places.get(i);
			// Si la place est libre et correspond au type de vehicule 
			if (place.getType()==type && place.getEtat() ==EtatPlace.Libre) {
				// Ajout de la place à la liste placesDispo
				placesDispo.add(place );
			}
		}
		// Retourne la liste contenant toutes les places libre qui correspondent au type de place recherch�
		return placesDispo;
	}
	
	/**
	 * le statut de la place de numéro numero passe à occupe
	 * @param numero
	 * @throws PlaceNotFoundException si la place de numéro numero n'existe pas
	 */
	public void occuperPlace(int numero) throws PlaceNotFoundException{
		// Parcours touetes les places 
        for (int i = 0; i < places.size(); i++){
			Place place= places.get(i);
			// Si le numéro de place est le même que celui passé en parametre 
            if (place.getNumero()==numero) {
				// Modifie l'état de la place à occupé
                place.setEtat(EtatPlace.Occupe);
                return;
            }
        }
		//Renvoie une exception si le numéro n'existe pas 
        throw new PlaceNotFoundException(numero);
    }  


	/**
	 * le statut de la place de numéro numero passe à occupe
	 * @param numero
	 */
	public void libererPlace(int numero) throws PlaceNotFoundException{
        // Parcours touetes les places
		for (int i = 0; i < places.size(); i++){
			Place place= places.get(i);
		// Si le numéro de place est le même que celui passé en parametre
            if (place.getNumero() ==numero) {
                // Modifie l'état de la place à libre
                place.setEtat(EtatPlace.Libre);
                return ;
            }
        }
        //Renvoie une exception si le numéro n'existe pas
        throw new PlaceNotFoundException(numero) ;
    }


	
	public String getAdresse() {
		return adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	protected List<Place> getPlaces() {
		return places;
		
	}
	
	
    private Map<String, LocalDateTime> dictionnairestationner = new HashMap<>();
    private double tauxHoraire = 5.5; // Taux horaire en euros par heure
    
    // Constructeur pour ajouter des plaques 
    public void Plaques() {
    	dictionnairestationner.put("ABC123", LocalDateTime.of(2023, 1, 1, 12, 0)); // Exemple: plaque "ABC123" avec heure d'entrée à midi le 1er janvier 2023
    	dictionnairestationner.put("XYZ789", LocalDateTime.of(2023, 1, 1, 14, 30)); // Exemple: plaque "XYZ789" avec heure d'entrée à 14h30 le 1er janvier 2023
    }

    public void prixentrersortie(String numeroPlaque) {
        if (!dictionnairestationner.containsKey(numeroPlaque)) {
        	dictionnairestationner.put(numeroPlaque, LocalDateTime.now());
            System.out.println("Nouvelle plaque d'immatriculation ajoutée au système.");
        } else {
            LocalDateTime heureEntree = dictionnairestationner.get(numeroPlaque);
            LocalDateTime heureSortie = LocalDateTime.now();
            Duration dureeStationnement = Duration.between(heureEntree, heureSortie);
            long minutesStationnement = dureeStationnement.toMinutes();
            double prix = minutesStationnement * tauxHoraire / 60.0;
            System.out.println("Le prix de stationnement pour la plaque "+numeroPlaque+" est de : "+prix+"€");
        }
    }
    

}