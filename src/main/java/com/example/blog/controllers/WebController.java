/********author--> Simi********/
package com.example.blog.controllers;




import com.example.blog.helper.Response;
import com.example.blog.payload.requests.AuthRequest;
import com.example.blog.payload.requests.FaqRequest;
import com.example.blog.payload.requests.RegisterRequest;
import com.example.blog.payload.responses.LoginResponse;
import com.example.blog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired; // Import this for constructor injection

import com.example.blog.services.FaqService;
import com.example.blog.payload.FaqResponse;

@Controller
public class WebController {

    // Declare a private field for the FaqService instance
    private final FaqService faqService;
    private final AuthService authService; // Inject AuthService for login functionality
    //private final UserService userService; // Inject UserService for sign-up functionality

    // Constructor injection: Spring will inject the FaqService instance here
    @Autowired
    public WebController(FaqService faqService, AuthService authService) {
        this.faqService = faqService;  // Assigning the injected FaqService to the field
        this.authService = authService; // Assign AuthService to the field
        //this.userService = userService;  // Assigning the injected UserService to the field
    }


    /*@GetMapping("/home")
    public String homePage() {
        return "index";  // This will look for index.html in the templates folder
    }*/

    @GetMapping("/home")
    public String homePage() {
        return "home";  // This will look for home.html in the templates folder
    }

    @GetMapping("/create-faq")
    public String createFaqPage() {
        return "createFAQ";  // This will look for createFAQ.html in the templates folder
    }

    @PostMapping("/create-faq")
    public ResponseEntity<Response<FaqResponse>> createFaq(@RequestBody @Valid FaqRequest faqRequest, BindingResult bindingResult) {
        // Call the service to handle the FAQ creation logic
        return faqService.createFaq(faqRequest, bindingResult);
    }

    // New login page
    /*@GetMapping("/auth/authenticate")
    public String loginPage() {
        return "login";  // This will look for login.html in the templates folder
    }*/

    // New login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // This will look for login.html in the templates folder
    }

    // Handle login POST request
    /*@PostMapping("/auth/authenticate")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest, BindingResult bindingResult) {
        // Call the AuthService to authenticate the user
        return authService.authenticate(authRequest, bindingResult);
    }*/


    // User Blog List page
    @GetMapping("/userBlogList")
    public String userBlogList() {
        return "userBlogList";  // This will look for userBlogList.html in the templates folder
    }

    //Sign-up page
    @GetMapping("/sign-up")
    public String signUpPage() {
        return "signup";  // This will look for logout.html in the templates folder
    }

    // Handle POST request for Sign-up
    /*@PostMapping("/auth/registration")
    public ResponseEntity<?> register(RegisterRequest request, BindingResult bindingResult)
    {
        // Call the AuthService to register the user
        return authService.register(request,bindingResult);
    }*/

    //About Us page
    @GetMapping("/about-us")
    public String aboutUsPage() {
        //System.out.println("About Us Page POST request received");
        return "aboutUs";  // This will look for aboutUs.html in the templates folder
    }

    //Contact US page
    @GetMapping("/contact-us")
    public String contactUsPage() {
        return "contactUs";  // This will look for contactUs.html in the templates folder
    }

    //FAQ page
    @GetMapping("/faq")
    public String faqPage() {
        return "faq";  // This will look for faq.html in the templates folder
    }

    //Log out page
    @GetMapping("/log-out")
    public String logoutPage() {
        return "logout";  // This will look for logout.html in the templates folder
    }

    //Manage FAQ page
    @GetMapping("/manage-faq")
    public String manageFAQPage() {
        return "manageFAQ";  // This will look for manageFAQ.html in the templates folder
    }

    //User Profile page
    @GetMapping("/user-profile")
    public String userProfilePage() {
        return "userProfile";  // This will look for logout.html in the templates folder
    }

    //Create New Post page
    @GetMapping("/create-blogpost")
    public String createBlogPostPage() {
        return "createBlogPost";  // This will look for createBlogPost.html in the templates folder
    }

    /*@PostMapping("/contact-us")
    public ResponseEntity<Response<String>> submitContactForm(@RequestBody @Valid ContactRequest contactRequest, BindingResult bindingResult) {
        // Call the ContactService to handle the contact form submission
        return contactService.handleContactRequest(contactRequest, bindingResult);
    }*/


    // Sign-up page
    /*@GetMapping("/signup")
    public String signupPage() {
        return "signUp";  // This will look for signup.html in the templates folder
    }

    // Handle sign-up POST request
    @PostMapping("/signup")
    public ResponseEntity<Response<UserResponse>> signup(@RequestBody @Valid SignupRequest signupRequest, BindingResult bindingResult) {
        // Call the UserService to handle the user sign-up logic
        return userService.createUser(signupRequest, bindingResult);
    }


    @GetMapping("/admin")
    public String adminPage() {
        return "admin"; // This will look for admin.html in the templates folder
    }

    @PostMapping("/admin/manage-user")
    public ResponseEntity<Response<String>> manageUser(@RequestBody @Valid AdminActionRequest adminActionRequest, BindingResult bindingResult) {
        return adminService.manageUser(adminActionRequest, bindingResult); // Call the service to handle user management
    }*/

}
