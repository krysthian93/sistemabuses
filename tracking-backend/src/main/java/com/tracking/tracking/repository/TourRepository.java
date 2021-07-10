package com.tracking.tracking.repository;

import com.tracking.tracking.entity.Tour;
import com.tracking.tracking.pojo.IDistanceBetween;
import com.tracking.tracking.pojo.ITimeTraveled;
import com.tracking.tracking.pojo.ITourPerMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.stereotype.Repository;

import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Repository("manageTour")
public interface TourRepository extends JpaRepository<Tour, Serializable> {

    @Query(value = "SELECT t from Tour t WHERE t.user.username = :username")
    List<Tour> showToursByUser(String username);

    @Query(value = "SELECT t from Tour t WHERE t.user.username = :username AND t.timeFinish BETWEEN :fromDate AND :toDate")
    List<Tour> showToursByRangeDate(String username,
                                    @Temporal(TemporalType.TIMESTAMP) Date fromDate,
                                    @Temporal(TemporalType.TIMESTAMP) Date toDate);

    @Query(value = "SELECT FUNCTION('MONTH', t.timeFinish) as month, SUM(t.distanceBetween) as distanceBetween from Tour t WHERE FUNCTION('YEAR', t.timeFinish) = :year GROUP BY month")
    List<IDistanceBetween> getDistanceBetween(Integer year);

    @Query(value = "SELECT FUNCTION('MONTH', t.timeFinish) as month, COUNT(t.timeFinish) as tourPerMonth FROM Tour t WHERE FUNCTION('YEAR', t.timeFinish) = :year GROUP BY month")
    List<ITourPerMonth> getTourPerMonth(Integer year);


    @Query(value = "SELECT FUNCTION('MONTH', t.timeFinish) as month, SUM(t.timeTravel) as timeTraveled FROM Tour t WHERE FUNCTION('YEAR', t.timeFinish) = :year GROUP BY month")
    List<ITimeTraveled> getTimeTraveled(Integer year);

}
