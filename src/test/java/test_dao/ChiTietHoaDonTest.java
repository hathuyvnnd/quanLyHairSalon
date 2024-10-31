package test_dao;



import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertNotEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import salon.Dao.ChiTietHoaDonDao;
import salon.entity.ChiTietHoaDon;

public class ChiTietHoaDonTest {
	static ChiTietHoaDonDao cthdDao;
	ChiTietHoaDon cthd;

	@BeforeMethod
	@BeforeClass
    public static void setUp() {
        cthdDao = new ChiTietHoaDonDao();
    }

    @BeforeMethod
    public void init() {
        cthd = new ChiTietHoaDon();
    }

    @AfterMethod
    public void finish() {
        cthd = null;
    }

    @Test
	public void testSelectAll() {
		List<ChiTietHoaDon> list = cthdDao.selectAll();
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertTrue(list.size() > 0);
	}

	@Test
	public void testSelectBySql() {
		String sql = "select * from chitiethoadon where mahoadon=?";
		List<ChiTietHoaDon> list = cthdDao.selectBySql(sql, 1);
		AssertJUnit.assertNotNull(list);
		AssertJUnit.assertTrue(list.size() > 0);
		AssertJUnit.assertEquals(list.get(0).getMaHoaDon(), 1);
	}
	
	@Test
	public void testSelectById() {
		ChiTietHoaDon cthd = cthdDao.selectById(1);
		AssertJUnit.assertNotNull(cthd);
		AssertJUnit.assertEquals(cthd.getId(), 1);
	}

	@Test
	public void testInsert() {
		String sql = "select * from chitiethoadon";
		List<ChiTietHoaDon> list1 = cthdDao.selectAll();
		cthd = new ChiTietHoaDon(0 , 1, "DV02", 2);
		cthdDao.insert(cthd);
		List<ChiTietHoaDon> list2 = cthdDao.selectAll();
		assertNotEquals(list1.size(), list2.size());
	}

	
	@Test
	public void testDelete() {
		cthdDao.delete(100);
		ChiTietHoaDon deleted = cthdDao.selectById(100);
		AssertJUnit.assertNull(deleted);
	}
}
