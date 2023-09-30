package com.phiteam.timeguessr.images;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ImageManager {
	private static final String JSON_FILE = "/imageinfos/images.json";
	private List<Image> images;
	private List<Image> notShownImages;

	public ImageManager() {
		images = createImages();
		notShownImages = new ArrayList<Image>(images);
	}

	public Image getRandomImage() {
		if (images.isEmpty())
			throw new ImageDescriptionNotLoadedException();

		if (notShownImages.isEmpty()) {
			System.out.println("All images shown. Refill.");
			notShownImages = new ArrayList<Image>(images);
		}

		Image image = notShownImages.get((int) (Math.random() * notShownImages.size()));
		notShownImages.remove(image);
		return image;
	}

	public Image getImage(String imageId) {
		for (Image image : images) {
			if (image.getId().equals(imageId)) {
				return image;
			}
		}
		throw new ImageDescriptionNotLoadedException();
	}

	private List<Image> createImages() {
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(readImageInfoFile());

			List<Image> images = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jobj = (JSONObject) jsonArray.get(i);
				images.add(new Image(jobj.getString("id"), jobj.getString("coordinates"), jobj.getInt("year"),
						jobj.getString("desc"), jobj.getString("author")));
			}
			System.out.println("Image Data for " + images.size() + " images loaded");
			return images;
		} catch (JSONException | IOException e) {
			System.out.println(e.getMessage());
		}
		return Collections.emptyList();
	}

	private String readImageInfoFile() throws IOException {
		try (InputStream inputStream = getClass().getResourceAsStream(JSON_FILE);
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {

			String line;
			StringBuilder text = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				text.append(line);
			}
			return text.toString();
		}
	}

	public class ImageDescriptionNotLoadedException extends RuntimeException {
		private static final long serialVersionUID = 1L;

	}
}
