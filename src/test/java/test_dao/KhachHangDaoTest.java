package test_dao;


import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import salon.Dao.KhachHangDao;
import salon.entity.KhachHang;

import java.util.List;

public class KhachHangDaoTest {

    private KhachHangDao khachHangDao;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
        khachHangDao = new KhachHangDao();
    }

    @Test
    public void testInsert() {
        KhachHang kh = new KhachHang();
        kh.setSoDienThoai("0987654321");
        kh.setHoTen("Nguyen Van B");

        khachHangDao.insert(kh);

        // Retrieve KhachHang to verify insertion
        KhachHang retrievedKh = khachHangDao.selectById(kh.getSoDienThoai());
        Assert.assertNotNull(retrievedKh, "KhachHang should be inserted and retrieved.");
        Assert.assertEquals(retrievedKh.getSoDienThoai(), kh.getSoDienThoai(), "SoDienThoai should match.");
        Assert.assertEquals(retrievedKh.getHoTen(), kh.getHoTen(), "HoTen should match.");
    }

    @Test
    public void testUpdate() {
        KhachHang kh = new KhachHang();
        kh.setSoDienThoai("0987654321");
        kh.setHoTen("Nguyen Van B");
        khachHangDao.update(kh);

        // Retrieve KhachHang to verify update
        KhachHang updatedKh = khachHangDao.selectById(kh.getSoDienThoai());
        Assert.assertNotNull(updatedKh, "KhachHang should be updated and retrieved.");
        Assert.assertEquals(updatedKh.getHoTen(), "Nguyen Van B", "HoTen should be updated.");
    }
    @Test
    public void testUpdate1() {
        KhachHang kh = new KhachHang();
        kh.setSoDienThoai("09876543211");
        kh.setHoTen("Nguyen Van B");
        khachHangDao.update(kh);

        // Retrieve KhachHang to verify update
        KhachHang updatedKh = khachHangDao.selectById(kh.getSoDienThoai());
        Assert.assertNotNull(updatedKh, "KhachHang should be updated and retrieved.");
        Assert.assertEquals(updatedKh.getHoTen(), "Nguyen Van B", "HoTen should be updated.");
    }

    @Test
    public void testDelete() {
        // Insert a KhachHang first
        KhachHang kh = new KhachHang();
        kh.setSoDienThoai("0987654321");
        kh.setHoTen("Nguyen Van C");
        khachHangDao.insert(kh);

        // Delete the KhachHang
        khachHangDao.delete(kh.getSoDienThoai());

        // Retrieve KhachHang to verify deletion
        KhachHang deletedKh = khachHangDao.selectById(kh.getSoDienThoai());
        Assert.assertNull(deletedKh, "KhachHang should be deleted and not found.");
    }

    @Test
    public void testSelectAll() {
        List<KhachHang> list = khachHangDao.selectAll();
        Assert.assertNotNull(list, "List of KhachHang should not be null.");
        Assert.assertTrue(list.size() > 0, "List of KhachHang should not be empty.");
        for (KhachHang kh : list) {
            Assert.assertNotNull(kh.getSoDienThoai(), "SoDienThoai should not be null.");
            Assert.assertNotNull(kh.getHoTen(), "HoTen should not be null.");
        }
    }

    @Test
    public void testSelectById() {
        KhachHang kh = new KhachHang();
        kh.setSoDienThoai("0123456789");
        kh.setHoTen("Nguyen Van D");
        khachHangDao.insert(kh);

        // Retrieve KhachHang by ID
        KhachHang retrievedKh = khachHangDao.selectById(kh.getSoDienThoai());
        Assert.assertNotNull(retrievedKh, "KhachHang should be retrieved.");
        
    }

    // Optional: Add tests for edge cases, such as invalid inputs, etc.
}
