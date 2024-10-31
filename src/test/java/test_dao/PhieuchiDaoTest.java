package test_dao;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import salon.Dao.PhieuchiDao;
import salon.entity.Phieuchi;
import java.util.List;
import java.sql.Date;


public class PhieuchiDaoTest {

    private PhieuchiDao phieuchiDao;
    private Phieuchi phieuchi;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        phieuchiDao = new PhieuchiDao();
    }

    @BeforeTest
    public void init() {
        phieuchi = new Phieuchi();
    }

    @AfterTest
    public void finish() {
        phieuchi = null;
    }

    @Test
    public void testInsert() {
        phieuchi = new Phieuchi();
        phieuchi.setMaPhieuChi("PC0111");
        phieuchi.setMaCuaHang("CH01");
        phieuchi.setMaChiPhi("CP01");
        phieuchi.setNgay(Date.valueOf("2024-07-20"));  // Sử dụng Date.valueOf để chuyển đổi từ String thành Date
        phieuchi.setThanhTien(1000000);
        
        phieuchiDao.insert(phieuchi);
        List<Phieuchi> list = phieuchiDao.selectBySql("select * from phieuchi where maphieuchi=?", "PC01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals(list.get(0).getMaPhieuChi(), "PC01");
    }


    @Test
    public void testSelectAll() {
        List<Phieuchi> list = phieuchiDao.selectAll();
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectByCuahang() {
        List<Phieuchi> list = phieuchiDao.selectbycuahang("CH01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertTrue(list.stream().allMatch(pc -> "CH01".equals(pc.getMaCuaHang())));
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from phieuchi where maphieuchi=?";
        List<Phieuchi> list = phieuchiDao.selectBySql(sql, "PC01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals(list.get(0).getMaPhieuChi(), "PC01");
    }
}
