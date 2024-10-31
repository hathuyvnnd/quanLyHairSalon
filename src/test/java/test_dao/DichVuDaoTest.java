package test_dao;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import salon.Dao.DichVuDao;
import salon.entity.DichVu;

import java.util.List;

public class DichVuDaoTest {

    private DichVuDao dichVuDao;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        dichVuDao = new DichVuDao();
    }

    @Test
    public void testInsert() {
        DichVu dv = new DichVu("DV11", "Hair Cut", 100, "Basic hair cut", true);
        dichVuDao.insert(dv);
        DichVu insertedDv = dichVuDao.selectById("DV11");
        Assert.assertNotNull(insertedDv);
        Assert.assertEquals(insertedDv.getMaDichVu(), "DV11");
    }

    @Test
    public void testUpdate() {
        DichVu dv = new DichVu("DV11", "Hair Cut", 150, "Nhuộm", false);
        dichVuDao.update(dv);
        DichVu updatedDv = dichVuDao.selectById("DV11");
        Assert.assertNotNull(updatedDv);
        Assert.assertEquals(updatedDv.getGiaDichVu(), 150);
        Assert.assertEquals(updatedDv.getMoTa(), "Advanced hair cut");
    }
    @Test
    public void testUpdate1() {
        DichVu dv = new DichVu("DV111", "Hair Cut", 150, "Nhuộm", false);
        dichVuDao.update(dv);
        DichVu updatedDv = dichVuDao.selectById("DV11");
        Assert.assertNotNull(updatedDv);
        Assert.assertEquals(updatedDv.getGiaDichVu(), 150);
        Assert.assertEquals(updatedDv.getMoTa(), "Advanced hair cut");
    }

    @Test
    public void testSelectAll() {
        List<DichVu> list = dichVuDao.selectAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0, "The list should not be empty.");
        for (DichVu dv : list) {
            Assert.assertNotNull(dv.getMaDichVu(), "MaDichVu should not be null.");
            Assert.assertNotNull(dv.getTenDichVu(), "TenDichVu should not be null.");
            Assert.assertTrue(dv.getGiaDichVu() > 0, "GiaDichVu should be greater than 0.");
        }
    }

    @Test
    public void testSelectById() {
        DichVu dv = dichVuDao.selectById("DV11");
        Assert.assertNotNull(dv);
        Assert.assertEquals(dv.getMaDichVu(), "DV11");
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from DICHVU where madichvu=?";
        List<DichVu> list = dichVuDao.selectBySql(sql, "DV01");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0, "The list should not be empty.");
        DichVu dv = list.get(0);
        Assert.assertEquals(dv.getMaDichVu(), "DV01");
    }

    @Test
    public void testSelectByKeyword() {
        List<DichVu> list = dichVuDao.selectbykeyword("Hair");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0, "The list should not be empty.");
        for (DichVu dv : list) {
            Assert.assertTrue(dv.getTenDichVu().contains("Hair"), "TenDichVu should contain the keyword.");
        }
    }

    @Test
    public void testGetGia() {
        int gia = dichVuDao.getGia("DV01");
        Assert.assertTrue(gia > 0, "GiaDichVu should be greater than 0.");
    }
}
