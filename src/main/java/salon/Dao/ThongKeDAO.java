/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.tienich.JdbcHelp;

/**
 *
 * @author mrphu
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                Object[] value = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    value[i] = rs.getObject(cols[i]);
                }
                list.add(value);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getLichSuNhapHang(int thang, int nam) {
        String sql = "{CALL getLichSuNhapHang(?,?)}";
        String[] cols = {"MaPhieuNhap", "NgayNhap", "MaNhaCungCap", "MaCuaHang"};
        return getListOfArray(sql, cols, thang, nam);
    }

    public List<Object[]> getTongTienNhap(int maphieunhap) {
        String sql = "{CALL getTongTienNhap(?)}";
        String[] cols = {"MaPhieuNhap", "GiaNhap"};
        return getListOfArray(sql, cols, maphieunhap);
    }

    public List<Object[]> getLichSuXuatHang(int thang, int nam) {
        String sql = "{CALL getLichSuXuatHang(?,?)}";
        String[] cols = {"MaPhieuXuat", "NgayXuat", "MaCuaHang"};
        return getListOfArray(sql, cols, thang, nam);
    }

    public List<Object[]> getChiTietPhieuNhap(int maphieunhap) {
        String sql = "{CALL getChiTietPhieuNhap(?)}";
        String[] cols = {"MaPhieuNhap", "MaSanPham", "SoLuongNhap", "ThanhTien"};
        return getListOfArray(sql, cols, maphieunhap);
    }

    public int getSoLuongThang(String sql, int thang, int nam) {
        int a = 0;
        try {
            ResultSet rs = null;
            rs = JdbcHelp.query(sql, thang, nam);
            while (rs.next()) {
                a = rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        return a;
    }

    public int getSoLuongNam(String sql, int nam) {
        int a = 0;
        try {
            ResultSet rs = null;
            rs = JdbcHelp.query(sql, nam);
            while (rs.next()) {
                a = rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return a;
    }

    public List<Object[]> getHangTonKho() {
        String sql = "select KHO.MaSanPham,SANPHAM.TenSanPham,sum(SoLuong) as SoLuong \n"
                + "from KHO join SANPHAM on KHO.MaSanPham = SANPHAM.MaSanPham\n"
                + "group by KHO.MaSanPham, SANPHAM.TenSanPham";
        String[] cols = {"MaSanPham", "TenSanPham", "SoLuong"};
        return getListOfArray(sql, cols);

    }

    public List<Object[]> getDoanhThuBYThang(int nam) {
        String sql = "select MONTH(HOADON.Ngay) as Thang,sum(DT.DoanhThu*CHUONGTRINHUUDAI.GiamGia) as DoanhThuThucTe\n"
                + "from HOADON join\n"
                + "(select HOADON.MaHoaDon,sum(CHITIETHOADON.SoLuong*DICHVU.GiaDichVu) as DoanhThu\n"
                + "	from CHITIETHOADON join DICHVU on CHITIETHOADON.MaDichVu = DICHVU.MaDichVu\n"
                + "							join HOADON on CHITIETHOADON.MaHoaDon = HOADON.MaHoaDon\n"
                + "group by HOADON.MaHoaDon) DT on HOADON.MaHoaDon = DT.MaHoaDon \n"
                + "join CHUONGTRINHUUDAI on HOADON.MaUuDai = CHUONGTRINHUUDAI.MaUuDai\n"
                + "where Year(HOADON.Ngay) =2024 \n"
                + "group by MONTH(HOADON.Ngay)";
        String[] cols = {"Thang", "DoanhThuThucTe"};
        return getListOfArray(sql, cols);
    }

}
