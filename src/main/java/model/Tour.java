package model;

import java.util.ArrayList;

public class Tour {
	private int id;
	private String name;
	private String duration, schedule, departure;
	private float price;
	private String transport;
	private ArrayList<String> imagePaths;

	public Tour(int id, String name, String duration, String schedule, String departure, float price,
			String transport) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.schedule = schedule;
		this.departure = departure;
		this.price = price;
		this.transport = transport;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departure == null) ? 0 : departure.hashCode());
		result = prime * result + ((duration == null) ? 0 : duration.hashCode());
		result = prime * result + id;
		result = prime * result + ((imagePaths == null) ? 0 : imagePaths.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((transport == null) ? 0 : transport.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tour other = (Tour) obj;
		if (departure == null) {
			if (other.departure != null)
				return false;
		} else if (!departure.equals(other.departure))
			return false;
		if (duration == null) {
			if (other.duration != null)
				return false;
		} else if (!duration.equals(other.duration))
			return false;
		if (id != other.id)
			return false;
		if (imagePaths == null) {
			if (other.imagePaths != null)
				return false;
		} else if (!imagePaths.equals(other.imagePaths))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (transport == null) {
			if (other.transport != null)
				return false;
		} else if (!transport.equals(other.transport))
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public ArrayList<String> getImagePaths() {
		return imagePaths;
	}

	public void setImagePaths(ArrayList<String> imagePaths) {
		this.imagePaths = imagePaths;
	}

	public Tour(int id, String name, String duration, String schedule, String departure, float price, String transport,
			ArrayList<String> imagePaths) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.schedule = schedule;
		this.departure = departure;
		this.price = price;
		this.transport = transport;
		this.imagePaths = imagePaths;
	}
}
