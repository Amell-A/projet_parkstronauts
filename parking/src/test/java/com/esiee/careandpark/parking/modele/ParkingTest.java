package com.esiee.careandpark.parking.modele;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
			  new Parking(-1, 0, 0, 0,"");
		});
	}
	
	@Test
	void testCreationParkingadresseNull() {
		
		 Assertions.assertThrows(InstantiationError.class, () -> {
			  new Parking(0, 0, 0, 0,null);
		});
	}
	
	@Test
	void testCreationParkingSansPlace_returnParkingWithListePlaceVide() {
		Parking parking = new Parking(0, 0, 0, 0,"");
		
		List<Place> places = parking.getPlaces();
		assertTrue(places.isEmpty(), "il ne doit pas y avoir de place");

	}
	
	@Test
	void testCreationParkingWithOnlyNormalPlace() {
		Parking parking = new Parking(2, 0, 0, 0,"");
		
		List<Place> places = parking.getPlaces();
		assertEquals(2,places.size(), "il  doit y avoir 2 place");
		
		for(Place place : places) {
			assertEquals(TypePlace.NOMINALE,place.getType(), "La place doit être normale");
		}

	}
	

	@Test
	void testCreationParkingWithOnlyHandicapePlace() {
		Parking parking = new Parking(0, 2, 0, 0,"");
		
		List<Place> places = parking.getPlaces();
		assertEquals(2,places.size(), "il  doit y avoir 2 place");
		
		for(Place place : places) {
			assertEquals(TypePlace.HANDICAPE,place.getType(), "La place doit être handicape");
		}

	}
	
	@Test
	void testCreationParkingWithOnlyBusPlace() {
		Parking parking = new Parking(0, 0, 2, 0,"");
		
		List<Place> places = parking.getPlaces();
		assertEquals(2,places.size(), "il  doit y avoir 2 place");
		
		for(Place place : places) {
			assertEquals(TypePlace.BUS,place.getType(), "La place doit être BUS");
		}

	}
	
	@Test
	void testCreationParkingWithOnly2roues() {
		Parking parking = new Parking(0, 0, 0, 2,"");
		
		List<Place> places = parking.getPlaces();
		assertEquals(2,places.size(), "il  doit y avoir 2 place");
		
		for(Place place : places) {
			assertEquals(TypePlace.DEUX_ROUES,place.getType(), "La place doit être 2 ROUES");
		}

	}

	@Test
	void testSearchPlaceLibre() {
		Parking parking = new Parking(40, 15, 5, 20,"4 Av. des Abbesses, 77500 Chelles");
		
		List<Place> placesDispoNominale = parking.searchPlaceLibre(TypePlace.NOMINALE);
		assertEquals(40,placesDispoNominale.size(), "il  doit y avoir 40 places libres");

		List<Place> placesDispoHandicape = parking.searchPlaceLibre(TypePlace.HANDICAPE);
		assertEquals(15,placesDispoHandicape.size(), "il  doit y avoir 15 places libres");

		List<Place> placesDispoBus = parking.searchPlaceLibre(TypePlace.BUS);
		assertEquals(5,placesDispoBus.size(), "il  doit y avoir 5 places libres");

		List<Place> placesDispo2roues = parking.searchPlaceLibre(TypePlace.DEUX_ROUES);
		assertEquals(20,placesDispo2roues.size(), "il  doit y avoir 20 places libres");

	}

	@Test
	void testOccuperPlace() {
		Parking parking = new Parking(40, 15, 5, 20,"4 Av. des Abbesses, 77500 Chelles");
		
		try{
			parking.occuperPlace(10);
		}catch(PlaceNotFoundException e){
			Assertions.fail("Exception innatendu quand on appelle occuperPlace()"+e.getMessage());
		}

		assertEquals(EtatPlace.Occupe,parking.getPlaces().get(9).getEtat(), "la place doit etre occupée");
		
		Assertions.assertThrows(PlaceNotFoundException.class, () -> {
			  parking.occuperPlace(300);;
		});
		
	}



}
