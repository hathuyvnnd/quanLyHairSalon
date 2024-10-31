package test_dao;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import salon.Dao.NhaCungCapDao;
import salon.entity.NhaCungCap;

import java.util.List;

public class NhaCungCapDaoTest {

    private NhaCungCapDao nhaCungCapDao;
    NhaCungCap nhaCungCap;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        nhaCungCapDao = new NhaCungCapDao();
    }

    @BeforeTest
    public void init() {
        nhaCungCap = new NhaCungCap();
    }

    @AfterTest
    public void finish() {
        nhaCungCap = null;
    }

    @Test
    public void testInsert() {
        nhaCungCap = new NhaCungCap("NCC01", "Nha Cung Cap 1", "0123456789", "123 Duong ABC", true);
        nhaCungCapDao.insert(nhaCungCap);
        NhaCungCap insertedNhaCungCap = nhaCungCapDao.selectById("NCC01");
        AssertJUnit.assertNotNull(insertedNhaCungCap);
        AssertJUnit.assertEquals(insertedNhaCungCap.getMaNhaCungCap(), "NCC01");
    }

    @Test
    public void testUpdate() {
        nhaCungCap = new NhaCungCap("NCC01", "Nha Cung Cap 2", "0987654321", "456 Duong XYZ", false);
        nhaCungCapDao.update(nhaCungCap);
        NhaCungCap updatedNhaCungCap = nhaCungCapDao.selectById("NCC01");
        AssertJUnit.assertNotNull(updatedNhaCungCap);
        AssertJUnit.assertEquals(updatedNhaCungCap.getTenNhaCungCap(), "Nha Cung Cap 2");
    }

    @Test
    public void testDelete() {
        nhaCungCapDao.delete("NCC01");
        NhaCungCap deletedNhaCungCap = nhaCungCapDao.selectById("NCC01");
        AssertJUnit.assertNull(deletedNhaCungCap);
    }

    @Test
    public void testSelectAll() {
        List<NhaCungCap> list = nhaCungCapDao.selectAll();
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById() {
        NhaCungCap nhaCungCap = nhaCungCapDao.selectById("NCC01");
        AssertJUnit.assertNotNull(nhaCungCap);
        AssertJUnit.assertEquals(nhaCungCap.getMaNhaCungCap(), "NCC01");
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from nhacungcap where manhacungcap=?";
        List<NhaCungCap> list = nhaCungCapDao.selectBySql(sql, "NCC01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals(list.get(0).getMaNhaCungCap(), "NCC01");
    }
}
