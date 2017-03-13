package com.account.monitor.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public ModelAndView root(){
        return new ModelAndView("redirect:/account/dashboard");
    }

}
