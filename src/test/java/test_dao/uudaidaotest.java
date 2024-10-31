package test_dao;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import java.util.List;
import salon.Dao.UuDaiDao;
import salon.entity.UuDai;
import java.sql.Date;

public class uudaidaotest {

    private UuDaiDao uuDaiDao;

    @org.testng.annotations.BeforeClass
    public static void setUpClass() {
        // This will run once before any of the test methods in this class
    }

    @BeforeMethod
	@org.testng.annotations.BeforeClass
    public void setUp() {
        uuDaiDao = new UuDaiDao();
    }

    @AfterMethod
	@AfterClass
    public void tearDown() {
        uuDaiDao = null;
    }

    @Test
	
    public void testInsert() {
        UuDai uuDai = new UuDai("UD001", "Promotion 1", Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), "Description 1", 0.1f);
        uuDaiDao.insert(uuDai);
        UuDai result = uuDaiDao.selectById("UD001");
        Assert.assertNotNull(result);
        Assert.assertEquals("Promotion 1", result.getTenUuDai());
    }

    @Test
	
    public void testUpdate() {
        UuDai uuDai = new UuDai("UD001", "Promotion 2", Date.valueOf("2024-01-01"), Date.valueOf("2024-12-31"), "Description 2", 0.2f);
        uuDaiDao.update(uuDai);
        UuDai result = uuDaiDao.selectById("UD001");
        Assert.assertNotNull(result);
        Assert.assertEquals("Promotion 2", result.getTenUuDai());
    }

    @Test
	
    public void testDelete() {
        uuDaiDao.delete("UD001");
        UuDai result = uuDaiDao.selectById("UD001");
        Assert.assertNull(result);
    }

    @Test
	
    public void testSelectAll() {
        List<UuDai> list = uuDaiDao.selectAll();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }


    @Test
	
    public void testSelectByKeyword() {
        List<UuDai> list = uuDaiDao.selectbykeyword("UD001");
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() > 0);
    }

 
}
