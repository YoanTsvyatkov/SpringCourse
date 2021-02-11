package com.example.recipes.web;

import com.example.recipes.exception.UnauthorisedModificationException;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.Role;
import com.example.recipes.model.User;
import com.example.recipes.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.regex.Pattern;

@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @Value("${uploads.directory}")
    private String uploadsDir;


    @GetMapping("/users")
    String getUsers(Model model,
                    Authentication auth) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("path", "users");

        return "users";
    }

    @GetMapping("/user-form")
    ModelAndView getUserForm(
            @ModelAttribute("user") User user,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "userId", required = false) Long userId) {
        ModelAndView result = new ModelAndView("user-form");

        if ("edit".equals(mode)) {
            User found = userService.findUserById(userId);
            result.addObject("user", found);
        } else {
            result.addObject("user", new User());
        }

        return result;
    }

    @PostMapping(value = "/users", params = "edit")
    public String editUser(@RequestParam("edit") Long editId,
                           Model model, UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/user-form")
                .query("mode=edit&userId={id}").buildAndExpand(editId).toUri();
        return "redirect:" + uri.toString();
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register-form";
    }

    @PostMapping("/save")
    public String register(@Valid @ModelAttribute User user,
                           BindingResult errors,
                           @RequestParam(value = "file") MultipartFile file,
                           Model model){
        if(errors.hasErrors()){
            return "register-form";
        }

        if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 4) {
            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                handleFile(file, user);
            } else {
                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                return "user-form";
            }
        }

        userService.addUser(user);
        return "redirect:/recipes";
    }

    @PostMapping(value = "/users", params = "delete")
    public String deleteUser(@RequestParam("delete") Long deleteId, Model model) {
        userService.deleteUser(deleteId);

        return "redirect:/users";
    }

    @PostMapping("/user-form")
    String postUser(@Valid @ModelAttribute("user") User user,
                    BindingResult errors,
                    @RequestParam(value = "file") MultipartFile file,
                    Model model) {

        if (errors.hasErrors()) {
            return "user-form";
        }

        if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 4) {
            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                handleFile(file, user);
            } else {
                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                return "user-form";
            }
        }

        if(errors.hasErrors()
                && !(user.getId() != null && "".equals(user.getPassword()) && errors.getFieldErrorCount()==1 && errors.getFieldError("password") != null)) {
            return "user-form";
        }

        if (user.getId() == null) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }

        return "redirect:/users";
    }

    @GetMapping(value = "/403")
    public ModelAndView accesssDenied(Principal user) {

        ModelAndView model = new ModelAndView();

        model.addObject("message", "Access denied");
        model.addObject("continueUrl", "/recipes");

        model.setViewName("errors");
        return model;
    }

    private void handleFile(MultipartFile file, User user) {
        String oldName = user.getImage();
        if (oldName != null && oldName.length() > 0) { //delete old image file
            Path oldPath = Paths.get(getUploadsDir(), oldName).toAbsolutePath();
            if (Files.exists(oldPath)) {
                try {
                    Files.delete(oldPath);
                } catch (IOException ex) {
                }
            }
        }
        if (file != null && file.getOriginalFilename().length() > 4) {
            String newName = file.getOriginalFilename();
            Path newPath = Paths.get(getUploadsDir(), newName).toAbsolutePath();
            int n = 0;
            String finalName = newName;
            while (Files.exists(newPath)) {   // change destination file name if it already exists
                finalName = newName.substring(0, newName.length() - 4) + "_" + ++n + newName.substring(newName.length() - 4);
                newPath = Paths.get(getUploadsDir(), finalName).toAbsolutePath();
            }
            try {
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(newPath));
                user.setImage(finalName);
            } catch (IOException ex) {
            }
        }
    }

    protected String getUploadsDir() {
        File uploadsDir = new File(this.uploadsDir);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        return uploadsDir.getAbsolutePath();
    }
}
