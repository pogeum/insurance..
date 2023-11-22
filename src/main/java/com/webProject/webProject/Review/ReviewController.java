package com.webProject.webProject.Review;

import com.webProject.webProject.Store.Store;
import com.webProject.webProject.Store.StoreService;
import com.webProject.webProject.User.User;
import com.webProject.webProject.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/review")
@RequiredArgsConstructor
@Controller
public class ReviewController {
    
}
