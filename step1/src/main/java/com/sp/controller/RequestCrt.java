package com.sp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sp.model.Poney;
import com.sp.model.PoneyForm;

@Controller // AND NOT @RestController
public class RequestCrt {
	
	// Injectez (inject) via application.properties.
	@Value("${welcome.message}")
	private String message;
	
	@Autowired
	PoneyDao poneyDao;
	
	private static String messageLocal="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("message", message);
		model.addAttribute("messageLocal", messageLocal);

		return "index";
	}
	
	@RequestMapping(value = { "/view"}, method = RequestMethod.GET)
	public String view(Model model) {

		model.addAttribute("myPoney",poneyDao.getRandomPoney() );

		return "poneyView";
	}
	
	@RequestMapping(value = { "/list"}, method = RequestMethod.GET)
	public String viewList(Model model) {

		model.addAttribute("poneyList",poneyDao.getPoneyList() );

		return "poneyViewList";
	}
	
	@RequestMapping(value = { "/addPoney"}, method = RequestMethod.GET)
	public String addponey(Model model) {
		PoneyForm poneyForm = new PoneyForm();
		model.addAttribute("poneyForm", poneyForm);
		return "poneyForm";
	}
	
	
	@RequestMapping(value = { "/addPoney"}, method = RequestMethod.POST)
	public String addponey(Model model, @ModelAttribute("poneyForm") PoneyForm poneyForm) {
		Poney p=poneyDao.addPoney(poneyForm.getName(),poneyForm.getColor(),poneyForm.getSuperPower(),poneyForm.getImgUrl());
		model.addAttribute("myPoney",p );
		return "poneyView";
	}
	
	//------------------------------------------------------------------------------------------- //
	//------------------------------------------ UIKIT ------------------------------------------ //
	//------------------------------------------------------------------------------------------- //

	@RequestMapping(value = { "/uk/addPoney"}, method = RequestMethod.POST)
	public String addponeyUk(Model model, @ModelAttribute("poneyForm") PoneyForm poneyForm) {
		Poney p=poneyDao.addPoney(poneyForm.getName(),poneyForm.getColor(),poneyForm.getSuperPower(),poneyForm.getImgUrl());
		model.addAttribute("myPoney",p );
		return "uk/poneyView";
	}
	
	@RequestMapping(value = { "/uk/addPoney"}, method = RequestMethod.GET)
	public String addponeyUk(Model model) {
		PoneyForm poneyForm = new PoneyForm();
		model.addAttribute("poneyForm", poneyForm);
		return "uk/poneyForm";
	}
	
	@RequestMapping(value = { "/uk/list"}, method = RequestMethod.GET)
	public String viewListUk(Model model) {

		model.addAttribute("poneyList",poneyDao.getPoneyList() );

		return "uk/poneyViewList";
	}
	
	@RequestMapping(value = { "/uk/view"}, method = RequestMethod.GET)
	public String viewUk(Model model) {

		model.addAttribute("myPoney",poneyDao.getRandomPoney() );

		return "uk/poneyView";
	}
	
	@RequestMapping(value = { "/uk"}, method = RequestMethod.GET)
	public String indexUk(Model model) {

		model.addAttribute("message", message);
		model.addAttribute("messageLocal", messageLocal);

		return "uk/index";
	}

//	@RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
//	public String personList(Model model) {
//
//		model.addAttribute("persons", persons);
//
//		return "personList";
//	}
//
//	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
//	public String showAddPersonPage(Model model) {
//
//		PersonForm personForm = new PersonForm();
//		model.addAttribute("personForm", personForm);
//
//		return "addPerson";
//	}
//
//	@RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
//	public String savePerson(Model model, //
//			@ModelAttribute("personForm") PersonForm personForm) {
//
//		String firstName = personForm.getFirstName();
//		String lastName = personForm.getLastName();
//
//		if (firstName != null && firstName.length() > 0 //
//				&& lastName != null && lastName.length() > 0) {
//			Person newPerson = new Person(firstName, lastName);
//			persons.add(newPerson);
//
//			return "redirect:/personList";
//		}
//
//		model.addAttribute("errorMessage", errorMessage);
//		return "addPerson";
//	}

}
