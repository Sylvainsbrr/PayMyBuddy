package com.paymybuddy.sylvain.controller;
import com.paymybuddy.sylvain.dto.BuddyFormDto;
import com.paymybuddy.sylvain.exception.DataAlreadyExistException;
import com.paymybuddy.sylvain.exception.DataNotFoundException;
import com.paymybuddy.sylvain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/user")
public class RelationController {
    @Autowired
    private UserService userService;

    @GetMapping("/relation")
    public String relation(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("relations", userService.listEmailRelation(userDetails.getUsername()));
        return "relation";
    }

    @PostMapping("/addBuddy")
    public String addBuddy(@ModelAttribute("buddy") BuddyFormDto buddyFormDto,
                           @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {

        buddyFormDto.setOwner(userDetails.getUsername());
        try {
            userService.addBuddy(buddyFormDto);
        } catch (DataAlreadyExistException | DataNotFoundException e) {
            redirectAttributes.addFlashAttribute("errors", List.of(e.getMessage()));
            return "redirect:/user/relation";
        }

        return "redirect:/user/relation?success";
    }

    @PostMapping("/relation")
    public String deleteBuddy(@RequestParam Integer id) {
        userService.deleteBuddy(id);
        return "redirect:/user/relation";
    }
}
