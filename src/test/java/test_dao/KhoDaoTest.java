package test_dao;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import salon.Dao.KhoDao;
import salon.entity.Kho;

import java.util.List;

public class KhoDaoTest {

    private KhoDao khoDao;
    Kho kho;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        khoDao = new KhoDao();
    }

    @BeforeTest
    public void init() {
        kho = new Kho();
    }

    @AfterTest
    public void finish() {
        kho = null;
    }

    @Test
    public void testInsert() {
        kho = new Kho("CH01", "SP02", 100);
        khoDao.insert(kho);
        List<Kho> insertedKho = khoDao.selectByMaCuaHang("CH01");
        AssertJUnit.assertNotNull(insertedKho);
        AssertJUnit.assertTrue(insertedKho.size() > 0);
        AssertJUnit.assertEquals(insertedKho.get(0).getMaSanPham(), "SP02");
    }

    @Test
    public void testUpdate() {
        kho = new Kho("CH01", "SP01",150);
        khoDao.update(kho);
        List<Kho> updatedKho = khoDao.selectByMaCuaHang("CH01");
        AssertJUnit.assertNotNull(updatedKho);
        AssertJUnit.assertTrue(updatedKho.size() > 0);
        Kho SP01 = null;
        for (Kho kho : updatedKho) {
            if ("SP01".equals(kho.getMaSanPham())) {
                SP01 = kho;
                break;
            }  //
    }
    }
    @Test
    public void testDelete() {
        kho = new Kho("CH01", "SP01", 0);
        khoDao.delete(kho);
        List<Kho> deletedKho = khoDao.selectByMaCuaHang("CH01");
        AssertJUnit.assertTrue(deletedKho.stream().noneMatch(k -> k.getMaSanPham().equals("SP01")));
    }

    @Test
    public void testSelectAll() {
        List<Kho> list = khoDao.selectAll();
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById() {
        Kho kho = khoDao.selectById(2);
        AssertJUnit.assertNotNull(kho);
        AssertJUnit.assertEquals((int) kho.getId(), 2);  // Chỉ rõ kiểu dữ liệu
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from kho where masanpham=?";
        List<Kho> list = khoDao.selectBySql(sql, "SP01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals(list.get(0).getMaSanPham(), "SP01");
    }

    @Test
    public void testSelectByMaCuaHang() {
        List<Kho> list = khoDao.selectByMaCuaHang("CH01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals(list.get(0).getMaCuaHang(), "CH01");
    }
}
