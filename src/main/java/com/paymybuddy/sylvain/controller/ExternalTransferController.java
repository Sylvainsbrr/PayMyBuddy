package com.paymybuddy.sylvain.controller;

import com.paymybuddy.sylvain.dto.BankAccountDto;
import com.paymybuddy.sylvain.dto.ExternalTransferDto;
import com.paymybuddy.sylvain.exception.DataAlreadyExistException;
import com.paymybuddy.sylvain.exception.DataMissingException;
import com.paymybuddy.sylvain.model.BankAccount;
import com.paymybuddy.sylvain.service.BankAccountService;
import com.paymybuddy.sylvain.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class ExternalTransferController {

    @Autowired
    private TransferService transferService;
    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/extransfer")
    public String externalTransferPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("externalTransfers", transferService.findExternalTransferByUser(userDetails.getUsername()));
        model.addAttribute("externalTransfer", new ExternalTransferDto());
        model.addAttribute("listBankAccount",bankAccountService.findBankAccountByUser(userDetails.getUsername()));
        model.addAttribute("bankAccount", new BankAccount());
        return "extransfer";
    }

    @PostMapping("/extransfer/doExternalTransfer")
    public String doExternalTransfert(@ModelAttribute ExternalTransferDto externalTransferDto, @AuthenticationPrincipal UserDetails userDetails) {
        externalTransferDto.setEmailUser(userDetails.getUsername());
        transferService.doExternalTransfer(externalTransferDto);
        return "redirect:/user/extransfer";
    }

    @PostMapping("/extransfer/addBankAccount")
    public String addBankAccount(@ModelAttribute BankAccountDto bankAccountDto, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes){
        try {
            bankAccountService.addBankAccount(userDetails.getUsername(), bankAccountDto);
        } catch (DataAlreadyExistException | DataMissingException | SQLException e){
            redirectAttributes.addFlashAttribute("errors", List.of(e.getMessage()));
        }
        return "redirect:/user/extransfer";
    }

    @PostMapping("/extransfer/deleteBankAccount")
    public String deteleBankAccount(@RequestParam String iban){
        bankAccountService.deleteBankAccount(iban);
        return "redirect:/user/extransfer";
    }
}

