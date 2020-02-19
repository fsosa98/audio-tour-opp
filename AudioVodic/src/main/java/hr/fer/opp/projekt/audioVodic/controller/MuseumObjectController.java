package hr.fer.opp.projekt.audioVodic.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hr.fer.opp.projekt.audioVodic.Util;
import hr.fer.opp.projekt.audioVodic.model.MuseumObject;
import hr.fer.opp.projekt.audioVodic.model.Statistics;
import hr.fer.opp.projekt.audioVodic.model.User;
import hr.fer.opp.projekt.audioVodic.service.MuseumObjectService;
import hr.fer.opp.projekt.audioVodic.service.StatisticsService;
import hr.fer.opp.projekt.audioVodic.service.UserService;

@Controller
public class MuseumObjectController {

	@Autowired
	private MuseumObjectService museumObjectService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private UserService userService;

	@Autowired
	ServletContext servletContext;

	@RequestMapping({ "/", "/museumObjects" })
	public String getMuseumObjects(HttpServletRequest req) {

		updateTime(req);

		req.setAttribute("groupsOfObjects", museumObjectService.getAllObjectsByGroups());
		req.setAttribute("groupNames", museumObjectService.getAllgroupsOfMuseumObjects());

		req.setAttribute("contextPath", servletContext.getContextPath());
		req.setAttribute("realPath", servletContext.getRealPath("WEB-INF/images"));

		return "museumObjects";
	}

	@RequestMapping("/museumObject/{id}")
	public String getMuseumObject(@PathVariable("id") int id, HttpServletRequest req) {

		updateTime(req);

		MuseumObject museumObject = museumObjectService.getMuseumObject(id);

		req.setAttribute("museumObject", museumObject);

		Statistics statistics = museumObject.getStatistics();

		statistics.addPageTraffic();
		statisticsService.addStatistics(statistics);

		req.getSession().setAttribute("id", id);
		req.getSession().setAttribute("startTime", System.currentTimeMillis());

		return "museumObject";
	}

	@RequestMapping("/museumObject/audio/{id}")
	public String getAudio(@PathVariable("id") int id, HttpServletRequest req) {

		updateTime(req);

		MuseumObject museumObject = museumObjectService.getMuseumObject(id);

		Statistics statistics = museumObject.getStatistics();
		statistics.addAudioCounter();
		statisticsService.addStatistics(statistics);

		req.setAttribute("museumObject", museumObject);

		req.setAttribute("contextPath", servletContext.getContextPath());

		return "audio";
	}

	@RequestMapping("/addMuseumObject")
	public String addMuseumObject(HttpServletRequest req) {
		updateTime(req);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (user == null || user.getRole().getName().equals("Registrirani korisnik")) {
			return "error";
		}

		req.setAttribute("groupNames", museumObjectService.getAllgroupsOfMuseumObjects());

		return "addMuseumObject";
	}

	@RequestMapping("/museumObject/deleteMuseumObject/{id}")
	public String deleteMuseumObject(@PathVariable("id") int id, HttpServletRequest req) {

		updateTime(req);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (user == null || user.getRole().getName().equals("Registrirani korisnik")) {
			return "error";
		}

		MuseumObject museumObject = museumObjectService.getMuseumObject(id);
		museumObject.setInMuseum(false);

		museumObjectService.addMuseumObject(museumObject);

		return "redirect:/museumObjects";
	}

