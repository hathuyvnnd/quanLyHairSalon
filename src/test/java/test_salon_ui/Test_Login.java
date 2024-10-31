package test_salon_ui;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import salon.Dao.NhanVienDao;
import salon.entity.NhanVien;

public class Test_Login {
	NhanVien nv;
	NhanVienDao nvDao;
	List<NhanVien> list = new ArrayList<>();
	@BeforeClass
	public void setUp() {
		nv = new NhanVien();
		nvDao = new NhanVienDao();
		list = nvDao.selectAll();
		
	}
	
	@AfterClass
	public void finish() {
		nv = null;
	}
		
	@Test
	public void testNV1(){
		String ma ="khoava";
		String pass="123456";
		boolean cv = false;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testNV2(){
		String ma ="khoava";
		String pass="1234567";
		boolean cv = false;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testNV3(){
		String ma ="khoava";
		String pass="123456";
		boolean cv = true;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testNV4(){
		String ma ="";
		String pass="123456";
		boolean cv = false;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testNV5(){
		String ma ="khoava";
		String pass="";
		boolean cv = false;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testNV6(){
		String ma ="";
		String pass="";
		boolean cv = false;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testNV7(){
		String ma ="khoavaak";
		String pass="123456";
		boolean cv = true;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testNV8(){
		String ma ="khoavaak";
		String pass="1234567";
		boolean cv = true;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
		
	}
	@Test
	public void testQL1() {
		String ma ="thuyht";
		String pass="123456";
		boolean cv = true;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
	}
	@Test
	public void testQL2() {
		String ma ="thuyht";
		String pass="123456ab";
		boolean cv = true;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
	}
	@Test
	public void testQL3() {
		String ma ="thuyht123";
		String pass="123456";
		boolean cv = false;
		nv = nvDao.selectById(ma);
		if(nv!= null) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(pass, nv.getMatKhau());
		Assert.assertEquals(cv, nv.isVaiTro());
	}
	
	
	
	
}
