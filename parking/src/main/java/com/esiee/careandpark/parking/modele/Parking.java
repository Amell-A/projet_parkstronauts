package com.esiee.careandpark.parking.modele;

import java.util.ArrayList;
import java.util.List;

import com.esiee.careandpark.parking.modele.exceptions.PlaceNotFoundException;
import com.esiee.careandpark.parking.modele.reference.EtatPlace;
import com.esiee.careandpark.parking.modele.reference.TypePlace;

public class Parking {
	
	private String nom;
	//private List<Place> places;
	private List<List<Place>> etages;
	private String adresse;
	
	

	/**
	 * Initie un parking avec le nombres de place par type d�finis par les param�tres.
	 * Toutes les places se voient affect�e un num�ro unique et tous les num�ros se suivent	 *  
	 * @param nbPlaceNominale
	 * @param nbPlaceHandicape
	 * @param nbPlacebus
	 * @param nbPlace2roues
	 */
	public Parking(int nbEtages, int nbPlaceNominale,int nbPlaceHandicape,int nbPlacebus,int nbPlace2roues, String adresse) {
		if (nbPlaceNominale<0) {
			throw new InstantiationError("interdit de mettre des nombres negatifs");
		}
		if (adresse==null) {
			throw new InstantiationError("interdit adresse null");
		}
		
		this.etages = new ArrayList<>();
		
		for (int i = 0; i < nbEtages; i++) {
			List<Place> places = new ArrayList<>();
			
			List<Place> placesNominale = createListePlaceForType(nbPlaceNominale, TypePlace.NOMINALE, 1);
			places.addAll(placesNominale);
			
			List<Place> placesHandicape = createListePlaceForType(nbPlaceHandicape, TypePlace.HANDICAPE,places.size()+1);
			places.addAll(placesHandicape);
			
			List<Place> placesBus = createListePlaceForType(nbPlacebus, TypePlace.BUS, places.size()+1);
			places.addAll(placesBus);
			
			List<Place> places2roues = createListePlaceForType(nbPlace2roues, TypePlace.DEUX_ROUES, places.size()+1);
			places.addAll(places2roues);
			
			etages.add(places);
		}
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

    public List<Place> searchPlaceLibre(TypePlace type, int etage) {
        if (etage >= 0 && etage < etages.size()) {
            List<Place> placesDispo = new ArrayList<>();
            List<Place> etageCourant = etages.get(etage);
            for (Place place : etageCourant) {
                if (place.getType() == type && place.getEtat() == EtatPlace.Libre) {
                    placesDispo.add(place);
                }
            }
            return placesDispo;
        } else {
            throw new IllegalArgumentException("L'étage spécifié n'existe pas.");
        }
    }
	
	/**
	 * le statut de la place de numéro numero passe à occupe
	 * @param numero
	 * @throws PlaceNotFoundException si la place de numéro numero n'existe pas
	 */
    public void occuperPlace(int numero, int etage) throws PlaceNotFoundException {
        if (etage >= 0 && etage < etages.size()) {
            List<Place> etageCourant = etages.get(etage);
            for (Place place : etageCourant) {
                if (place.getNumero() == numero) {
                    place.setEtat(EtatPlace.Occupe);
                    return;
                }
            }
            throw new PlaceNotFoundException(numero);
        } else {
            throw new IllegalArgumentException("L'étage spécifié n'existe pas.");
        }
    }


	/**
	 * le statut de la place de numéro numero passe à occupe
	 * @param numero
	 */
	public void libererPlace(int numero, int etage) throws PlaceNotFoundException{
        if (etage >= 0 && etage < etages.size()) {
            List<Place> etageCourant = etages.get(etage);
            for (Place place : etageCourant) {
                if (place.getNumero() == numero) {
                    place.setEtat(EtatPlace.Libre);
                    return;
                }
            }
            throw new PlaceNotFoundException(numero);
        } else {
            throw new IllegalArgumentException("L'étage spécifié n'existe pas.");
        }
    }
	
    public int getNombreTotalPlaces() {
        int nombreTotalPlaces = 0;
        for (List<Place> etage : etages) {
            nombreTotalPlaces += etage.size();
        }
        return nombreTotalPlaces;
    }
	
    public int getNombrePlacesLibresParType(TypePlace type) {
        int placesLibresType = 0;
        for (List<Place> etage : etages) {
            for (Place place : etage) {
                if (place.getType() == type && place.getEtat() == EtatPlace.Libre) {
                    placesLibresType++;
                }
            }
        }
        return placesLibresType;
    }
    
    public int getNombrePlacesLibres() {
        int nombrePlacesLibres = 0;
        for (List<Place> etage : etages) {
            for (Place place : etage) {
                if (place.getEtat() == EtatPlace.Libre) {
                    nombrePlacesLibres++;
                }
            }
        }
        return nombrePlacesLibres;
    }

    public int getNombrePlacesOccupees() {
        int nombrePlacesOccupees = 0;
        for (List<Place> etage : etages) {
            for (Place place : etage) {
                if (place.getEtat() == EtatPlace.Occupe) {
                    nombrePlacesOccupees++;
                }
            }
        }
        return nombrePlacesOccupees;
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
	
	protected List<List<Place>> getPlaces() {
		return etages;
		
	}
	

    

}
