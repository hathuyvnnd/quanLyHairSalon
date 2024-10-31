package test_dao;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import salon.Dao.SanPhamDao;
import salon.entity.SanPham;
import java.util.List;

public class sanphamdaotest {

    private SanPhamDao sanPhamDao;
    private SanPham sp;

    @org.testng.annotations.BeforeClass
    public static void setUpClass() {
        // This will run once before any of the test methods in this class
    }

    @BeforeMethod
	@org.testng.annotations.BeforeClass
    public void setUp() {
        sanPhamDao = new SanPhamDao();
        sp = new SanPham();
    }

    @AfterMethod
	@AfterClass
    public void tearDown() {
        sp = null;
    }


    @Test
	
    public void testInsert() {
        SanPham sanPham = new SanPham("SP001", "Product 1", 1000000, "01", "image1.jpg");
        sanPhamDao.insert(sanPham);
        SanPham result = sanPhamDao.selectById("SP001");
        Assert.assertNotNull(result);
        Assert.assertEquals("Product 1", result.getTenSanPham());
    }

    @Test
	
    public void testUpdate() {
        SanPham sanPham = new SanPham("SP01", "Product 2", 200000, "01", "image2.jpg");
        sanPhamDao.update(sanPham);
        SanPham result = sanPhamDao.selectById("SP01");
        Assert.assertNotNull(result);
        Assert.assertEquals("Product 2", result.getTenSanPham());
    }

    @Test
	
    public void testSelectAll() {
        List<SanPham> list = sanPhamDao.selectAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
	
    public void testSelectById() {
        SanPham sp = sanPhamDao.selectById("SP01");
        Assert.assertNotNull(sp);
        Assert.assertEquals(sp.getMaSanPham(), "SP01");
    }

    @Test
	
    public void testSelectBySql() {
        String sql = "SELECT * FROM sanpham WHERE masanpham=?";
        List<SanPham> list = sanPhamDao.selectBySql(sql, "SP01");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
        Assert.assertEquals(list.get(0).getMaSanPham(), "SP01");
    }

    @Test
	
    public void testSelectByKeyword() {
        List<SanPham> list = sanPhamDao.selectbykeyword("GOLGWELL dập nước");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
	
    public void testSelectByNhaCungCap() {
        List<SanPham> list = sanPhamDao.selecbyNhaCungCap("01");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }
}
