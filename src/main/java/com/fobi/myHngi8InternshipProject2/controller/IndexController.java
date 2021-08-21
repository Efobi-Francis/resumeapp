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
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 *
 * @author USER
 */
@Controller
public class IndexController {
    String folderPath = "src/main/resources/files/";

    @Autowired
    private UserRepository userRepo;

    @GetMapping(path = "/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/")
    public String downloadFile(Model model) {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        model.addAttribute("files", listOfFiles);
        return "index";
    }

    @RequestMapping("/file/{fileName}")
    @ResponseBody
    public void show(@PathVariable("fileName") String fileName, HttpServletResponse response){

        if(fileName.indexOf(".pdf")>-1) response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setHeader("content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath+fileName);
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf))>0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
