package test_dao;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import salon.Dao.HoaDonDao;
import salon.entity.HoaDon;
import salon.tienich.XDate;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class HoaDonDaoTest {

    private HoaDonDao hdDao;
    private HoaDon hd;

    @BeforeMethod
	@BeforeClass
    public void setUp() {
    	hdDao = new HoaDonDao();
    }
    
    @AfterMethod
	@AfterTest
    public void tearDown() {
    	hd = null;
    }
    @Test
    public void testInsert() {
    	hd = new HoaDon();
    	List<HoaDon> list = hdDao.selectAll();
    	int soLuongList = list.size();
    	String phoneKH = "0123456789";
    	Date ngay = new Date();
    	String maNV = "khoava";
    	String maUD = "UD01";
    	String htTT = "Tiền mặt";
    	hd.setSdtKH(phoneKH);
    	hd.setMaNhanVien(maNV);
    	hd.setMaUuDai(maUD);
    	hd.setHinhThucThanhToan(htTT);
    	hd.setNgay(ngay);
    	hdDao.insert(hd);
    	AssertJUnit.assertTrue(hdDao.selectAll().size()>soLuongList);
    }
    
    @Test
    public void testUpdate1() {
    	hd = new HoaDon();
    	int maHD =1;
    	String phoneKH ="0123456789";
    	String maUD = "UD01";
    	String htTT = "Tiền mặt";
    	hd.setSdtKH(phoneKH);
    	hd.setMaUuDai(maUD);
    	hd.setHinhThucThanhToan(htTT);
    	hd.setMaHoaDon(maHD);
    	hdDao.update(hd);
    	HoaDon hdUD = hdDao.selectById(hd.getMaHoaDon());
    	AssertJUnit.assertNotNull(hdUD);
    	AssertJUnit.assertEquals(hdUD.getSdtKH(),"0123456789");
    }
    @Test
    public void testUpdate2() {
    	hd = new HoaDon();
    	int maHD =1000;
    	String phoneKH ="0123456789";
    	String maUD = "UD01";
    	String htTT = "Tiền mặt";
    	hd.setSdtKH(phoneKH);
    	hd.setMaUuDai(maUD);
    	hd.setHinhThucThanhToan(htTT);
    	hd.setMaHoaDon(maHD);
    	hdDao.update(hd);
    	HoaDon hdUD = hdDao.selectById(hd.getMaHoaDon());
    	AssertJUnit.assertNotNull(hdUD);
    	AssertJUnit.assertEquals(hdUD.getSdtKH(),"0123456789");
    }
    
    
    


}
