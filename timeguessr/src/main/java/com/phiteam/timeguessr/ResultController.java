package com.phiteam.timeguessr;

import java.io.IOException;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.phiteam.timeguessr.images.Image;
import com.phiteam.timeguessr.images.ImageManager;

@Controller
public class ResultController {

	private static final DecimalFormat DistanceNumberFormat = new DecimalFormat("0.##");
	@Autowired
	private ImageManager imageManager;

	@GetMapping("/resultpage")
	public String greeting(@RequestParam(name = "imageid", required = true) String imageid,
			@RequestParam(name = "year", required = true) int year,
			@RequestParam(name = "coordinates", required = true) String coordinates, Model model) throws IOException {

		Image image = imageManager.getImage(imageid);

		model.addAttribute("title", image.getDescription());
		model.addAttribute("yearsdiff", year - image.getYear());
		model.addAttribute("year", image.getYear());
		model.addAttribute("distancediff", getDistanceDiff(image.getCoordinates(), coordinates));
		model.addAttribute("coordinates", image.getCoordinates());

		return "resultpage";
	}

	private String getDistanceDiff(String coord1, String coord2) {
		double diffvalue = calculateDistance(coord1, coord2);
		if(diffvalue > 1000d) {
			diffvalue = diffvalue/1000;
			return DistanceNumberFormat.format(diffvalue) + " Kilometer";
		}
		return DistanceNumberFormat.format(diffvalue) + " Meter";
	}
	
	private double calculateDistance(String coord1, String coord2) {
		double lat1 = Double.parseDouble(coord1.split(",")[0]);
		double lon1 = Double.parseDouble(coord1.split(",")[1]);
		double lat2 = Double.parseDouble(coord2.split(",")[0]);
		double lon2 = Double.parseDouble(coord2.split(",")[1]);

		final int R = 6371; // Radius der Erde in Kilometern

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double distance = R * c * 1000; // Umwandlung in Meter

		distance = Math.pow(distance, 2);

		return Math.sqrt(distance);
	}

}