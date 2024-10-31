package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.KhachHang;
import salon.tienich.JdbcHelp;

/**
 * DAO class for KhachHang entity.
 */
public class KhachHangDao {

    private final String insert_sql = "INSERT INTO KHACHHANG (SoDienThoai, HoTen) VALUES (?, ?)";
    private final String update_sql = "UPDATE KHACHHANG SET HoTen = ? WHERE SoDienThoai = ?";
    private final String delete_sql = "DELETE FROM KHACHHANG WHERE SoDienThoai = ?";
    private final String select_all_sql = "SELECT * FROM KHACHHANG";
    private final String select_by_id_sql = "SELECT * FROM KHACHHANG WHERE SoDienThoai = ?";

    public void insert(KhachHang khachHang) {
        JdbcHelp.update(insert_sql, khachHang.getSoDienThoai(), khachHang.getHoTen());
    }

    public void update(KhachHang khachHang) {
        JdbcHelp.update(update_sql, khachHang.getHoTen(), khachHang.getSoDienThoai());
    }

    public void delete(String soDienThoai) {
        JdbcHelp.update(delete_sql, soDienThoai);
    }

    public List<KhachHang> selectAll() {
        return selectBySql(select_all_sql);
    }

    public KhachHang selectById(String soDienThoai) {
        List<KhachHang> result = selectBySql(select_by_id_sql, soDienThoai);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> khachHangs = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                KhachHang khachHang = new KhachHang();
                khachHang.setSoDienThoai(rs.getString("SoDienThoai"));
                khachHang.setHoTen(rs.getString("HoTen"));
                khachHangs.add(khachHang);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return khachHangs;
    }
}
