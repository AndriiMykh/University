package org.foxminded.university.dao;

import org.foxminded.university.domain.Page;
import org.foxminded.university.entity.Schedule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Time;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringJUnitConfig(SpringTestConfig.class)
@Sql(scripts = { "classpath:schemaTest.sql", "classpath:data.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScheduleDaoTest {

    @Autowired
    private ScheduleDao scheduleDao;

    @Test
    void createShouldCreateSchedule(){
        int sizeBefore = scheduleDao.findAll().size();
        Schedule schedule = Schedule.builder()
                .withId(4L)
                .withDate(LocalDate.now())
                .withStartTime(new Time(1000))
                .withEndTime(new Time(2000))
                .build();
        scheduleDao.create(schedule);
        assertThat(scheduleDao.findAll())
                .hasSize(sizeBefore+1)
                .contains(schedule);
    }

    @Test
    void findByIdShouldFindSchedule() {
        Schedule schedule =  Schedule.builder()
                .withId(1L)
                .build();
        assertThat(scheduleDao.findById(1L)).hasValue(schedule);
    }

    @Test
    void updateShouldUpdateSchedule() {
        Schedule schedule = Schedule.builder()
                .withId(1L)
                .withDate(LocalDate.now())
                .withStartTime(new Time(1000))
                .withEndTime(new Time(2000))
                .build();
        scheduleDao.update(schedule);
        assertThat(scheduleDao.findById(1L)).hasValue(schedule);
    }

    @Test
    void deleteShouldDeleteSchedule() {
        int sizeBefore = scheduleDao.findAll().size();
        scheduleDao.delete(1L);
        int sizeAfter = scheduleDao.findAll().size();
        assertThat(sizeAfter)
                .isEqualTo(sizeBefore-1);
    }

    @Test
    void findAllShouldFindAllSchedule() {
        Schedule schedule = Schedule.builder()
                .withId(1L)
                .build();
        assertThat(scheduleDao.findAll())
                .isNotEmpty()
                .contains(schedule);
    }

    @Test
    void findAllShouldFindAllSchedulesPageable() {
        Page page = new Page(0,3);
        assertThat(scheduleDao.findAll(page).getItems())
                .hasSize(3);
    }

    @Test
    void insertDatesShouldInsertDates(){
        final long id = 1L;
        int sizeBefore = scheduleDao.getDates(id).size();
        LocalDate localDate = LocalDate.now();
        scheduleDao.insertDates(id, localDate);
        assertThat(scheduleDao.getDates(id)).hasSize(sizeBefore + 1);
    }
}