	@RequestMapping(value = "/addMuseumObject", method = RequestMethod.POST)
	public String validationMuseumObject(@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2, HttpServletRequest req, HttpServletResponse res) {

		if (!"Dodaj".equals(req.getParameter("metoda"))) {

			return "redirect:/museumObjects";
		}

		req.setAttribute("groupNames", museumObjectService.getAllgroupsOfMuseumObjects());

		String titleError = "";
		String descriptionError = "";
		String imageAndAudioError = "";
		String groupError = "";

		boolean titleHasError = false;
		boolean descriptionHasError = false;
		boolean imageAndAudioHasError = false;
		boolean groupHasError = false;

		String title = req.getParameter("title");
		String text = req.getParameter("text");
		String group = req.getParameter("group");
		String checkBox = req.getParameter("checkBox");

		req.setAttribute("title", title);
		req.setAttribute("text", text);
		req.setAttribute("group", group);

		// title
		if (title == null || title.isEmpty() || !Util.checkTitle(title)) {
			titleError = "Možete upotrebljavati samo slova, brojeve i razmak";
			titleHasError = true;
			req.setAttribute("titleError", titleError);
			req.setAttribute("titleHasError", titleHasError);
		}

		// text
		if (text == null || text.isEmpty() || Util.checkDescription(text) != null) {
			descriptionError = "Unesite opis muzejskog objekta";
			if (Util.checkDescription(text) != null) {
				descriptionError = Util.checkDescription(text);
			}
			descriptionHasError = true;
			req.setAttribute("descriptionError", descriptionError);
			req.setAttribute("descriptionHasError", descriptionHasError);
		}

		// group
		if (checkBox != null && checkBox.equals("on")) {
			if (group == null || group.isEmpty() || !Util.checkTitle(group)) {
				groupError = "Možete upotrebljavati samo slova, brojeve i razmak";
				groupHasError = true;
				req.setAttribute("groupError", groupError);
				req.setAttribute("groupHasError", groupHasError);
			}
		} else {
			group = req.getParameter("groupSelect");
		}

		if (titleHasError || descriptionHasError || groupHasError) {
			return "addMuseumObject";
		}

		if (file1 == null || file1.isEmpty() || file2 == null || file2.isEmpty()) {
			imageAndAudioError = "Izaberite sliku i zvučni zapis";
			imageAndAudioHasError = true;
			req.setAttribute("imageAndAudioError", imageAndAudioError);
			req.setAttribute("imageHasError", imageAndAudioHasError);
			return "addMuseumObject";
		}

		MuseumObject museumObject = new MuseumObject();
		museumObjectService.addMuseumObject(museumObject);
		museumObject.setImageName(file1.getOriginalFilename().split("\\.")[1]);
		museumObject.setAudioName(file2.getOriginalFilename().split("\\.")[1]);

		// SLIKA
		Path filepath1 = null;

		if (file1.getContentType().startsWith("image")) {
			filepath1 = Paths.get(servletContext.getRealPath("/WEB-INF/images"), museumObject.getImageName());
			try {
				Files.write(filepath1, file1.getBytes());
			} catch (IOException e) {
				imageAndAudioError = "Greška";
				imageAndAudioHasError = true;
			}
		} else {
			imageAndAudioError = "Izaberite sliku i zvučni zapis";
			imageAndAudioHasError = true;
		}

		if (imageAndAudioHasError) {
			filepath1.toFile().delete();
			museumObjectService.deleteMuseumObject(museumObject);
			req.setAttribute("imageAndAudioError", imageAndAudioError);
			req.setAttribute("imageHasError", imageAndAudioHasError);
			return "addMuseumObject";
		}

		// AUDIO
		Path filepath2 = null;

		if (file2.getContentType().startsWith("audio")) {
			filepath2 = Paths.get("src/main/resources/static/audio", museumObject.getAudioName());
			try {
				Files.write(filepath2, file2.getBytes());
			} catch (IOException e) {
				imageAndAudioError = "Greška";
				imageAndAudioHasError = true;
			}
		} else {
			imageAndAudioError = "Izaberite sliku i zvučni zapis";
			imageAndAudioHasError = true;
		}

		if (imageAndAudioHasError) {
			filepath2.toFile().delete();
			museumObjectService.deleteMuseumObject(museumObject);
			req.setAttribute("imageAndAudioError", imageAndAudioError);
			req.setAttribute("imageAndAudioHasError", imageAndAudioHasError);
			return "addMuseumObject";
		}

		museumObject.setName(title);
		museumObject.setDescription(text);
		museumObject.setGroupOfMuseumObjects(group);
		museumObject.setInMuseum(true);

		Statistics newStatistics = new Statistics();
		statisticsService.addStatistics(newStatistics);
		museumObject.setStatistics(newStatistics);

		museumObjectService.addMuseumObject(museumObject);

		return "redirect:/museumObjects";
	}

	@RequestMapping("/museumObject/editMuseumObject/{id}")
	public String editMuseumObject(@PathVariable("id") int id, HttpServletRequest req) {

		updateTime(req);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (user == null || user.getRole().getName().equals("Registrirani korisnik")) {
			return "error";
		}
		
		req.setAttribute("groupNames", museumObjectService.getAllgroupsOfMuseumObjects());

		MuseumObject museumObject = museumObjectService.getMuseumObject(id);

		String titleError = "";
		String descriptionNameError = "";
		String groupError = "";

		req.setAttribute("titleError", titleError);
		req.setAttribute("descriptionNameError", descriptionNameError);
		req.setAttribute("groupError", groupError);

		boolean titleHasError = false;
		boolean descriptionHasError = false;
		boolean groupHasError = false;

		req.setAttribute("titleHasError", titleHasError);
		req.setAttribute("descriptionHasError", descriptionHasError);
		req.setAttribute("groupHasError", groupHasError);

		req.setAttribute("title", museumObject.getName());
		req.setAttribute("text", museumObject.getDescription());
		req.setAttribute("group", museumObject.getGroupOfMuseumObjects());

		return "editMuseumObject";
	}

