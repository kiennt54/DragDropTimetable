package com.example.kiennt54.timetable;

import android.content.Context;

import com.example.kiennt54.timetable.adapters.TableAdapter;
import com.example.kiennt54.timetable.commons.Constants;
import com.example.kiennt54.timetable.models.CellData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
public class TableAdapterTest {

    @Mock
    Context mContext;

    @Before
    public void SetUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckHeader_IsHeader_ReturnTrue(){
        ArrayList<CellData> cellDatas;
        cellDatas = (ArrayList<CellData>) Constants.createTestCells();
        TableAdapter tableAdapter = new TableAdapter(cellDatas,mContext);
        assertTrue(tableAdapter.checkHeader(2));
    }

    @Test
    public void testCheckHeader_IsHeader_ReturnFalse(){
        ArrayList<CellData> cellDatas;
        cellDatas = (ArrayList<CellData>) Constants.createTestCells();
        TableAdapter tableAdapter = new TableAdapter(cellDatas,mContext);
        assertFalse(tableAdapter.checkHeader(9));
    }

    @Test
    public void testTotalCell(){
        ArrayList<CellData> cellDatas;
        cellDatas = (ArrayList<CellData>) Constants.createTestCells();
        TableAdapter tableAdapter = new TableAdapter(cellDatas,mContext);
        assertEquals(49,tableAdapter.getItemCount());
    }

}
