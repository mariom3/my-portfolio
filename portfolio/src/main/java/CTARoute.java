/*
	Mario Morales
	3 Dec. 2018
	Description: Defines a CTA route; the underlying data structure, an arraylist,
		is used to process all the CTA stations pertaining to the route.
*/

package com.google.sps.servlets;

import java.util.ArrayList;
import java.util.Scanner;

public class CTARoute {

	// The name of the route, in the form of "<Color> Line" ("Red Line", "Green Line", etc.).
	private String name;
	private ArrayList<CTAStation> stops;

	CTARoute(){
		name = "Unnamed Route";
		stops = new ArrayList<CTAStation>();
	}

	CTARoute(String name){
		this.name = name;
		stops = new ArrayList<CTAStation>();
	}

	CTARoute(String name, ArrayList<CTAStation> stops){
		setName(name);
		setStops(stops);
	}

	public String getName() {
		return name;
	}
	public ArrayList<CTAStation> getData() {
		return stops;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setStops(ArrayList<CTAStation> stops) {
		this.stops = stops;
	}

	public String toString() {
		String listStops = "";
		String visual = "------------------------------";
		String visualDos = "----------"; 
		for (int i=0; i < stops.size(); i++) {
			if (i != stops.size()-1) 
				listStops += (stops.get(i).toString() + "\n\n");
			else listStops += (stops.get(i).toString() + "\n");
		}
		return (visualDos + name + visualDos + "\n" + listStops + visual);
	}

	public boolean equals(CTARoute otherRoute) {
		return otherRoute.toString().equals(this.toString());
	}

	public void addStation(CTAStation station) {
		stops.add(station);
		sort(); //Yes I know sorting every time we add as opposed to doing so when were done adds more time complexity, but is negligible with the amount of data we're dealing with here.
	}

	public void insertStation(int position, CTAStation station) {
		try {
			stops.add(position, station);
			System.out.println(station.getName() + " was successfully added.");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Index out of bounds!" + station.getName() 
				+ " was added at the end instead.");
			stops.add(station);
		}
	}

	//Returns a new CTA station with parameters specified by the user.
	private CTAStation getStationVars(String stationName, Scanner in) {
		while(true) {
			try {
				System.out.print("Enter latitude: ");
				double lat = Double.parseDouble(in.nextLine());
				if(!(lat <= 90 && lat >= -90))
					System.out.println("Please enter valid coordinates!");
				else {
					System.out.print("Enter longitude: ");
					double lng = Double.parseDouble((in.nextLine()));
					if (!(lng <= 180 && lng >= -180))
						System.out.println("Please enter valid coordinates!");
					else {
						System.out.print("Enter the station's location: ");
						String location = in.nextLine();
						System.out.print("Enter if the station is wheelchair accessible " 
								+ "(true or false): ");
						boolean isAccessible = Boolean.parseBoolean(in.nextLine().toUpperCase());
						return new CTAStation(stationName, lat, lng, location, isAccessible);
					}
				}
			}catch(NumberFormatException e) {
				System.out.println("Please only numbers! Try again.");
			}
		}
	}

	public void removeStation(CTAStation station) {
		stops.remove(station);
	}

	// Returns index of station if found. Otherwise, returns -1.
	public int findStationIndex(String stationName) {
		for (int i = 0; i < stops.size(); i++)
			if (stops.get(i).getName().equals(stationName))
				return i;
		return -1;
	}

	// Returns the station if found. Otherwise, returns null.
	public CTAStation findStationObject(String name) {
		for (CTAStation s : stops)
			if (s.getName().equals(name))
				return s;
		return null;
	}

	// Returns the nearest station on this line, given coordinates.
	public CTAStation nearestStation(double lat, double lng) {
		int count = 0;
		int smallest_idx = 0;
		if(!(lat <= 90 && lat >= -90))
			System.out.println("Please enter valid coordinates!");
		else {
			if (!(lng <= 180 && lng >= -180))
				System.out.println("Please enter valid coordinates!");
			else {
				double smallest_dist = stops.get(0).calcDistance(lng, lat);
				for (CTAStation s : stops) {
					if (s.calcDistance(lng, lat) < smallest_dist) {
						smallest_dist = s.calcDistance(lng, lat);
						smallest_idx = count;
					}
					count ++;
				}
			}
		}
		return stops.get(smallest_idx);
	}

	// Returns the nearest station on this line, given a geolocation.
	public CTAStation nearestStation(GeoLocation location) {
		int count = 0;
		int smallest_idx = 0;
		double smallest_dist = stops.get(0).calcDistance(location);
		for (CTAStation s : stops) {
			if(s.calcDistance(location) < smallest_dist) {
				smallest_dist = s.calcDistance(location);
				smallest_idx = count;
			}
			count++;
		}
		return stops.get(smallest_idx);
	}

	// This is to override the one from station
	public double calcDistance(GeoLocation location, GeoLocation station) {
		return Math.sqrt(Math.pow(location.getLat()-station.getLat(),2)+
				Math.pow(location.getLng()-station.getLng(),2));
	}

	// Uses insertion sort to maintain CTA stations sorted
	public void sort() {
		for(int i=1; i < stops.size(); i++) {
			for(int j=i; j > 0; j--){
				if (stops.get(j-1).getIndex() > stops.get(j).getIndex()) {
					CTAStation temp = stops.get(j-1);
					stops.set(j-1, stops.get(j));
					stops.set(j, temp);
				}
			}
		}
	}

	// Used to ensure proper indexing is maintained, especially when adding and removing stations from CTA route.
	public void fixIndex(){
		for(int i=1; i < stops.size(); i++) 
			stops.get(i).setIndex(i);
	}

}
