package test_salon_ui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import salon.tienich.Auth;
import salon.ui.Login;

public class TestLoginASM {
    private File file;
    private volatile Login dangNhapJDialog;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    Map<String, Object[]> TestNGResult;

    @BeforeTest
    public void openMainFrame() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestLoginASM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        file = new File(System.getProperty("user.dir") + "//TestASM.xls");
    }

    @BeforeClass(alwaysRun = true)
    public void suiteSetUp() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("TestNG Result Summary");

        TestNGResult = new LinkedHashMap<String, Object[]>();

        TestNGResult.put("1", new Object[] { "Test step No.", "Action", "Expected Output", "Actual Output" });
    }

    @BeforeMethod
    public void createDialog() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dangNhapJDialog = new Login(null, true);
                dangNhapJDialog.setVisible(true);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void removeDialog() {
        if (dangNhapJDialog != null) {
            Auth.user = null;
            dangNhapJDialog.dispose();
        }
    }

    @Test(description = "Đăng nhập thành công với tài khoản admin", priority = 1)
    public void testAdminLogin() {
        try {
            dangNhapJDialog.getTxtMaNV().setText("tuanhm");
            dangNhapJDialog.getTxtMatKhau().setText("123456");
            dangNhapJDialog.getBtnDangNhap().doClick();
            Assert.assertNotNull(Auth.user, "Đăng nhập không thành công");
            Assert.assertTrue(Auth.isManager(), "Tài khoản không có quyền quản lý");
            TestNGResult.put("2", new Object[] { 1d, "Đăng nhập thành công với tài khoản admin", "Đăng nhập thành công", "Pass" });
        } catch (Exception e) {
            TestNGResult.put("2", new Object[] { 1d, "Đăng nhập thành công với tài khoản admin", "Đăng nhập không thành công", "Fail" });
            Assert.fail("Đăng nhập không thành công");
        }
    }

    @Test(description = "Đăng nhập thành công với tài khoản user", priority = 2)
    public void testUserLogin() {
        try {
            dangNhapJDialog.getTxtMaNV().setText("khoava");
            dangNhapJDialog.getTxtMatKhau().setText("123456");
            dangNhapJDialog.getBtnDangNhap().doClick();
            Assert.assertNotNull(Auth.user, "Đăng nhập không thành công");
            TestNGResult.put("2", new Object[] { 1d, "Đăng nhập thành công với tài khoản admin", "Đăng nhập thành công tài khoản không phải User", "Fail" });
            Assert.assertFalse(Auth.isManager(), "Tài khoản user có quyền quản lý không đúng");
            TestNGResult.put("3", new Object[] { 2d, "Đăng nhập thành công với tài khoản user", "Đăng nhập thành công", "Pass" });
        } catch (Exception e) {
            TestNGResult.put("3", new Object[] { 2d, "Đăng nhập thành công với tài khoản user", "Đăng nhập không thành công", "Fail" });
            Assert.fail("Đăng nhập không thành công");
        }
    }

    @Test(description = "Đăng nhập thất bại với tên đăng nhập sai", priority = 3)
    public void testInvalidUsername() {
        try {
            dangNhapJDialog.getTxtMaNV().setText("tuan");
            dangNhapJDialog.getTxtMatKhau().setText("123456");
            dangNhapJDialog.getBtnDangNhap().doClick();

            Assert.assertNull(Auth.user, "Đăng nhập thành công không đúng");
            TestNGResult.put("4", new Object[] { 3d, "Đăng nhập thất bại với tên đăng nhập sai", "Đăng nhập thất bại", "Pass" });
        } catch (Exception e) {
            TestNGResult.put("4", new Object[] { 3d, "Đăng nhập thất bại với tên đăng nhập sai", "Đăng nhập thành công", "Fail" });
            Assert.fail("Đăng nhập thành công không đúng");
        }
    }

    @Test(description = "Đăng nhập thất bại với mật khẩu sai", priority = 4)
    public void testInvalidPassword() {
        try {
            dangNhapJDialog.getTxtMaNV().setText("tuanhm");
            dangNhapJDialog.getTxtMatKhau().setText("123");
            dangNhapJDialog.getBtnDangNhap().doClick();

            Assert.assertNull(Auth.user, "Đăng nhập thành công không đúng");
            TestNGResult.put("5", new Object[] { 4d, "Đăng nhập thất bại với mật khẩu sai", "Đăng nhập thất bại", "Pass" });
        } catch (Exception e) {
            TestNGResult.put("5", new Object[] { 4d, "Đăng nhập thất bại với mật khẩu sai", "Đăng nhập thành công", "Fail" });
            Assert.fail("Đăng nhập thành công không đúng");
        }
    }


    @AfterClass
    public void suiteTearDown() {
        Set<String> keyset = TestNGResult.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = TestNGResult.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof Date) {
                    cell.setCellValue((Date) obj);
                } else if (obj instanceof Boolean) {
                    cell.setCellValue((Boolean) obj);
                } else if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                }
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(new File("TestReportLoginASM.xls"));
            workbook.write(out);
            out.close();
            System.out.println("Successfully saved Selenium WebDriver TestNG result to Excel file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
