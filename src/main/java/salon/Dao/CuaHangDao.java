/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.CuaHang;
import salon.tienich.JdbcHelp;

/**
 *
 * @author mrphu
 */
public class CuaHangDao extends SalonDao<CuaHang,String>{
    String SELECT_ALL_SQL ="select * from CUAHANG";

    @Override
    public List<CuaHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public List<CuaHang> selectBySql(String sql, Object... args) {
        List<CuaHang> list = new ArrayList<>();
        try{
            ResultSet rs = JdbcHelp.query(sql, args);
            System.out.println(rs);
            while(rs.next()){
                CuaHang ch = new CuaHang();
                ch.setMaCuaHang(rs.getString("MaCuaHang"));
                ch.setTenCuaHang(rs.getString("TenCuaHang"));
                ch.setDiaChi(rs.getString("DiaChi"));
                ch.setSoDienThoaiCH(rs.getString("SoDienThoai"));
                list.add(ch);
            }          
        }
        catch(Exception ex){
            throw new RuntimeException (ex);
        }
        System.out.println(list);
        return list;
        
    }

    @Override
    public void insert(CuaHang entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(CuaHang entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public CuaHang selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
    
    
}
