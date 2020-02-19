package hr.fer.opp.projekt.audioVodic.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.opp.projekt.audioVodic.Util;
import hr.fer.opp.projekt.audioVodic.model.Role;
import hr.fer.opp.projekt.audioVodic.model.Statistics;
import hr.fer.opp.projekt.audioVodic.model.User;
import hr.fer.opp.projekt.audioVodic.service.MuseumObjectService;
import hr.fer.opp.projekt.audioVodic.service.NotificationService;
import hr.fer.opp.projekt.audioVodic.service.RoleService;
import hr.fer.opp.projekt.audioVodic.service.StatisticsService;
import hr.fer.opp.projekt.audioVodic.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private MuseumObjectService museumObjectService;

	@RequestMapping("/confirmRegistration/{id}")
	public ModelAndView confirmRegistration(@PathVariable("id") int id) {
		User user = userService.getUser(id);
		user.setAccConfirmed(true);
		userService.addUser(user);

		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("welcome");
		return mv;
	}

	@RequestMapping("/registrationForm")
	public String showRegistrationForm() {
		return "registrationForm";
	}

	@RequestMapping(value = "/registrationForm", method = RequestMethod.POST)
	public String registrationValidation(HttpServletRequest req, HttpServletResponse res) {

		String firstNameError = "";
		String lastNameError = "";
		String usernameError = "";
		String emailError = "";
		String passwordError = "";

		boolean firstNameHasError = false;
		boolean lastNameHasError = false;
		boolean usernameHasError = false;
		boolean emailHasError = false;
		boolean passwordHasError = false;

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");

		req.setAttribute("firstName", firstName);
		req.setAttribute("lastName", lastName);
		req.setAttribute("username", username);
		req.setAttribute("email", email);

		// firstName
		if (firstName == null || firstName.isEmpty() || !Util.checkIsAlphabetic(firstName)) {
			firstNameError = "Možete upotrebljavati slova";
			firstNameHasError = true;
			req.setAttribute("firstNameError", firstNameError);
			req.setAttribute("firstNameHasError", firstNameHasError);
		}

		// lastName
		if (lastName == null || lastName.isEmpty() || !Util.checkIsAlphabetic(lastName)) {
			lastNameError = "Možete upotrebljavati slova";
			lastNameHasError = true;
			req.setAttribute("lastNameError", lastNameError);
			req.setAttribute("lastNameHasError", lastNameHasError);
		}

		// username
		if (username == null || username.isEmpty() || !Util.checkUsername(username)) {
			usernameError = "Možete upotrebljavati slova i brojeve";
			usernameHasError = true;
			req.setAttribute("usernameError", usernameError);
			req.setAttribute("usernameHasError", usernameHasError);
		}
		if (!usernameHasError) {
			User user = userService.getUserByUsername(username);
			if (user != null) {
				usernameError = "Korisničko ime je zauzeto";
				usernameHasError = true;
				req.setAttribute("usernameError", usernameError);
				req.setAttribute("usernameHasError", usernameHasError);
			}
		}

		// email
		if (email == null || email.isEmpty() || !Util.checkMail(email)) {
			emailError = "Možete upotrebljavati slova, brojeve i točke";
			emailHasError = true;
			req.setAttribute("emailError", emailError);
			req.setAttribute("emailHasError", emailHasError);
		}

		// password
		if (password == null || password.isEmpty() || !Util.checkPassword(password) || confirmPassword == null
				|| confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
			passwordError = "Lozinka ne može biti kraća od 7 znakova i treba sadržavati barem jedno veliko i malo slovo";
			passwordHasError = true;
			req.setAttribute("passwordError", passwordError);
			req.setAttribute("passwordHasError", passwordHasError);
		}

		if (firstNameHasError || lastNameHasError || usernameHasError || emailHasError || passwordHasError) {
			return "registrationForm";
		}

		Role role = roleService.getRoleByName("Registrirani korisnik");
		if (role == null) {
			return "error";
		}

		try {
			User user = new User(username, firstName, lastName, email, Util.sha(req.getParameter("password")), role, false,
					false);
			userService.addUser(user);
			
			notificationService.sendNotification(user);
			
		} catch (MailException exc) {
			return "error";
		}

		return "redirect:/museumObjects";
	}

	@RequestMapping("/logInForm")
	public String showLogInForm(HttpServletRequest req) {
		updateTime(req);
		return "logIn";
	}

	@RequestMapping(value = "/logInForm", method = RequestMethod.POST)
	public String logInValidation(HttpServletRequest req, HttpServletResponse res) {

		User user = null;

		if (!"Login".equals(req.getParameter("metoda"))) {
			String nick = (String) req.getSession().getAttribute("current.user.nick");
			user = userService.getUserByUsername(nick);
			user.setCurrentlyActive(false);
			userService.addUser(user);
			req.getSession().removeAttribute("current.user.fn");
			req.getSession().removeAttribute("current.user.ln");
			req.getSession().removeAttribute("current.user.nick");
			req.getSession().removeAttribute("admin");
			req.getSession().removeAttribute("vlasnik");

			return "redirect:/museumObjects";
		}

		String logInError = "";

		boolean logInHasError = false;

		String username = req.getParameter("username");
		user = userService.getUserByUsername(username);
		String passwordHash = Util.sha(req.getParameter("password"));

		req.setAttribute("username", username);

		if (user == null || !((user.getPasswordHash()).equals(passwordHash))) {
			logInHasError = true;
			logInError = "Neispravno korisničko ime ili lozinka";
			req.setAttribute("logInError", logInError);
			req.setAttribute("logInHasError", logInHasError);
			return "logIn";
		}
		if (!user.isAccConfirmed()) {
			logInHasError = true;
			logInError = "Potvrdite registraciju putem mail adrese";
			req.setAttribute("logInError", logInError);
			req.setAttribute("logInHasError", logInHasError);
			return "logIn";
		}

		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getUsername());

		if (user.getRole().getName().equals("Administrator")) {
			req.getSession().setAttribute("admin", true);
		}

		if (user.getRole().getName().equals("Vlasnik sustava")) {
			req.getSession().setAttribute("vlasnik", true);
		}

		user.setCurrentlyActive(true);
		userService.addUser(user);

		return "redirect:/museumObjects";

	}

	@RequestMapping("/showProfile")
	public String showProfile(HttpServletRequest req) {

		updateTime(req);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);

		if (user == null) {
			return "error";
		}

		req.setAttribute("user", user);

		return "showProfile";
	}

	@RequestMapping("/editUser/{id}")
	public String editProfile(@PathVariable("id") int id, HttpServletRequest req) {

		updateTime(req);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (user == null) {
			return "error";
		}

		String firstNameError = "";
		String lastNameError = "";
		String usernameError = "";
		String emailError = "";
		String passwordError = "";

		req.setAttribute("firstNameError", firstNameError);
		req.setAttribute("lastNameError", lastNameError);
		req.setAttribute("usernameError", usernameError);
		req.setAttribute("emailError", emailError);
		req.setAttribute("passwordError", passwordError);

		boolean firstNameHasError = false;
		boolean lastNameHasError = false;
		boolean usernameHasError = false;
		boolean emailHasError = false;
		boolean passwordHasError = false;

		req.setAttribute("firstNameHasError", firstNameHasError);
		req.setAttribute("lastNameHasError", lastNameHasError);
		req.setAttribute("usernameHasError", usernameHasError);
		req.setAttribute("emailHasError", emailHasError);
		req.setAttribute("passwordHasError", passwordHasError);

		req.setAttribute("firstName", user.getFirstName());
		req.setAttribute("lastName", user.getLastName());
		req.setAttribute("username", user.getUsername());
		req.setAttribute("email", user.getEmail());

		return "editProfile";
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public String editValidation(HttpServletRequest req, HttpServletResponse res) {

		String firstNameError = "";
		String lastNameError = "";
		String usernameError = "";
		String emailError = "";
		String passwordError = "";

		boolean firstNameHasError = false;
		boolean lastNameHasError = false;
		boolean usernameHasError = false;
		boolean emailHasError = false;
		boolean passwordHasError = false;

		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		String checkBox = req.getParameter("checkBox");

		req.setAttribute("firstName", firstName);
		req.setAttribute("lastName", lastName);
		req.setAttribute("username", username);
		req.setAttribute("email", email);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);

		// firstName
		if (firstName == null || firstName.isEmpty() || !Util.checkIsAlphabetic(firstName)) {
			firstNameError = "Možete upotrebljavati samo slova";
			firstNameHasError = true;
			req.setAttribute("firstNameError", firstNameError);
			req.setAttribute("firstNameHasError", firstNameHasError);
		}

		// lastName
		if (lastName == null || lastName.isEmpty() || !Util.checkIsAlphabetic(lastName)) {
			lastNameError = "Možete upotrebljavati samo slova";
			lastNameHasError = true;
			req.setAttribute("lastNameError", lastNameError);
			req.setAttribute("lastNameHasError", lastNameHasError);
		}

		// username
		if (username == null || username.isEmpty()
				|| (!user.getUsername().equals(username) && !Util.checkUsername(username))) {
			usernameError = "Možete upotrebljavati samo slova i brojeve";
			usernameHasError = true;
			req.setAttribute("usernameError", usernameError);
			req.setAttribute("usernameHasError", usernameHasError);
		}
		if (!usernameHasError) {
			User user2 = userService.getUserByUsername(username);
			if (user2 != null && !user.getUsername().equals(username)) {
				usernameError = "Korisničko ime je zauzeto";
				usernameHasError = true;
				req.setAttribute("usernameError", usernameError);
				req.setAttribute("usernameHasError", usernameHasError);
			}
		}

		// email
		if (email == null || email.isEmpty() || !Util.checkMail(email)) {
			emailError = "Možete upotrebljavati samo slova, brojeve i točke";
			emailHasError = true;
			req.setAttribute("emailError", emailError);
			req.setAttribute("emailHasError", emailHasError);
		}

		// password
		if (checkBox != null && checkBox.equals("on")) {
			if (oldPassword == null || oldPassword.isEmpty() || newPassword == null || newPassword.isEmpty()) {
				passwordError = "Unesite staru i novu lozinku";
				passwordHasError = true;
				req.setAttribute("passwordError", passwordError);
				req.setAttribute("passwordHasError", passwordHasError);
			} else {
				if (!user.getPasswordHash().equals(Util.sha(oldPassword))) {
					passwordError = "Unijeli ste krivu staru lozinku";
					passwordHasError = true;
					req.setAttribute("passwordError", passwordError);
					req.setAttribute("passwordHasError", passwordHasError);
				} else if (!Util.checkPassword(newPassword)) {
					passwordError = "Lozinka ne može biti kraća od 7 znakova i mora sadržavati barem jedno veliko i malo slovo";
					passwordHasError = true;
					req.setAttribute("passwordError", passwordError);
					req.setAttribute("passwordHasError", passwordHasError);
				}
			}
		}

		if (firstNameHasError || lastNameHasError || usernameHasError || emailHasError || passwordHasError) {
			return "editProfile";
		}

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setUsername(username);
		if (checkBox != null && checkBox.equals("on")) {
			user.setPasswordHash(Util.sha(newPassword));
		}

		userService.addUser(user);

		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getUsername());

		if (user.getRole().getName().equals("Administrator")) {
			req.getSession().setAttribute("admin", true);
		}

		if (user.getRole().getName().equals("Vlasnik sustava")) {
			req.getSession().setAttribute("vlasnik", true);
		}

		return "redirect:/museumObjects";
	}

	@RequestMapping("/creatingAdmin")
	public String creatingAdmin(HttpServletRequest req) {
		updateTime(req);

//		if (userService.getAdmins().size() >= 5) {
//			return "error";
//		}

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (!(user != null && user.getRole().getName().equals("Vlasnik sustava"))) {
			return "error";
		}

		return "creatingAdmin";
	}

	@RequestMapping(value = "/creatingAdmin", method = RequestMethod.POST)
	public String adminValidation(HttpServletRequest req, HttpServletResponse res) {

		String usernameError = "";
		String passwordError = "";

		boolean usernameHasError = false;
		boolean passwordHasError = false;

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		req.setAttribute("username", username);

		if (userService.getAdmins().size() >= 5) {
			passwordError = "Maksimalan broj administratora je 5";
			passwordHasError = true;
			req.setAttribute("passwordError", passwordError);
			req.setAttribute("passwordHasError", passwordHasError);
			return "creatingAdmin";
		}

		// username
		if (username == null || username.isEmpty() || !Util.checkUsername(username)) {
			usernameError = "Možete upotrebljavati samo slova i brojeve";
			usernameHasError = true;
			req.setAttribute("usernameError", usernameError);
			req.setAttribute("usernameHasError", usernameHasError);
		}
		if (!usernameHasError) {
			User user = userService.getUserByUsername(username);
			if (user != null) {
				usernameError = "Korisničko ime je zauzeto";
				usernameHasError = true;
				req.setAttribute("usernameError", usernameError);
				req.setAttribute("usernameHasError", usernameHasError);
			}
		}

		// password
		if (password == null || password.isEmpty() || !Util.checkPassword(password)) {
			passwordError = "Lozinka ne može biti kraća od 7 znakova i treba sadržavati barem jedno veliko i malo slovo";
			passwordHasError = true;
			req.setAttribute("passwordError", passwordError);
			req.setAttribute("passwordHasError", passwordHasError);
		}

		if (usernameHasError || passwordHasError) {
			return "creatingAdmin";
		}

		Role role = roleService.getRoleByName("Administrator");
		if (role == null) {
			return "error";
		}

		User user = new User();
		user.setUsername(username);
		user.setPasswordHash(Util.sha(req.getParameter("password")));
		user.setRole(role);
		user.setAccConfirmed(true);
		user.setCurrentlyActive(false);
		userService.addUser(user);

		return "redirect:/museumObjects";
	}

	@RequestMapping("/editUsers")
	public String editUsers(HttpServletRequest req) {

		updateTime(req);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (!(user != null && user.getRole().getName().equals("Vlasnik sustava"))) {
			return "error";
		}

		User vlasnik = userService.getVlasnik();
		List<User> admins = userService.getAdmins();
		List<User> registeredUsers = userService.getRegisteredUsers();

		req.setAttribute("vlasnik", vlasnik);
		req.setAttribute("admins", admins);
		req.setAttribute("registeredUsers", registeredUsers);
		return "editUsers";
	}

	@RequestMapping("/changeUser/{id}")
	public String changeUser(@PathVariable("id") int id, HttpServletRequest req) {

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (!(user != null && user.getRole().getName().equals("Vlasnik sustava"))) {
			return "error";
		}

		user = userService.getUser(id);
		if (user.getRole().getName().equals("Administrator")) {
			user.setRole(roleService.getRoleByName("Registrirani korisnik"));
		} else {
			if (userService.numberOfAdmins() < 5) {
				user.setRole(roleService.getRoleByName("Administrator"));
			}
		}
		userService.addUser(user);

		return "redirect:/editUsers";
	}

	@RequestMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id, HttpServletRequest req) {

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (!(user != null && user.getRole().getName().equals("Vlasnik sustava"))) {
			return "error";
		}

		user = userService.getUser(id);
		userService.deleteUser(user);

		return "redirect:/editUsers";
	}

	@RequestMapping("/onlineUsers")
	public String onlineUsers(HttpServletRequest req) {

		updateTime(req);

		String nick = (String) req.getSession().getAttribute("current.user.nick");
		User user = userService.getUserByUsername(nick);
		if (user == null || user.getRole().getName().equals("Registrirani korisnik")) {
			return "error";
		}

		List<User> onlineAdmins = userService.getOnlineAdmins();
		List<User> onlineRegisteredUsers = userService.getOnlineRegisteredUsers();

		req.setAttribute("onlineAdmins", onlineAdmins);
		req.setAttribute("onlineRegisteredUsers", onlineRegisteredUsers);

		return "onlineUsers";
	}

	private void updateTime(HttpServletRequest req) {

		Integer id = (Integer) req.getAttribute("id");
		if (id == null) {
			return;
		}

		long endTime = System.currentTimeMillis();
		long startTime = (long) req.getAttribute("startTime");
		long time = endTime - startTime;

		Statistics statistics = museumObjectService.getMuseumObject(id).getStatistics();
		statistics.updateTime(time);
		statisticsService.addStatistics(statistics);

		req.removeAttribute("id");
		req.removeAttribute("startTime");

	}
}
