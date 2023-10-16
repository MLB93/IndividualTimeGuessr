package com.phiteam.timeguessr.images;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Image {
	private String id;
	private String coordinates;
	private int year;
	private String description;
	private String author;

	public Image(String id, String coordinates, int year, String description, String author) {
		super();
		this.id = id;
		this.coordinates = coordinates;
		this.year = year;
		this.description = description;
		this.author=author;
	}

	public String getId() {
		return id;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public int getYear() {
		return year;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}

	public String getBase64Image() {
		try (InputStream imageStream = getClass().getResourceAsStream("/static/images/" + id);) {
			byte[] imageBytes = imageStream.readAllBytes();

			String encodeToString = Base64.getEncoder().encodeToString(imageBytes);
			return encodeToString;
		} catch (Exception e) {
			System.out.println("Image " + id + " not loaded");
			System.out.println(e.getClass().getSimpleName()+": "+e.getMessage());
			return null;
		}
	}

}
