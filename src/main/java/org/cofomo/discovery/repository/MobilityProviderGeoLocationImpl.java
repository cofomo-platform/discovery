package org.cofomo.discovery.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.cofomo.commons.domain.exploration.Location;
import org.cofomo.commons.domain.exploration.MobilityProviderEntity;
import org.cofomo.discovery.utils.GeoLocation;
import org.springframework.beans.factory.annotation.Autowired;

public class MobilityProviderGeoLocationImpl implements MobilityProviderGeoLocation {

	@Autowired
	private EntityManager em;

	static final double distance = 20;
	static final double earthRadius = 6371.01;

	@Override
	public List<MobilityProviderEntity> getByLocation(Location location) {		
		GeoLocation geoLocation = GeoLocation.fromRadians(location.latitude, location.longitude);
		GeoLocation[] boundingCoordinates = geoLocation.boundingCoordinates(distance, earthRadius);
		boolean meridian180WithinDistance = boundingCoordinates[0].getLongitudeInRadians() > boundingCoordinates[1].getLongitudeInRadians();
		String sqlString = "SELECT * FROM Places WHERE (Lat >= ? AND Lat <= ?) AND (Lon >= ? " +
				(meridian180WithinDistance ? "OR" : "AND") + " Lon <= ?) AND " +
				"acos(sin(?) * sin(Lat) + cos(?) * cos(Lat) * cos(Lon - ?)) <= ?";
		
		return em.createNativeQuery(sqlString).getResultList();
	}

}
