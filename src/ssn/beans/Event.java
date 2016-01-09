package ssn.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class Event {
	public enum States {OPEN, RESERVED, COMPLETED, FINISHED, CANCELED}
	private int idEvent;
	private int idCreator;
	private int idSport;
	private String sportName;
	private int actualPlayers;
	private int minPlayers;
	private int maxPlayers;
	private long startDate;
	private long endDate;
	private String city;
	private double latitude;
	private double longitude;
	private double range;
	private double maxPrice;
	private List<Integer> managerEntities;
	private boolean joined;
	private States state;
	private long limitDate;
	private int idReservation;
	
	public int getIdEvent() {
		return idEvent;
	}
	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}
	public int getIdCreator() {
		return idCreator;
	}
	public void setIdCreator(int idCreator) {
		this.idCreator = idCreator;
	}
	public int getIdSport() {
		return idSport;
	}
	public void setIdSport(int idSport) {
		this.idSport = idSport;
	}
	public int getMinPlayers() {
		return minPlayers;
	}
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}
    @XmlElement( name="item" )
    @XmlElementWrapper( name="managerEntities" )
	public List<Integer> getManagerEntities() {
		return managerEntities;
	}
	public void setManagerEntities(List<Integer> managerEntities) {
		this.managerEntities = managerEntities;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getSportName() {
		return sportName;
	}
	public void setSportName(String sportName) {
		this.sportName = sportName;
	}
	public int getActualPlayers() {
		return actualPlayers;
	}
	public void setActualPlayers(int actualPlayers) {
		this.actualPlayers = actualPlayers;
	}
	public boolean isJoined() {
		return joined;
	}
	public void setJoined(boolean joined) {
		this.joined = joined;
	}
	public States getState() {
		return state;
	}
	public void setState(States state) {
		this.state = state;
	}
	public long getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(long limitDate) {
		this.limitDate = limitDate;
	}
	public int getIdReservation() {
		return idReservation;
	}
	public void setIdReservation(int idReservation) {
		this.idReservation = idReservation;
	}
}
