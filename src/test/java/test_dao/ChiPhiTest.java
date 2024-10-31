package test_dao;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import salon.Dao.ChiPhiDao;
import salon.entity.ChiPhi;

public class ChiPhiTest {

    static ChiPhiDao chiphiDao;
    ChiPhi cp;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        chiphiDao = new ChiPhiDao();
    }

    @BeforeMethod
    public void init() {
        cp = new ChiPhi();
    }

    @AfterMethod
    public void finish() {
        cp = null;
    }

    @Test
    public void testSelectAll() {
        List<ChiPhi> list = chiphiDao.selectAll();
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById() {
        ChiPhi cp = chiphiDao.selectById("CP01");
        AssertJUnit.assertNotNull(cp);
        AssertJUnit.assertEquals(cp.getMaChiPhi(), "CP01");
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from LOAICHIPHI where machiphi=?";
        List<ChiPhi> list = chiphiDao.selectBySql(sql, "CP01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals(list.get(0).getMaChiPhi(), "CP01");
    }

    @Test
    public void testInsert() {
        cp = new ChiPhi("CP11", "Tiền điện", "Tiền điện tháng 7");
        chiphiDao.insert(cp);
        ChiPhi insertedCp = chiphiDao.selectById("CP11");
        AssertJUnit.assertNotNull(insertedCp);
        AssertJUnit.assertEquals(insertedCp.getMaChiPhi(), "CP11");
    }

    @Test
    public void testUpdate() {
        cp = new ChiPhi("CP11", "Tiền nước", "Tiền điện tháng 7");
        chiphiDao.update(cp);
        ChiPhi updatedCp = chiphiDao.selectById("CP11");
        AssertJUnit.assertNotNull(updatedCp);
        AssertJUnit.assertEquals(updatedCp.getTenChiPhi(), "Tiền nước");
    }

    @Test
    public void testDelete() {
        chiphiDao.delete("CP11");
        ChiPhi deleted = chiphiDao.selectById("CP11");
        AssertJUnit.assertNull(deleted);
    }
}
