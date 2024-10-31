package test_dao;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import salon.Dao.NhanVienDao;
import salon.entity.NhanVien;

import java.util.List;

public class NhanVienDaoTest {

    private NhanVienDao nhanVienDao;
    NhanVien nv;
    @BeforeMethod
	@BeforeClass
    public void setUp() {
        nhanVienDao = new NhanVienDao();
    }
    @BeforeMethod
    public void init() {
    	nv = new NhanVien();
    }
    @AfterTest
    public void finish() {
    	nv = null;
    }

    @Test
    public void testInsert() {
        nv = new NhanVien("NV0201", "123456", "Nguyen Van A", true, "12/12/1996", true, "NhanVien", "0987654321", "email@example.com", 5000, 10, 0, true, "CH01");
        if(nv.getSoDienThoai().matches("(84|0[35789])[0-9]{8}")) {
        	nhanVienDao.insert(nv);
            NhanVien insertedNv = nhanVienDao.selectById(nv.getMaNhanVien());
            AssertJUnit.assertNotNull(insertedNv);
            AssertJUnit.assertEquals(insertedNv.getMaNhanVien(), nv.getMaNhanVien());
        }
        else {
        	NhanVien insertedNv = null;
        	AssertJUnit.assertNotNull(insertedNv);
        }
    }
    @Test
    public void testInsert1() {
        nv = new NhanVien("NV02", "123456", "Nguyen Van A", true, "12/12/1996", true, "NhanVien", "123456789", "email@example.com", 5000, 10, 0, true, "CH01");
        if(nv.getSoDienThoai().matches("(84|0[35789])[0-9]{8}")) {
        	nhanVienDao.insert(nv);
            NhanVien insertedNv = nhanVienDao.selectById(nv.getMaNhanVien());
            AssertJUnit.assertNotNull(insertedNv);
            AssertJUnit.assertEquals(insertedNv.getMaNhanVien(), nv.getMaNhanVien());
        }
        else {
        	NhanVien insertedNv = null;
        	AssertJUnit.assertNotNull(insertedNv);
        }
    }
    @Test
    public void testInsert3() {
        nv = new NhanVien("NV02AB", "123456", "Nguyen Van A", true, "12/12/1996", true, "NhanVien", "123456789", "email@example.com", 5000, 10, 0, true, "CH01");
        if(nv.getSoDienThoai().matches("(84|0[35789])[0-9]{8}")) {
        	nhanVienDao.insert(nv);
            NhanVien insertedNv = nhanVienDao.selectById(nv.getMaNhanVien());
            AssertJUnit.assertNotNull(insertedNv);
            AssertJUnit.assertEquals(insertedNv.getMaNhanVien(), nv.getMaNhanVien());
        }
        else {
        	NhanVien insertedNv = null;
        	AssertJUnit.assertNotNull(insertedNv);
        }
    }

    @Test
    public void testUpdate() {
        nv = new NhanVien("NV01", "123456", "Nguyen Van B", false, "12/21/1994", false, "NhanVien", "0987654321", "newemail@example.com", 6000, 15, 0, false, "CH02");
        nhanVienDao.update(nv);
        NhanVien updatedNv = nhanVienDao.selectById("NV01");
        AssertJUnit.assertNotNull(updatedNv);
        AssertJUnit.assertEquals(updatedNv.getHoTen(), "Nguyen Van B");
        
    }
    @Test
    public void testUpdate1() {
        nv = new NhanVien("NV01", "123456", "Nguyen Van B", false, "12/21/1994", false, "NhanVien", "1234657899999999", "newemail@example.com", 6000, 15, 0, false, "CH02");
        if(nv.getSoDienThoai().matches("(84|0[35789])[0-9]{8}")) {
        	nhanVienDao.update(nv);
            NhanVien updatedNv = nhanVienDao.selectById("NV01");
            AssertJUnit.assertNotNull(updatedNv);
            AssertJUnit.assertEquals(updatedNv.getHoTen(), "Nguyen Van B");
        }
        else {
        	NhanVien updatedNv = null;
        	AssertJUnit.assertNotNull(updatedNv);
        }
        
    }
    @Test
    public void testUpdate2() {
        nv = new NhanVien("NV01AB", "123456", "Nguyen Van B", false, "12/21/1994", false, "NhanVien", "0987654321", "newemail@example.com", 6000, 15, 0, false, "CH02");
        nhanVienDao.update(nv);
        NhanVien updatedNv = nhanVienDao.selectById("NV01");
        AssertJUnit.assertNotNull(updatedNv);
        AssertJUnit.assertEquals(updatedNv.getHoTen(), "Nguyen Van B");
        
    }

    @Test
    public void testSelectAll() {
        List<NhanVien> list = nhanVienDao.selectAll();
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
    }

    @Test
    public void testSelectById() {
        NhanVien nv = nhanVienDao.selectById("NV01");
        AssertJUnit.assertNotNull(nv);
        AssertJUnit.assertEquals(nv.getMaNhanVien(), "NV01");
    }

    @Test
    public void testSelectBySql() {
        String sql = "select * from NHANVIEN where MaNhanVien=?";
        List<NhanVien> list = nhanVienDao.selectBySql(sql, "NV01");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
        AssertJUnit.assertEquals(list.get(0).getMaNhanVien(), "NV01");
    }

    @Test
    public void testSelectByKeyword() {
        List<NhanVien> list = nhanVienDao.selectbykeyword("Nguyen");
        AssertJUnit.assertNotNull(list);
        AssertJUnit.assertTrue(list.size() > 0);
    }
}
