/*
	Mario Morales
	3 Dec. 2018
	Description: Defines a CTA station. Extends GeoLocation to define its location.
*/

public class CTAStation extends GeoLocation{

	private String name;
	// Physical location of the station: elevated, surface, etc.
	private String location;
	private boolean isAccessible;
    public int index;

	public CTAStation(){
		super();
		setName("CTA Station");
		setLocation("undefined");
		setAccessibility(false);
        this.index = -1;
	}

	public CTAStation(String name, double latitude, double longitude, String location, boolean isAccessible){
		super(latitude, longitude);
		setName(name);
		setLocation(location);
		setAccessibility(isAccessible);
        this.index = -1;
	}

	public String getName() {
		return name;
	}
	public String getLocation() {
		return location;
	}
	public boolean isWheelchairAccessible() {
		return isAccessible;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setAccessibility(boolean isAccessible) {
		this.isAccessible = isAccessible;
	}

	public String toString() {
		return (name + " ("+ index + ")" + ":\nAt: " + super.toString() + "\nLocated: "
				+ location + "\nWheelchair Accessible? " + isAccessible);
	}

	public boolean equals(CTAStation otherStation) {
		return (otherStation.toString().equals(this.toString()));
	}

	// Return distance between this station and another location
	public double distanceFrom(GeoLocation otherLocation) {
		return Math.sqrt(Math.pow(otherLocation.getLatitude()-super.getLatitude(),2)+
				Math.pow(otherLocation.getLongitude()-otherLocation.getLongitude(),2));
	}

	public double distanceFrom(double latitude, double longitude) {
		return Math.sqrt(Math.pow(latitude-super.getLatitude(),2)+
				Math.pow(longitude-super.getLongitude(),2));
	}

}