	@RequestMapping(value = "/museumObject/editMuseumObject/{id}", method = RequestMethod.POST)
	public String editValidation(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
			@PathVariable("id") int id, HttpServletRequest req, HttpServletResponse res) {

		if (!"Potvrdi".equals(req.getParameter("metoda"))) {

			return "redirect:/museumObject/" + id;
		}
		
		req.setAttribute("groupNames", museumObjectService.getAllgroupsOfMuseumObjects());

		MuseumObject museumObject = museumObjectService.getMuseumObject(id);

		String titleError = "";
		String descriptionError = "";
		String imageAndAudioError = "";
		String groupError = "";

		boolean imageAndAudioHasError = false;
		boolean groupHasError = false;

		req.setAttribute("titleError", titleError);
		req.setAttribute("descriptionNameError", descriptionError);
		req.setAttribute("imageAndAudioError", imageAndAudioError);
		req.setAttribute("groupError", groupError);

		boolean titleHasError = false;
		boolean descriptionHasError = false;

		String title = req.getParameter("title");
		String text = req.getParameter("text");
		String group = req.getParameter("group");
		String checkBox = req.getParameter("checkBox");

		req.setAttribute("title", title);
		req.setAttribute("text", text);
		req.setAttribute("group", group);

		// title
		if (title == null || title.isEmpty() || !Util.checkTitle(title)) {
			titleError = "Možete upotrebljavati samo slova";
			titleHasError = true;
			req.setAttribute("titleError", titleError);
			req.setAttribute("titleHasError", titleHasError);
		}

		// text
		if (text == null || text.isEmpty() || Util.checkDescription(text) != null) {
			descriptionError = "Unesite opis muzejskog objekta";
			if (Util.checkDescription(text) != null) {
				descriptionError = Util.checkDescription(text);
			}
			descriptionHasError = true;
			req.setAttribute("descriptionError", descriptionError);
			req.setAttribute("descriptionHasError", descriptionHasError);
		}

		// group
		if (checkBox != null && checkBox.equals("on")) {
			if (group == null || group.isEmpty() || !Util.checkTitle(group)) {
				groupError = "Možete upotrebljavati samo slova, brojeve i razmak";
				groupHasError = true;
				req.setAttribute("groupError", groupError);
				req.setAttribute("groupHasError", groupHasError);
			}
		} else {
			group = req.getParameter("groupSelect");
		}

		if (titleHasError || descriptionHasError || groupHasError) {
			return "editMuseumObject";
		}

		// SLIKA
		if (!file1.isEmpty()) {
			Path filepath1 = null;

			if (file1.getContentType().startsWith("image")) {
				filepath1 = Paths.get(servletContext.getRealPath("/WEB-INF/images"), museumObject.getImageName());
				try {
					Files.write(filepath1, file1.getBytes());
				} catch (IOException e) {
					imageAndAudioError = "Greška";
					imageAndAudioHasError = true;
				}
			} else {
				imageAndAudioError = "Izaberite sliku i zvučni zapis";
				imageAndAudioHasError = true;
			}

			if (imageAndAudioHasError) {
				filepath1.toFile().delete();
				museumObjectService.deleteMuseumObject(museumObject);
				req.setAttribute("imageAndAudioError", imageAndAudioError);
				req.setAttribute("imageHasError", imageAndAudioHasError);
				return "addMuseumObject";
			}
		}

		// AUDIO
		if (!file2.isEmpty()) {
			Path filepath2 = null;

			if (file2.getContentType().startsWith("audio")) {
				filepath2 = Paths.get("src/main/resources/static/audio", museumObject.getAudioName());
				try {
					Files.write(filepath2, file2.getBytes());
				} catch (IOException e) {
					imageAndAudioError = "Greška";
					imageAndAudioHasError = true;
				}
			} else {
				imageAndAudioError = "Izaberite sliku i zvučni zapis";
				imageAndAudioHasError = true;
			}

			if (imageAndAudioHasError) {
				filepath2.toFile().delete();
				museumObjectService.deleteMuseumObject(museumObject);
				req.setAttribute("imageAndAudioError", imageAndAudioError);
				req.setAttribute("imageAndAudioHasError", imageAndAudioHasError);
				return "addMuseumObject";
			}
		}

		museumObject.setName(title);
		museumObject.setDescription(text);
		museumObject.setGroupOfMuseumObjects(group);
		museumObject.setInMuseum(true);
		museumObjectService.addMuseumObject(museumObject);

		return "redirect:/museumObject/" + museumObject.getId();
	}

	private void updateTime(HttpServletRequest req) {

		Integer id = (Integer) req.getSession().getAttribute("id");
		if (id == null) {
			return;
		}

		long endTime = System.currentTimeMillis();
		long startTime = (long) req.getSession().getAttribute("startTime");
		long time = endTime - startTime;

		Statistics statistics = museumObjectService.getMuseumObject(id).getStatistics();
		statistics.updateTime(time);
		statisticsService.addStatistics(statistics);

		req.getSession().removeAttribute("id");
		req.getSession().removeAttribute("startTime");

	}


}
