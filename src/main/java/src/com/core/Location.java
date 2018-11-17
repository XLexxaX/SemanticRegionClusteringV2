package src.com.core;

public class Location {
	public double longitude = 0d;
	public double latitude = 0d;
	public Location(double latitude, double longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Location() {
	}
	//Calc distance to another location object
	public double distance(Location loc) {
		return Math.sqrt(Math.pow(loc.latitude - latitude, 2) + Math.pow(loc.longitude - longitude, 2) );
	}
}
