package test_dao;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import salon.Dao.PhieuXuatDao;
import salon.entity.PhieuXuat;
import java.util.List;

public class phieuxuatdaotest {

    private PhieuXuatDao phieuXuatDao;
    PhieuXuat px;

    @org.testng.annotations.BeforeClass
    public static void setUpClass() {
        // This will run once before any of the test methods in this class
    }

    @BeforeMethod
	@org.testng.annotations.BeforeClass
    public void setUp() {
        phieuXuatDao = new PhieuXuatDao();
    }

    @org.testng.annotations.BeforeClass
    public void init() {
        px = new PhieuXuat();
    }

    @AfterClass
    public void finish() {
        px = null;
    }

    @Test
	
    public void testInsert() {
        px = new PhieuXuat();
        phieuXuatDao.insert(px);
        PhieuXuat insertedPx = phieuXuatDao.selectById(1); // Assuming the ID is 1 for the inserted record
        Assert.assertNotNull(insertedPx);
    }

    @Test
	
    public void testSelectAll() {
        List<PhieuXuat> list = phieuXuatDao.selectAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById() {
        PhieuXuat px = phieuXuatDao.selectById(1); // Assuming the ID to be tested is 1
        Assert.assertNotNull(px);
    }

    @Test
	
    public void testSelectBySql() {
        String sql = "SELECT * FROM PHIEUXUATHANG WHERE MaPhieuXuat=?";
        List<PhieuXuat> list = phieuXuatDao.selectBySql(sql, 1); // Assuming the ID to be tested is 1
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

 
}
