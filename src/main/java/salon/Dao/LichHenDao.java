/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salon.entity.LichHen;
import salon.tienich.JdbcHelp;

/**
 *
 * @author mrphu
 */
public class LichHenDao extends SalonDao<LichHen, Integer> {

    final String SELECT_ALL = "select * from LICHHEN";
    final String SELECT_UPDATE = "update LICHHEN set SoDienThoai=?, Ngay=?, Gio=?, NhanVien=?, GhiChu=?, TrangThai=? where MaLichHen=?";
    final String SELECT_BY_ID = "select * from LICHHEN where MaLichHen=?";
    final String SELECT_INSERT = "insert into LICHHEN (SoDienThoai,Ngay,Gio,NhanVien,GhiChu,TrangThai) values (?,?,?,?,?,?)";
    final String SELECT_BY_DATE = "select * from LICHHEN where Ngay=?";

    @Override
    public void insert(LichHen entity) {
        JdbcHelp.update(SELECT_INSERT, entity.getSodienthoaiKH(),entity.getNgayHen(), entity.getGioHen(),entity.getMaNhanVien(),entity.getGhiChu(),entity.getTrangThai());
    }

    @Override
    public void update(LichHen entity) {
        JdbcHelp.update(SELECT_UPDATE, entity.getSodienthoaiKH(),entity.getNgayHen(),entity.getGioHen(),entity.getMaNhanVien(),entity.getGhiChu(),entity.getTrangThai(),entity.getMaLichHen());
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<LichHen> selectAll() {
        List<LichHen> list = selectBySql(SELECT_ALL);
        return list;
    }

    @Override
    public LichHen selectById(Integer id) {
        List<LichHen> list = selectBySql(SELECT_BY_ID,id);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
    
    public List<LichHen> selectByDate(Date ngay){
        List<LichHen> listLH = selectBySql(SELECT_BY_DATE,ngay);
        return listLH;
    }

    @Override
    public List<LichHen> selectBySql(String sql, Object... args) {
        List<LichHen> listLH = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                LichHen lh = new LichHen();
                lh.setMaLichHen(rs.getInt("MaLichHen"));
                lh.setSodienthoaiKH(rs.getString("SoDienThoai"));
                lh.setNgayHen(rs.getDate("Ngay"));
                lh.setGioHen(rs.getString("Gio"));
                lh.setMaNhanVien(rs.getString("NhanVien"));
                lh.setGhiChu(rs.getString("GhiChu"));
                lh.setTrangThai(rs.getBoolean("TrangThai"));
                listLH.add(lh);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listLH;
    }

}
