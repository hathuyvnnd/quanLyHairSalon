/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import salon.entity.HoaDon;
import salon.tienich.JdbcHelp;

/**
 *
 * @author Duy Toan
 */
public class HoaDonDao extends SalonDao<HoaDon, Object> {

    String insert_sql = "insert into hoadon (SoDienThoai,Ngay,MaNhanVien,MaUuDai,HinhThucThanhToan) values(?,?,?,?,?)";
    String update_sql = "update hoadon set sodienthoai =?,mauudai=?,hinhthucthanhtoan=?  where mahoadon=? ";
    String delete_sql = "delete from hoadon where mahoadon=?";
    String select_all_sql = "select * from hoadon ";
    String selectbyid_sql = "select * from  hoadon where mahoadon=? ";
    String selectbyNV_sql = "select * from  hoadon where manhanvien=? ";
    String SELECT_BY_DATE = "select * from hoadon where Ngay=?";

    @Override
    public void insert(HoaDon entity) {
        JdbcHelp.update(insert_sql, entity.getSdtKH(), entity.getNgay(), entity.getMaNhanVien(), entity.getMaUuDai(), entity.getHinhThucThanhToan());
    }

    @Override
    public void update(HoaDon entity) {
        JdbcHelp.update(update_sql, entity.getSdtKH(), entity.getMaUuDai(), entity.getHinhThucThanhToan(), entity.getMaHoaDon());
    }

    @Override
    public void delete(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySql(select_all_sql);
    }

    @Override
    public HoaDon selectById(Object id) {
    	 List<HoaDon> list = selectBySql(selectbyid_sql, id);
         if (list.isEmpty()) {
             return null;
         }
         return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<HoaDon>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHoaDon(rs.getInt("mahoadon"));
                entity.setSdtKH(rs.getString("sodienthoai"));
                entity.setNgay(rs.getDate("ngay"));
                entity.setMaNhanVien(rs.getString("manhanvien"));
                entity.setMaUuDai(rs.getString("mauudai"));
                entity.setHinhThucThanhToan(rs.getString("hinhthucthanhtoan"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<HoaDon> selectbykeyword(String keyword) {
        String sql = "select * from  hoadon where manhanvien like ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }

    public List<HoaDon> selectByDate(Date ngay) {
        List<HoaDon> listLH = selectBySql(SELECT_BY_DATE, ngay);
        return listLH;
    }

}
