package com.paymybuddy.sylvain.controller;

import com.paymybuddy.sylvain.dto.InternalTransferDto;
import com.paymybuddy.sylvain.exception.DataNotExisteException;
import com.paymybuddy.sylvain.exception.DataNotFoundException;
import com.paymybuddy.sylvain.service.TransferService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class InternalTransferController {

    @Autowired
    private TransferService transferService;

    @Autowired
    private UserService userService;



    @GetMapping("/transfer")
    public String internalTransferPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("transfers", transferService.findInternalTransferByUser(userDetails.getUsername()));
        model.addAttribute("relations", userService.listEmailRelation(userDetails.getUsername()));
        model.addAttribute("internalTransfer", new InternalTransferDto());

        return "internalTransfer";
    }

    @PostMapping("/transfer/doInternalTransfer")
    public String doInternalTransfer(@ModelAttribute InternalTransferDto internalTransferDto, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes ) {

        internalTransferDto.setEmailSender(userDetails.getUsername());
        try{
            transferService.doInternalTransfer(internalTransferDto);
        } catch (DataNotExisteException | DataNotFoundException e){
            redirectAttributes.addFlashAttribute("errors", List.of(e.getMessage()));
        }
        return "redirect:/user/transfer";
    }

}

