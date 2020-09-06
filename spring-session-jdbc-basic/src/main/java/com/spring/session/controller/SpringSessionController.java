package com.spring.session.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/session")
public class SpringSessionController {

    @GetMapping("/display")
    public String process(Model model, HttpSession httpSession) {
        List<String> messages = (List<String>) httpSession.getAttribute("MY_SESSION_ATTRIBUTE");
        if (messages == null) {
            new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        return "index";
    }

    @PostMapping("/persistentMessage")
    public String persistMessage(HttpServletRequest httpServletRequest, @RequestParam("msg") String msg) {
        List<String> messages = (List<String>) httpServletRequest.getSession().getAttribute("MY_SESSION_ATTRIBUTE");

        if (messages == null) {
            messages = new ArrayList<>();
            httpServletRequest.getSession().setAttribute("MY_SESSION_ATTRIBUTE", messages);
        }

        messages.add(msg);
        httpServletRequest.getSession().setAttribute("MY_SESSION_ATTRIBUTE", messages);
        return "redirect:/session/display";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().invalidate();
        return "redirect:/session/display";

    }

}
