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

import salon.Dao.ChiTietPhieuXuatDao;
import salon.entity.ChiTietPhieuXuat;

public class ChiTietPhieuXuatTest {
	static ChiTietPhieuXuatDao phieuDao;
	ChiTietPhieuXuat phieu;

	@BeforeMethod
	@BeforeClass
    public static void setUp() {
        phieuDao = new ChiTietPhieuXuatDao();
    }

    @BeforeMethod
    public void init() {
    	phieu = new ChiTietPhieuXuat();
    }

    @AfterMethod
    public void finish() {
    	phieu = null;
    }

	@Test
	public void testSelectAll() {
		List<ChiTietPhieuXuat> list = phieuDao.selectAll();
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectById() {
		phieu = phieuDao.selectById(1);
		AssertJUnit.assertNotNull(phieu);
		AssertJUnit.assertEquals(phieu.getMaPhieuXuat(), 1);
	}
	
	@Test
	public void testSelectByMPX() {
		List<ChiTietPhieuXuat> list = phieuDao.selectByMaphieuxuat(1);
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertEquals(list.get(0).getMaPhieuXuat(), 1);
	}

	@Test
	public void testSelectBySql() {
		String sql = "select * from chitietphieuxuat where maphieuxuat=?";
		List<ChiTietPhieuXuat> list = phieuDao.selectBySql(sql, 1);
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertTrue(list.size() > 0);
		AssertJUnit.assertEquals(list.get(0).getMaPhieuXuat(), 1);
	}

	@Test
	public void testInsert() {
		phieu = new ChiTietPhieuXuat(13, "SP02", 200);
		phieuDao.insert(phieu);
		List<ChiTietPhieuXuat> inserted = phieuDao.selectByMaphieuxuat(13);
		AssertJUnit.assertNotNull(inserted);
		AssertJUnit.assertEquals(inserted.get(0).getMaSanPham(), "SP02");
	}
	
	@Test
	public void testDeleteByMPX() {
		phieuDao.deleteBympx(13);
		List<ChiTietPhieuXuat> deleted = phieuDao.selectByMaphieuxuat(13);
		AssertJUnit.assertEquals(deleted.size(),0);
	}
}
