package salon.Dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import salon.entity.UuDai;
import salon.tienich.JdbcHelp;

public class UuDaiDao extends SalonDao<UuDai, String> {

    private final String insert_sql = "INSERT INTO chuongtrinhuudai (mauudai, tenuudai, ngaybatdau, ngayketthuc, noidung, giamgia) VALUES (?, ?, ?, ?, ?, ?)";
    private final String update_sql = "UPDATE chuongtrinhuudai SET tenuudai = ?, ngaybatdau = ?, ngayketthuc = ?, noidung = ?, giamgia = ? WHERE mauudai = ?";
    private final String delete_sql = "DELETE FROM chuongtrinhuudai WHERE mauudai = ?";
    private final String select_all_sql = "SELECT * FROM chuongtrinhuudai";
    private final String select_by_id_sql = "SELECT * FROM chuongtrinhuudai WHERE mauudai = ?";
    private final String select_by_mud_sql = "select * from  chuongtrinhuudai where mauudai=?";

    @Override
    public void insert(UuDai entity) {
        JdbcHelp.update(insert_sql, entity.getMaUuDai(), entity.getTenUuDai(), entity.getNgayBatDau(), entity.getNgayKetThuc(), entity.getNoiDung(), entity.getGiamGia());
    }

    @Override
    public void update(UuDai entity) {
        JdbcHelp.update(update_sql, entity.getTenUuDai(), entity.getNgayBatDau(), entity.getNgayKetThuc(), entity.getNoiDung(), entity.getGiamGia(), entity.getMaUuDai());
    }

    @Override
    public void delete(String id) {
        JdbcHelp.update(delete_sql, id);
    }

    @Override
    public List<UuDai> selectAll() {
        return selectBySql(select_all_sql);
    }

    @Override
    public UuDai selectById(String id) {
        List<UuDai> result = selectBySql(select_by_id_sql, id);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<UuDai> selectBySql(String sql, Object... args) {
        List<UuDai> list = new ArrayList<>();
        //     String sql = "SELECT [MaUuDai], [TenUuDai], [NgayBatDau], [NgayKetThuc], [NoiDung], [GiamGia] FROM [DUAN1].[dbo].[CHUONGTRINHUUDAI]";
        try {
            ResultSet rs = JdbcHelp.query(sql, args);
            while (rs.next()) {
                UuDai ud = new UuDai();
                ud.setMaUuDai(rs.getString("MaUuDai"));
                ud.setTenUuDai(rs.getString("TenUuDai"));
                ud.setNgayBatDau(rs.getDate("NgayBatDau"));
                ud.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                ud.setNoiDung(rs.getString("NoiDung"));
                ud.setGiamGia(rs.getFloat("GiamGia"));
                list.add(ud);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<UuDai> selectbykeyword(String keyword) {
        String sql = "select * from  chuongtrinhuudai where mauudai like ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }

    public List<UuDai> selecByMaUuDai(String maud) {
        return this.selectBySql(select_by_mud_sql, maud);
    }
}
