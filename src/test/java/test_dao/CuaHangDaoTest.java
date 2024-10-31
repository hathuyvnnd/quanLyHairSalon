package test_dao;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import salon.Dao.CuaHangDao;
import salon.entity.CuaHang;

public class CuaHangDaoTest {
	private CuaHangDao cuaHangDao;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        cuaHangDao = new CuaHangDao();
    }

    @Test
    public void testSelectAll() {
        List<CuaHang> list = cuaHangDao.selectAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0, "The list should not be empty.");
        for (CuaHang cuaHang : list) {
            Assert.assertNotNull(cuaHang.getMaCuaHang(), "MaCuaHang should not be null.");
            Assert.assertNotNull(cuaHang.getTenCuaHang(), "TenCuaHang should not be null.");
            Assert.assertNotNull(cuaHang.getDiaChi(), "DiaChi should not be null.");
            Assert.assertNotNull(cuaHang.getSoDienThoaiCH(), "SoDienThoai should not be null.");
        }
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from CUAHANG where MaCuaHang=?";
        List<CuaHang> list = cuaHangDao.selectBySql(sql, "CH01");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0, "The list should not be empty.");
        CuaHang cuaHang = list.get(0);
        Assert.assertEquals(cuaHang.getMaCuaHang(), "CH01", "MaCuaHang should match the query parameter.");
        Assert.assertNotNull(cuaHang.getTenCuaHang(), "TenCuaHang should not be null.");
        Assert.assertNotNull(cuaHang.getDiaChi(), "DiaChi should not be null.");
        Assert.assertNotNull(cuaHang.getSoDienThoaiCH(), "SoDienThoai should not be null.");
    }
}
