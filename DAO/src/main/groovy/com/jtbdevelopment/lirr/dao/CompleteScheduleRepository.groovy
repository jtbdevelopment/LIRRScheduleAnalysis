package com.jtbdevelopment.lirr.dao

import com.jtbdevelopment.lirr.dataobjects.schedule.CompleteSchedule
import org.joda.time.LocalDate
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

/**
 * Date: 2/23/14
 * Time: 6:07 PM
 */
@Repository
public interface CompleteScheduleRepository extends PagingAndSortingRepository<CompleteSchedule, String> {
    List<CompleteSchedule> findByStartAndEnd(final LocalDate start, final LocalDate end)
}