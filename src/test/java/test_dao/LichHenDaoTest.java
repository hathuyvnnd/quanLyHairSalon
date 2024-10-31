package test_dao;


import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import salon.Dao.LichHenDao;
import salon.entity.LichHen;

import java.util.Date;
import java.util.List;

public class LichHenDaoTest {

    private LichHenDao lichHenDao;
    LichHen lichHen;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        lichHenDao = new LichHenDao();
    }

    @BeforeTest
    public void init() {
        lichHen = new LichHen();
    }

    @AfterTest
    public void finish() {
        lichHen = null;
    }

    @Test
    public void testInsert() {
        lichHen = new LichHen(1, "0123456789", new Date(), "10:00", "NV01", "Ghi chú", true);
        lichHenDao.insert(lichHen);
        LichHen insertedLichHen = lichHenDao.selectById(1);
        AssertJUnit.assertNotNull(insertedLichHen);
        AssertJUnit.assertEquals((int) insertedLichHen.getMaLichHen(), 1);
    }

    @Test
    public void testUpdate() {
        lichHen = new LichHen(1, "0123456789", new Date(), "11:00", "NV02", "Ghi chú mới", false);
        lichHenDao.update(lichHen);
        LichHen updatedLichHen = lichHenDao.selectById(1);
        AssertJUnit.assertNotNull(updatedLichHen);
        AssertJUnit.assertEquals(updatedLichHen.getGioHen(), "11:00");
    }

    @Test
    public void testDelete() {
        lichHenDao.delete(1);
        LichHen deletedLichHen = lichHenDao.selectById(1);
        AssertJUnit.assertNull(deletedLichHen);
    }

    @Test
    public void testSelectAll() {
        List<LichHen> list = lichHenDao.selectAll();
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById() {
        LichHen lichHen = lichHenDao.selectById(1);
        AssertJUnit.assertNotNull(lichHen);
        AssertJUnit.assertEquals((int) lichHen.getMaLichHen(), 1);
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from lichhen where maLichHen=?";
        List<LichHen> list = lichHenDao.selectBySql(sql, 1);
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals((int) list.get(0).getMaLichHen(), 1);
    }

  
}
