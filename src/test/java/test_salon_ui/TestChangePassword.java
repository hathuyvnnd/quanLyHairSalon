package test_salon_ui;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import salon.Dao.NhanVienDao;
import salon.entity.NhanVien;

public class TestChangePassword {
	NhanVienDao nvDao;
	NhanVien nv;
	@BeforeClass
	public void init() {
		nvDao = new NhanVienDao();
		
		
	}
	@BeforeMethod
	@BeforeTest
	public void setUp() {
		nv = new NhanVien();
	}
	@AfterMethod
	public void clear() {
		nv = nvDao.selectById("khoava");
		nv.setMatKhau("123456");
		nvDao.updatePassword(nv);
	}
	@Test
	public void testCP1() {
		nv = nvDao.selectById("khoava");
		String mkCu="123456";
		AssertJUnit.assertEquals(mkCu, nv.getMatKhau());
		String mkNew = "456789";
		nv.setMatKhau(mkNew);
		nvDao.updatePassword(nv);
		AssertJUnit.assertEquals(mkNew, nv.getMatKhau());
		
	}
	@Test
	public void testCP2() {
		nv = nvDao.selectById("khoava");
		String mkCu="123456";
		AssertJUnit.assertEquals(mkCu, nv.getMatKhau());
		String mkNew = "456789";
		nv.setMatKhau(mkNew);
		nvDao.updatePassword(nv);
		AssertJUnit.assertEquals(mkCu, nv.getMatKhau());
		
	}
	@Test
	public void testCP3() {
		nv = nvDao.selectById("khoava");
		String mkCu="223344";
		AssertJUnit.assertEquals(mkCu, nv.getMatKhau());
		String mkNew ="456789";
		nv.setMatKhau(mkNew);
		nvDao.updatePassword(nv);
		AssertJUnit.assertEquals(mkNew, nv.getMatKhau());
		
	}
	
}
