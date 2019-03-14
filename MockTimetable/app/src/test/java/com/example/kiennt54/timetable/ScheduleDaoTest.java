package com.example.kiennt54.timetable;

import com.example.kiennt54.timetable.activities.MainActivity;
import com.example.kiennt54.timetable.commons.Constants;
import com.example.kiennt54.timetable.dao.ScheduleDao;
import com.example.kiennt54.timetable.models.CellData;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleDaoTest {

    @Test
    public void testInsertListSubject_Success(){
        MainActivity activity = Mockito.mock(MainActivity.class);
        ScheduleDao scheduleDao = new ScheduleDao(activity);
        List<CellData> cellDataList;
        cellDataList = Constants.createTestCells();
        assertEquals(1,scheduleDao.insertListSubject(cellDataList));
    }
}
