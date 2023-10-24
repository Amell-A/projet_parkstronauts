package com.esiee.careandpark.parking.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.esiee.careandpark.parking.modele.exceptions.PlaceNotFoundException;
import com.esiee.careandpark.parking.modele.reference.EtatPlace;
import com.esiee.careandpark.parking.modele.reference.TypePlace;

class ParkingTest {
	
	
	@Test
	void testCreationParkingMalInitie() {
		
		 Assertions.assertThrows(InstantiationError.class, () -> {
			  new Parking(2, -1, 0, 0, 0,"");
		});
	}
	
	@Test
	void testCreationParkingadresseNull() {
		
		 Assertions.assertThrows(InstantiationError.class, () -> {
			  new Parking(2, 0, 0, 0, 0,null);
		});
	}
	
	@Test
	void testCreationParkingSansPlace_returnParkingWithListePlaceVide() {
		Parking parking = new Parking(2, 0, 0, 0, 0,"");
		
		List<List<Place>> etages = parking.getPlaces();
		assertTrue(etages.isEmpty(), "il ne doit pas y avoir de place");

	}
	
	@Test
	void testCreationParkingWithOnlyNormalPlace() {
	    Parking parking = new Parking(2, 2, 0, 0, 0, "");

	    List<List<Place>> etages = parking.getPlaces();
	    assertEquals(2, etages.size(), "Il doit y avoir 2 étages");

	    for (List<Place> etage : etages) {
	        assertEquals(2, etage.size(), "Il doit y avoir 2 places par étage");

	        for (Place place : etage) {
	            assertEquals(TypePlace.NOMINALE, place.getType(), "La place doit être normale");
	        }
	    }
	}
	

	@Test
	void testCreationParkingWithOnlyHandicapePlace() {
	    Parking parking = new Parking(2, 0, 2, 0, 0, "");

	    List<List<Place>> etages = parking.getPlaces();
	    assertEquals(2, etages.size(), "Il doit y avoir 2 étages");

	    for (List<Place> etage : etages) {
	        assertEquals(2, etage.size(), "Il doit y avoir 2 places handicapées par étage");

	        for (Place place : etage) {
	            assertEquals(TypePlace.HANDICAPE, place.getType(), "La place doit être handicapée");
	        }
	    }
	}
	
	@Test
	void testCreationParkingWithOnlyBusPlace() {
	    Parking parking = new Parking(2, 0, 0, 2, 0, "");

	    List<List<Place>> etages = parking.getPlaces();
	    assertEquals(2, etages.size(), "Il doit y avoir 2 étages");

	    for (List<Place> etage : etages) {
	        assertEquals(2, etage.size(), "Il doit y avoir 2 places pour les bus par étage");

	        for (Place place : etage) {
	            assertEquals(TypePlace.BUS, place.getType(), "La place doit être pour les bus");
	        }
	    }
	}
	
	@Test
	void testCreationParkingWithOnly2roues() {
	    Parking parking = new Parking(2, 0, 0, 0, 2, "");

	    List<List<Place>> etages = parking.getPlaces();
	    assertEquals(2, etages.size(), "Il doit y avoir 2 étages");

	    for (List<Place> etage : etages) {
	        assertEquals(2, etage.size(), "Il doit y avoir 2 places pour les deux-roues par étage");

	        for (Place place : etage) {
	            assertEquals(TypePlace.DEUX_ROUES, place.getType(), "La place doit être pour les deux-roues");
	        }
	    }
	}

	@Test
	void testSearchPlaceLibre() {
		Parking parking = new Parking(1, 40, 15, 5, 20,"4 Av. des Abbesses, 77500 Chelles");
		
		List<Place> placesDispoNominale = parking.searchPlaceLibre(TypePlace.NOMINALE, 0);
		assertEquals(40,placesDispoNominale.size(), "il  doit y avoir 40 places libres");

		List<Place> placesDispoHandicape = parking.searchPlaceLibre(TypePlace.HANDICAPE, 0);
		assertEquals(15,placesDispoHandicape.size(), "il  doit y avoir 15 places libres");

		List<Place> placesDispoBus = parking.searchPlaceLibre(TypePlace.BUS, 0);
		assertEquals(5,placesDispoBus.size(), "il  doit y avoir 5 places libres");

		List<Place> placesDispo2roues = parking.searchPlaceLibre(TypePlace.DEUX_ROUES, 0);
		assertEquals(20,placesDispo2roues.size(), "il  doit y avoir 20 places libres");

	}

	@Test
	void testOccuperPlace() {
	    Parking parking = new Parking(2, 40, 15, 5, 20, "4 Av. des Abbesses, 77500 Chelles");

	    try {
	        parking.occuperPlace(10, 1);
	    } catch (PlaceNotFoundException e) {
	        Assertions.fail("Exception inattendue lors de l'appel à occuperPlace(): " + e.getMessage());
	    }

	    assertEquals(EtatPlace.Occupe, parking.getPlaces().get(1).get(9).getEtat(), "La place doit être occupée");
	    
	    // Vérification pour l'occuption d'une place qui n'existe pas
	    Assertions.assertThrows(PlaceNotFoundException.class, () -> {
	        parking.occuperPlace(300, 1);
	    });
	}
	
	@Test
	void testLibererPlace() {
	    Parking parking = new Parking(2, 40, 15, 5, 20, "4 Av. des Abbesses, 77500 Chelles");

	    try {
	        parking.occuperPlace(10, 1);
	    } catch (PlaceNotFoundException e) {
	        Assertions.fail("Exception inattendue lors de l'appel à libererPlace(): " + e.getMessage());
	    }

	    assertEquals(EtatPlace.Libre, parking.getPlaces().get(1).get(9).getEtat(), "La place doit être libre");
	    
	    // Vérification pour l'occuption d'une place qui n'existe pas
	    Assertions.assertThrows(PlaceNotFoundException.class, () -> {
	        parking.libererPlace(300, 1);
	    });
	}

}
