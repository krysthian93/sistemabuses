package com.tracking.tracking.controler;

import com.tracking.tracking.entity.Tour;
import com.tracking.tracking.pojo.DashBoardReport;
import com.tracking.tracking.pojo.IDistanceBetween;
import com.tracking.tracking.pojo.ITimeTraveled;
import com.tracking.tracking.pojo.ITourPerMonth;
import com.tracking.tracking.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private TourRepository tourRepository;

    @GetMapping("/tours/range-date")
    public ResponseEntity<List<Tour>> showToursByRangeDate(Authentication authentication,
                                                           @RequestParam String fromDate,
                                                           @RequestParam String toDate) throws ParseException {

        DateFormat completeDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        List<Tour> tourList = tourRepository.showToursByRangeDate(authentication.getName(),
                completeDateFormat.parse(fromDate),
                completeDateFormat.parse(toDate));
        return ResponseEntity.ok(tourList);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashBoardReport> getDashboard() {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

        DashBoardReport dashBoardReport = new DashBoardReport();
        dashBoardReport.setDistanceBetween(tourRepository.getDistanceBetween(currentYear));
        dashBoardReport.setTourPerMonth(tourRepository.getTourPerMonth(currentYear));
        dashBoardReport.setTimeTraveled(tourRepository.getTimeTraveled(currentYear));

        return ResponseEntity.ok(dashBoardReport);
    }

    @GetMapping("/distanceBetween")
    public ResponseEntity<List<IDistanceBetween>> getDistanceBetween(@RequestParam Integer year) {
        return ResponseEntity.ok(tourRepository.getDistanceBetween(year));
    }

    @GetMapping("/tourPerMonth")
    public ResponseEntity<List<ITourPerMonth>> getTourPerMonth(@RequestParam Integer year) {
        return ResponseEntity.ok(tourRepository.getTourPerMonth(year));
    }

    @GetMapping("/timeTraveled")
    public ResponseEntity<List<ITimeTraveled>> getTimeTraveled(@RequestParam Integer year) {
        return ResponseEntity.ok(tourRepository.getTimeTraveled(year));
    }
}
