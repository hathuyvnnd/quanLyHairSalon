package test_dao;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import java.util.List;
import salon.Dao.PhieuNhapDao;
import salon.entity.PhieuNhap;
import java.sql.Date;

public class phieunhapdaotest {

    private PhieuNhapDao phieuNhapDao;

    @org.testng.annotations.BeforeClass
    public static void setUpClass() {
        // This will run once before any of the test methods in this class
    }

    @BeforeMethod
	@org.testng.annotations.BeforeClass
    public void setUp() {
        phieuNhapDao = new PhieuNhapDao();
    }

    @AfterMethod
	@AfterClass
    public void tearDown() {
        phieuNhapDao = null;
    }

    @Test
	
    public void testInsert() {
        PhieuNhap phieuNhap = new PhieuNhap(01, Date.valueOf("2024-01-01"), "NCC001", "CH001");
        phieuNhapDao.insert(phieuNhap);
        List<PhieuNhap> result = phieuNhapDao.selectAll();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.size() > 0);
    }

  
    @Test
	
    public void testUpdate() {
        PhieuNhap phieuNhap = new PhieuNhap(2, Date.valueOf("2024-04-25"), "02", "CH01");
        phieuNhapDao.update(phieuNhap);
        PhieuNhap result = phieuNhapDao.selectById(1);
        Assert.assertNotNull(result);
        Assert.assertEquals(Date.valueOf("2024-04-25"), result.getNgayNhap());
        Assert.assertEquals("02", result.getMaNCC());
        Assert.assertEquals("CH02", result.getMaCH());
        
    }

    @Test
	
    public void testSelectAll() {
        List<PhieuNhap> list = phieuNhapDao.selectAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
	
    public void testSelectById() {
        // Assuming there is a PhieuNhap with maphieunhap 1 for this test
        PhieuNhap result = phieuNhapDao.selectById(1);
        Assert.assertNotNull(result);
    }
}
