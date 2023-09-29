package com.phiteam.timeguessr;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.phiteam.timeguessr.images.Image;
import com.phiteam.timeguessr.images.ImageManager;

@Controller
public class GuessController {

	@Autowired
	private ImageManager imageManager;

	@GetMapping("/guesspage")
	public String greeting(Model model) throws IOException {

		Image image = imageManager.getRandomImage();

		model.addAttribute("base64Image", image.getBase64Image());
		model.addAttribute("imageid", image.getId());

		return "guesspage";
	}

}