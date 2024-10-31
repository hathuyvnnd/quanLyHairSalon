/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.NhanVien;
import salon.tienich.JdbcHelp;

/**
 *
 * @author mrphu
 */
public class NhanVienDao extends SalonDao<NhanVien, String> {

    String SELECT_BY_ID_SQL = "select * from NHANVIEN where MaNhanVien=?";
    String SELECT_ALL = "select * from NHANVIEN";
    String update_nv = "update NHANVIEN set Hoten=?, GioiTinh=?, NgaySinh=?, SoDienThoai=?, Emaill=?, MaCuaHang=? where MaNhanVien=?";
    String update_password_sql = "UPDATE NHANVIEN SET MATKHAU = ? WHERE MANHANVIEN =?";
    String insert_sql = "insert into nhanvien (manhanvien,matkhau,hoten,gioitinh,ngaysinh,vaitro,chucvu,sodienthoai,Emaill,luongcung,hoahong,trangthai,macuahang) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String update_ql = "update nhanvien set matkhau=?, hoten =?,gioitinh=?,ngaysinh=?,vaitro=?,chucvu=?,sodienthoai=?,emaill=?,luongcung=?,hoahong=?,trangthai=?,macuahang=?  where manhanvien = ? ";

    @Override
    public void insert(NhanVien entity) {
        JdbcHelp.update(insert_sql, entity.getMaNhanVien(), entity.getMatKhau(), entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.isVaiTro(), entity.getChucVu(), entity.getSoDienThoai(), entity.getEmail(), entity.getLuongCung(), entity.getHoaHong(), entity.isTrangThai(), entity.getMaCuaHang());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelp.update(update_nv, entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getSoDienThoai(), entity.getEmail(), entity.getMaCuaHang(), entity.getMaNhanVien());
    }

    public void updatePassword(NhanVien entity) {
        JdbcHelp.update(update_password_sql, entity.getMatKhau(), entity.getMaNhanVien());
    }

    public void updateQL(NhanVien entity) {
        JdbcHelp.update(update_ql, entity.getMatKhau(), entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getSoDienThoai(), entity.getEmail(), entity.getMaCuaHang(), entity.getMaNhanVien());
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<NhanVien> selectAll() {
        List<NhanVien> list = selectBySql(SELECT_ALL);
        return list;
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setNgaySinh(rs.getString("NgaySinh"));
                nv.setVaiTro(rs.getBoolean("VaiTro"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setSoDienThoai(rs.getString("SoDienThoai"));
                nv.setEmail(rs.getString("Emaill"));
                nv.setLuongCung(rs.getInt("LuongCung"));
                nv.setHoaHong(rs.getInt("HoaHong"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                nv.setMaCuaHang(rs.getString("MaCuaHang"));
                list.add(nv);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<NhanVien> selectbykeyword(String keyword) {
        String sql = "select * from  nhanvien where manhanvien like ? or hoten like ?";
        return this.selectBySql(sql, "%" + keyword + "%", "%" + keyword + "%");
    }

    public void updateql(NhanVien entity) {
        JdbcHelp.update(update_ql, entity.getMatKhau(), entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.isVaiTro(), entity.getChucVu(), entity.getSoDienThoai(), entity.getEmail(), entity.getLuongCung(), entity.getHoaHong(), entity.isTrangThai(), entity.getMaCuaHang(), entity.getMaNhanVien());
    }

	

}
