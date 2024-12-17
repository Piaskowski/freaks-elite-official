package com.freakselite.controller;

import com.freakselite.model.MailMessage;
import com.freakselite.service.MailService;
import com.freakselite.util.PageMappings;
import com.freakselite.util.StaticPath;
import com.freakselite.util.ViewNames;
import jakarta.validation.Valid;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Setter
@Controller
public class ContactController {

    // == fields ==
    private MailService mailService;
    private final Path riderPath = Paths.get(StaticPath.RIDER).toAbsolutePath().normalize();

    // == constructors ==
    @Autowired
    public ContactController(MailService mailService) {
        this.mailService = mailService;
    }

    // == public methods ==
    @GetMapping(PageMappings.CONTACT)
    public String getContact(@AuthenticationPrincipal UserDetails userDetails, Model model){
        MailMessage mail = new MailMessage();
        model.addAttribute("mail", mail);

        boolean isAdmin = false;

        if (userDetails != null){
            isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);

        return ViewNames.CONTACT;
    }

    @PostMapping(PageMappings.CONTACT)
    public String sendEmail(@Valid @ModelAttribute("mail") MailMessage mail, BindingResult result){

        if (result.hasErrors()){
            return ViewNames.CONTACT;
        }

        mailService.sendMessage(mail);

        return "redirect:/kontakt";
    }

    @GetMapping("download/rider")
    public ResponseEntity<Resource> downloadRider(){
        try {

            Resource resource = new UrlResource(riderPath.toUri());

            log.info(resource.getFilename());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
