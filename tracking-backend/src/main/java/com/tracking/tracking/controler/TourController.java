package com.tracking.tracking.controler;

import com.tracking.tracking.entity.Tour;
import com.tracking.tracking.repository.TourRepository;
import com.tracking.tracking.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourRepository tourRepository;

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Tour> registerTour(@NonNull @RequestBody Tour tour, Authentication authentication) {
        return ResponseEntity.ok(tourService.createTour(tour, authentication.getName()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Tour>> showToursByUser(Authentication authentication) {
        List<Tour> tourList = tourRepository.showToursByUser(authentication.getName());
        return ResponseEntity.ok(tourList);
    }
}
