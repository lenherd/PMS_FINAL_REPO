/*
 * EventController Class contains the various methods like 
 * Insert month to view event details
 * 
 * @author Adarsh
 * 
 * */
package org.crce.interns.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.crce.interns.beans.Event_detailsBean;
import org.crce.interns.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventController {
	Integer months;
	
	@Autowired
	private EventService eventService;

	@RequestMapping(value = "/InsertMonth", method = RequestMethod.GET)//Call to jsp to get the month number
	public ModelAndView insertMonth(@ModelAttribute("command") Event_detailsBean edBean,BindingResult result) {
		return new ModelAndView("insertMonth");
	}

	@RequestMapping(value = "/SubmitMonth", method = RequestMethod.POST)
	public ModelAndView submitMonth(@RequestParam("month") Integer month) {
		System.out.println("Month sent from front end :" + month);
		months = month;
		return new ModelAndView("redirect:/ViewEvents");
	}

	@RequestMapping(value="/ViewEvents", method = RequestMethod.GET)//View event details
	public ModelAndView viewEvents() {
		System.out.println("In View Events: " + months);
/*Map<String, Object> modelMap = new HashMap<String, Object>();
		
		// get all events acc to month
		List<Event_detailsBean> eventList = eventService.viewEvents(months);
		modelMap.put("events", eventList);
		
		//List<String> companyNames = new ArrayList<>();
		//key of map is integer if id is int, else String ok?!
		Map<Integer,String> companyMap = new HashMap<Integer,String>();
		
		for (Event_detailsBean i : eventList) {
			//i.getCompany_id();write a service to get company name from company id
			//String companyName = null;
			//companyNames.add(eventService.getCompanyName(i.getCompany_id()));
			
			companyMap.put(i.getCompany_id(), eventService.getCompanyName(i.getCompany_id()));
			
		}
		System.out.println("###########################################"+companyMap.size());
		//modelMap.put("companyNames", companyNames);
		modelMap.put("companyMap", companyMap);*/
		
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Event_detailsBean> eventList = eventService.viewEvents(months);
		modelMap.put("events", eventList);
		List<String> companyNames = new ArrayList<>();
		for (Event_detailsBean i : eventList) {
			//i.getCompany_id();write a service to get company name from company id
			String companyName = null;
			companyNames.add(eventService.getCompanyName(i.getCompany_id()));
			
		}
		System.out.println("###########################################"+companyNames.size());
		modelMap.put("companyNames", companyNames);
		if (modelMap.isEmpty()) {
			System.out.println("Error no Model map, Model map is null");
			return new ModelAndView("403");
		}
		if(modelMap.containsValue(null))//If no events in the selected month display this
			{
			System.out.println("No events in selected month");
			return new ModelAndView("noEvents");
			}

		return new ModelAndView("viewEvents", modelMap);
	}


}
