package com.taxah.springdz7.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * The ResourceWebController class is responsible for handling web requests related to resource access and authentication.
 * <p>
 * Endpoints:
 * "/" Root home page returns index view.
 * <p>
 * "/public-data" Handles the request to access public data.
 * <p>
 * "/private-data" Handles the request to access private data.
 * <p>
 * "/login" Handles the request to access the login page.
 * <p>
 * "/logout" Handles the request to log out the authenticated user.
 */
@Controller
public class ResourceWebController {

    /**
     * Handles the request to access the home page.
     *
     * @param authenticator An object representing the authentication status.
     * @param model         The model to be populated with data for rendering the view.
     * @return The logical view name for rendering the home page.
     */
    @GetMapping("/")
    public String homePage(Authentication authenticator, Model model) {
        model.addAttribute("username",
                (authenticator != null) ? authenticator.getName() : "Stranger");
        return "index";
    }

    /**
     * Handles the request to access public data.
     *
     * @return The logical view name for rendering public data.
     */
    @GetMapping("/public-data")
    public String getPublicResource() {
        return "public";
    }

    /**
     * Handles the request to access private data.
     *
     * @return The logical view name for rendering private data.
     */
    @GetMapping("/private-data")
    public String getPrivateResource() {
        return "private";
    }

    /**
     * Handles the request to access the login page.
     *
     * @return The logical view name for rendering the login page.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * Handles the request to log out the authenticated user.
     *
     * @return A redirection to the home page after logging out.
     */
    @GetMapping("/logout")
    public String logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/";
    }

}
