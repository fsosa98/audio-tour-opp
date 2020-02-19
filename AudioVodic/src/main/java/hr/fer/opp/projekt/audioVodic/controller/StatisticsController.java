package hr.fer.opp.projekt.audioVodic.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.fer.opp.projekt.audioVodic.model.MuseumObject;
import hr.fer.opp.projekt.audioVodic.model.User;
import hr.fer.opp.projekt.audioVodic.service.MuseumObjectService;
import hr.fer.opp.projekt.audioVodic.service.UserService;

@Controller
public class StatisticsController {

	@Autowired
	private MuseumObjectService museumObjectService;
	@Autowired
	private UserService userService;

	@RequestMapping("/museumObjectStatistics")
	public String getStatistics(HttpServletRequest req) {

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (user == null || user.getRole().getName().equals("Registrirani korisnik")) {
			return "error";
		}

		List<MuseumObject> museumObjects = museumObjectService.getAllObjects();
		req.setAttribute("museumObjects", museumObjects);
		if (req.getParameter("sort") != null) {
			if (req.getParameter("sort").equals("pregledi")) {
				Collections.sort(museumObjects, (m1, m2) -> Integer.compare(m1.getStatistics().getPageTraffic(),
						m2.getStatistics().getPageTraffic()));
			}
			if (req.getParameter("sort").equals("vrijeme")) {
				Collections.sort(museumObjects,
						(m1, m2) -> Long.compare(m1.getStatistics().getTime(), m2.getStatistics().getTime()));
			}
		}

		int ukupno = 0;
		for (MuseumObject museumObject : museumObjects) {
			ukupno += museumObject.getStatistics().getPageTraffic();
		}

		req.setAttribute("ukupno", ukupno);

		return "museumObjectStatistics";
	}

}
