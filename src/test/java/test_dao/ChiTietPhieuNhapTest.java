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

import salon.Dao.ChiTietPhieuNhapDao;
import salon.entity.ChiTietPhieuNhap;

public class ChiTietPhieuNhapTest {
	static ChiTietPhieuNhapDao phieuDao;
	ChiTietPhieuNhap phieu;

	@BeforeMethod
	@BeforeClass
    public static void setUp() {
        phieuDao = new ChiTietPhieuNhapDao();
    }

    @BeforeMethod
    public void init() {
    	phieu = new ChiTietPhieuNhap();
    }

    @AfterMethod
    public void finish() {
    	phieu = null;
    }

	@Test
	public void testSelectAll() {
		List<ChiTietPhieuNhap> list = phieuDao.selectAll();
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectById() {
		phieu = phieuDao.selectById(1);
		AssertJUnit.assertNotNull(phieu);
		AssertJUnit.assertEquals(phieu.getMaPhieuNhap(), 1);
	}
	
	@Test
	public void testSelectByMPN() {
		List<ChiTietPhieuNhap> list = phieuDao.selectByMaphieunhap(1);
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertEquals(list.get(0).getMaPhieuNhap(), 1);
	}

	@Test
	public void testSelectBySql() {
		String sql = "select * from chitietphieunhap where maphieunhap=?";
		List<ChiTietPhieuNhap> list = phieuDao.selectBySql(sql, 1);
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertTrue(list.size() > 0);
		AssertJUnit.assertEquals(list.get(0).getMaPhieuNhap(), 1);
	}

	@Test
	public void testInsert() {
		phieu = new ChiTietPhieuNhap(9, "SP02", 2);
		phieuDao.insert(phieu);
		List<ChiTietPhieuNhap> inserted = phieuDao.selectByMaphieunhap(9);
		AssertJUnit.assertNotNull(inserted);
		AssertJUnit.assertEquals(inserted.get(0).getMaSanPham(), "SP02");
	}
	
	@Test
	public void testDeleteByMPN() {
		phieuDao.deleteBympn(9);
		List<ChiTietPhieuNhap> deleted = phieuDao.selectByMaphieunhap(9);
		AssertJUnit.assertEquals(deleted.size(),0);
	}
}
