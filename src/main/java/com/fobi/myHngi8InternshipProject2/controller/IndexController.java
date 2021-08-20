/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fobi.myHngi8InternshipProject2.controller;

import com.fobi.myHngi8InternshipProject2.entity.User;
import com.fobi.myHngi8InternshipProject2.model.ContactForm;
import com.fobi.myHngi8InternshipProject2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author USER
 */
@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping(path = "/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping(path = "/download_resume")
    public void downloadResume(HttpServletResponse response) throws IOException {
        File file = new File("C:\\Users\\USER\\Desktop\\myHngi8InternshipProject-2\\src\\main\\resources\\files\\Resume.pdf");

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + file.getName();

        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        byte[] buffer = new byte[8192]; //8kb buffer
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outputStream.close();
    }

    // method to submit form
    @PostMapping(path = "/index")
    public String submitForm(ContactForm contactForm, RedirectAttributes redirAttrs){
        
        User user = null;
        if (contactForm != null){
            user = new User(contactForm.getName(),contactForm.getEmailId(),contactForm.getMessage());
            userRepo.save(user);
            redirAttrs.addFlashAttribute("success", "sent");
            return "redirect:/index";

        }else {
            redirAttrs.addFlashAttribute("null", "Please fill form");
            return "redirect:/index";
        }

    }
}
