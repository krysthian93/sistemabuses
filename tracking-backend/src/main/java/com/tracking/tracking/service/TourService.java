package com.tracking.tracking.service;

import com.tracking.tracking.entity.Tour;
import com.tracking.tracking.entity.User;
import com.tracking.tracking.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tourService")
public class TourService {

    @Autowired
    private UserService userService;

    @Autowired
    private TourRepository tourRepository;

    public Tour createTour(Tour tour, String username) {
        User user = userService.getByUsername(username);
        tour.setUser(user);

        tour.setTimeTravel(tour.getTimeFinish().getTime() - tour.getTimeStart().getTime());

        return tourRepository.save(tour);
    }
}
