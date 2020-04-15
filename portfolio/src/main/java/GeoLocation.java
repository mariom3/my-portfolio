/*
	Mario Morales
	3 Dec. 2018
	Description: Defines a geolocation to be extended by CTAStation in order to 
		define its location.
*/

package com.google.sps.servlets;

import java.util.Arrays;
import java.util.List;

public class GeoLocation {

	private double latitude;
	private double longitude;

	// Default constructor sets location in Chicago.
	public GeoLocation() {
		latitude = 41.8781;
		longitude = -87.6298;
	}

	public GeoLocation(double latitude, double longitude) {
		setLatitude(latitude);
		setLongitude(longitude);
	}

	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}

	public void setLatitude(double latitude) {
		if (validateLatitude(latitude))
			this.latitude = latitude;
		else System.out.println("The sepecified value is not a valid latitude!");
	}
	public void setLongitude(double longitude) {
		if(validateLongitude(longitude))
			this.longitude = longitude;
		else System.out.println("The sepecified value is not a valid longitude!");
	}

	public String toString() {
		return "(" + latitude + ", " + longitude + ")";
	}

	// Converts string formated as "n, m" where n and m are 64-bit floating point.
	public GeoLocation toGeoLocation(String geoLocation) {
		List<String> cords = Arrays.asList(geoLocation.split(","));
		try {
			return new GeoLocation(Double.parseDouble(cords.get(0)), Double.parseDouble(cords.get(1)));
		}catch(NumberFormatException e) {
			return new GeoLocation();
		}
	}

	private boolean validateLatitude(double latitude) {
		return (latitude <= 90 && latitude >= -90);
	}
	private boolean validateLongitude(double longitude) {
		return (longitude <= 180 && longitude >= -180);
	}

}
