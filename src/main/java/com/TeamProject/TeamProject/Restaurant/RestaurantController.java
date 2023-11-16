package com.TeamProject.TeamProject.Restaurant;

import com.TeamProject.TeamProject.Restaurant.Restaurant;
import com.TeamProject.TeamProject.Restaurant.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Restaurant> paging = this.restaurantService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "Restaurant_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Restaurant restaurant = this.restaurantService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurant_detail";
    }
}
