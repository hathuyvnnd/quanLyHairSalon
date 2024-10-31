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
public class ChiTietBangLuongDao extends SalonDao<salon.entity.ChiTietBangLuong,Integer>{
    final String SELECT_ALL ="select * from CHITIETBANGLUONG";
    final String SELECT_UPATE ="update CHITIETBANGLUONG set NgayCong =? where Thang =? and Nam =? and MaNhanVien =?";
    final String SELECT_BY_ID = "select * from CHITIETBANGLUONG where Thang =? and Nam =?";
    
    @Override
    public void insert(salon.entity.ChiTietBangLuong entity) {
        
    }

    @Override
    public void update(salon.entity.ChiTietBangLuong entity) {
        JdbcHelp.update(SELECT_UPATE, entity.getNgayCong(),entity.getThang(),entity.getNam(),entity.getMaNhanVien());
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<salon.entity.ChiTietBangLuong> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public salon.entity.ChiTietBangLuong selectById(Integer Thang) {
        return null;
    }

    @Override
    public List<salon.entity.ChiTietBangLuong> selectBySql(String sql, Object... args) {
        List<salon.entity.ChiTietBangLuong> list = new ArrayList<>();
        try{
            ResultSet rs = JdbcHelp.query(sql, args);
            while(rs.next()){
                salon.entity.ChiTietBangLuong ctBL = new salon.entity.ChiTietBangLuong();
                ctBL.setThang(rs.getInt("Thang"));
                ctBL.setNam(rs.getInt("Nam"));
                ctBL.setNgayCong(rs.getInt("NgayCong"));
                ctBL.setMaNhanVien(rs.getString("MaNhanVien"));
                list.add(ctBL);
            }
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<salon.entity.ChiTietBangLuong> selectByMa(Integer thang, Integer nam){
        List<salon.entity.ChiTietBangLuong> listCT =  selectBySql(SELECT_BY_ID,thang,nam);
        return listCT;
    }
    
    public List<Object []> getList(String sql, String [] cols , Object ... args){
        try{
            List<Object []> list = new ArrayList<>();
            ResultSet rs=  JdbcHelp.query(sql, args);
            while(rs.next()){
                Object [] value = new Object[cols.length];
                for(int i = 0; i< cols.length; i++){
                    value[i] = rs.getObject(cols[i]);
                }
                list.add(value);
            }
            return list;
        }
        catch(Exception e){
            throw new RuntimeException(
                    e);
        }
    }
    
    public int tinhDoanhThu(int thang, int nam, String maNV){
        int doanhThu =0;
        try{
            ResultSet rs = null;
            String sql = "{call getBangLuong(?, ?, ?)}";
            rs = JdbcHelp.query(sql, thang, nam, maNV);
            while(rs.next()){
                doanhThu = rs.getInt(1);
            }
            rs.getStatement().getConnection().close();
        }
        catch(Exception e){
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return doanhThu;
    }
     
}
