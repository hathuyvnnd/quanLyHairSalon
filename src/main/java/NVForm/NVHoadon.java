/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package NVForm;

import QLForm.QLHome;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import salon.Dao.ChiTietHoaDonDao;
import salon.Dao.DichVuDao;
import salon.Dao.HoaDonDao;
import salon.Dao.KhachHangDao;
import salon.Dao.LichHenDao;
import salon.Dao.UuDaiDao;
import salon.entity.ChiTietHoaDon;
import salon.entity.DichVu;
import salon.entity.HoaDon;
import salon.entity.KhachHang;
import salon.entity.LichHen;
import salon.entity.UuDai;
import salon.tienich.Auth;
import salon.tienich.MsgBox;
import salon.tienich.XDate;
import salon.ui.Changepass;
import salon.ui.Login;

/**
 *
 * @author trong
 */
public class NVHoadon extends javax.swing.JFrame {

    int row = -1;
    int row2 = -1;
    int tongtien = 0;
    float thanhtien = 0;
    List<DichVu> dichvu;
    DichVuDao dvdao = new DichVuDao();
    UuDaiDao uddao = new UuDaiDao();
    List<ChiTietHoaDon> chitiethoadon = new ArrayList<>();
    ChiTietHoaDonDao cthddao = new ChiTietHoaDonDao();
    HoaDonDao hddao = new HoaDonDao();
    KhachHangDao khDao = new KhachHangDao();
    LichHenDao lhDao = new LichHenDao();

    /**
     * Creates new form Manager
     */
    public NVHoadon() {
        initComponents();
        this.setLocationRelativeTo(null);
        init();
    }

    void init() {
        fillTableDichVu();
        fillTableUuDai();
    }

    void fillTableDichVu() {
        DefaultTableModel model = (DefaultTableModel) tbldichvu.getModel();
        model.setRowCount(0);
        try {
            dichvu = dvdao.selectAll();
            for (DichVu dv : dichvu) {
                if (dv.isTrangThai()) {
                    Object[] ob = {dv.getMaDichVu(), dv.getTenDichVu(), dv.getGiaDichVu(), dv.getMoTa()};
                    model.addRow(ob);
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void fillTableUuDai() {
        DefaultTableModel model = (DefaultTableModel) tbluudai.getModel();
        model.setRowCount(0);
        try {
            List<UuDai> uudai = uddao.selectAll();
            for (UuDai ud : uudai) {
                Object[] ob = {ud.getMaUuDai(), ud.getTenUuDai(), ud.getNgayBatDau(), ud.getNgayKetThuc(), ud.getNoiDung(), ud.getGiamGia()};
                model.addRow(ob);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void addDichVu() {
        String madv = (String) tbldichvu.getValueAt(row, 0);
        int soluong = 1;
        int i = -1;
        for (ChiTietHoaDon cthd : chitiethoadon) {
            i++;
            if (madv.equals(cthd.getMaDichVu())) {
                soluong = cthd.getSoLuong() + 1;
                cthd.setSoLuong(soluong);
                chitiethoadon.set(i, cthd);
                return;
            }
        }
        ChiTietHoaDon a = new ChiTietHoaDon();
        a.setMaDichVu(madv);
        a.setSoLuong(soluong);
        chitiethoadon.add(a);
    }

    void removeDichVu(int index) {
        String madv = (String) tbldichvudachon.getValueAt(index, 0);
        int soluong;
        int i = -1;
        for (ChiTietHoaDon cthd : chitiethoadon) {
            i++;
            if (madv.equals(cthd.getMaDichVu())) {
                soluong = cthd.getSoLuong() - 1;
                if (soluong == 0) {
                    chitiethoadon.remove(i);
                    return;
                } else {
                    cthd.setSoLuong(soluong);
                    chitiethoadon.set(i, cthd);
                    return;
                }
            }
        }
    }

    void fillTableDVDC() {
        DefaultTableModel model = (DefaultTableModel) tbldichvudachon.getModel();
        model.setRowCount(0);
        try {
            for (ChiTietHoaDon ct : chitiethoadon) {
                Object[] ob = {ct.getMaDichVu(), ct.getSoLuong()};
                model.addRow(ob);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void setTongTien() {
        tongtien = 0;
        for (ChiTietHoaDon ct : chitiethoadon) {
            tongtien = tongtien + (dvdao.getGia(ct.getMaDichVu()) * ct.getSoLuong());
        }
        lbltongtien.setText(String.valueOf(tongtien));
    }

    HoaDon getHoaDon() {
        String sodienthoai = txtsodienthoai.getText();
        String ngay = XDate.toString(new Date(), "yyyy-MM-dd");
        String manhanvien = Auth.user.getMaNhanVien();
        String mauudai = txtuudai.getText();
        String hinhthucthanhtoan = (String) cboHinhthucthanhtoan.getSelectedItem();
        HoaDon hd = new HoaDon(sodienthoai, XDate.toDate(ngay, "yyyy-MM-dd"), manhanvien, mauudai, hinhthucthanhtoan);
        return hd;
    }

    boolean validateInsert() {
        if (txtsodienthoai.getText().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập đủ thông tin");
            return false;
        }
        if (txthoten.getText().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập đủ thông tin");
            return false;
        }
        if (chitiethoadon.isEmpty()) {
            MsgBox.alert(this, "Chưa chọn dịch vụ sử dụng");
            return false;
        }
        try {
            int sdt = Integer.valueOf(txtsodienthoai.getText());
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Số điện thoại nhập sai định dạng");
            return false;
        }
        if (!txtuudai.getText().isEmpty()) {
            if (uddao.selectById(txtuudai.getText()) == null) {
                MsgBox.alert(this, "Mã ưu đãi không tồn tại");
                return false;
            }
        }
        if (!Auth.isLogin()) {
            MsgBox.alert(this, "Bạn chưa đăng nhập");
            return false;
        }
        return true;
    }

    void insert() {
        if (validateInsert()) {
            try {
                if (txtuudai.getText().isEmpty()) {
                    //insert hoa don
                    HoaDon hd = getHoaDon();
                    hd.setMaUuDai(null);
                    if (khDao.selectById(hd.getSdtKH()) == null) {
                        KhachHang kh = new KhachHang(txthoten.getText(), txtsodienthoai.getText());
                        khDao.insert(kh);
                        hddao.insert(hd);
                    } else {
                        hddao.insert(hd);
                    }

                    //insert chitiethoadon
                    int mahoadon = hddao.selectAll().get(hddao.selectAll().size() - 1).getMaHoaDon();
                    for (ChiTietHoaDon ct : chitiethoadon) {
                        ChiTietHoaDon a = new ChiTietHoaDon();
                        a.setMaDichVu(ct.getMaDichVu());
                        a.setMaHoaDon(mahoadon);
                        a.setSoLuong(ct.getSoLuong());
                        cthddao.insert(a);
                    }
                    List<LichHen> lichhen = lhDao.selectByDate(hd.getNgay());
                    for (LichHen lh : lichhen) {
                        if (lh.getSodienthoaiKH().equals(hd.getSdtKH())) {
                            lh.setTrangThai(true);
                            lhDao.update(lh);
                        }
                    }
                    MsgBox.alert(this, "Đã tạo thành công");
                    thanhtien = tongtien;
                } else {
                    //insert hoa don
                    HoaDon hd = getHoaDon();
                    if (khDao.selectById(hd.getSdtKH()) == null) {
                        KhachHang kh = new KhachHang(txthoten.getText(), txtsodienthoai.getText());
                        khDao.insert(kh);
                        hddao.insert(hd);
                    } else {
                        hddao.insert(hd);
                    }

                    //insert chitiethoadon
                    int mahoadon = hddao.selectAll().get(hddao.selectAll().size() - 1).getMaHoaDon();
                    for (ChiTietHoaDon ct : chitiethoadon) {
                        ChiTietHoaDon a = new ChiTietHoaDon();
                        a.setMaDichVu(ct.getMaDichVu());
                        a.setMaHoaDon(mahoadon);
                        a.setSoLuong(ct.getSoLuong());
                        cthddao.insert(a);
                    }
                    List<LichHen> lichhen = lhDao.selectByDate(hd.getNgay());
                    for (LichHen lh : lichhen) {
                        if (lh.getSodienthoaiKH().equals(hd.getSdtKH())) {
                            lh.setTrangThai(true);
                            lhDao.update(lh);
                        }
                    }
                    MsgBox.alert(this, "Đã tạo thành công");
                    thanhtien = tongtien * uddao.selectById(txtuudai.getText()).getGiamGia();
                }
                lblthanhtien.setText(String.valueOf(thanhtien));
                btnTao.setEnabled(false);
            } catch (Exception e) {
                System.out.println(e);
                MsgBox.alert(this, "Lỗi!");
            }
        }
    }

    void clearForm() {
        chitiethoadon.clear();
        fillTableDVDC();
        row = -1;
        tongtien = 0;
        thanhtien = 0;
        txtsodienthoai.setText(null);
        txthoten.setText(null);
        txtuudai.setText(null);
        cboHinhthucthanhtoan.setSelectedIndex(0);
        lblthanhtien.setText(String.valueOf(tongtien));
        lbltongtien.setText(String.valueOf(thanhtien));
        btnTao.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldichvu = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbluudai = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        txthoten = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtsodienthoai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbldichvudachon = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbltongtien = new javax.swing.JLabel();
        lblthanhtien = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtuudai = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        btnTao = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        cboHinhthucthanhtoan = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1220, 620));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("QUẢN LÝ HÓA ĐƠN");

        tbldichvu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Giá", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbldichvu.setDoubleBuffered(true);
        tbldichvu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldichvuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbldichvu);
        if (tbldichvu.getColumnModel().getColumnCount() > 0) {
            tbldichvu.getColumnModel().getColumn(0).setMinWidth(60);
            tbldichvu.getColumnModel().getColumn(0).setMaxWidth(60);
            tbldichvu.getColumnModel().getColumn(1).setMinWidth(110);
            tbldichvu.getColumnModel().getColumn(1).setMaxWidth(110);
        }

        jTabbedPane1.addTab("BẢNG DỊCH VỤ", jScrollPane1);

        tbluudai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã", "Tên", "Ngày bắt đầu", "Ngày kết thúc", "Nội dung", "Giảm giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbluudai.setEnabled(false);
        jScrollPane2.setViewportView(tbluudai);
        if (tbluudai.getColumnModel().getColumnCount() > 0) {
            tbluudai.getColumnModel().getColumn(0).setMinWidth(60);
            tbluudai.getColumnModel().getColumn(0).setMaxWidth(60);
            tbluudai.getColumnModel().getColumn(1).setMinWidth(110);
            tbluudai.getColumnModel().getColumn(1).setMaxWidth(110);
            tbluudai.getColumnModel().getColumn(5).setMinWidth(60);
            tbluudai.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("BẢNG ƯU ĐÃI", jPanel1);

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txthoten.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Thành tiền:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Số điện thoại khách hàng:");

        txtsodienthoai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Họ tên khách hàng:");

        tbldichvudachon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã dịch vụ", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbldichvudachon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldichvudachonMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbldichvudachon);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Dịch vụ đã chọn:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Tổng tiền:");

        lbltongtien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbltongtien.setText("0");

        lblthanhtien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblthanhtien.setForeground(new java.awt.Color(255, 0, 0));
        lblthanhtien.setText("0");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("Voucher:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("Hình thức thành toán:");

        btnTao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTao.setText("Tạo");
        btnTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoActionPerformed(evt);
            }
        });

        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlammoi.setText("Làm mới");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        cboHinhthucthanhtoan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboHinhthucthanhtoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(btnTao, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtsodienthoai)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblthanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(lbltongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtuudai, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(18, 18, 18)
                        .addComponent(cboHinhthucthanhtoan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsodienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboHinhthucthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txtuudai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(lbltongtien))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblthanhtien))
                .addGap(37, 37, 37)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTao, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 1203, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 171, 190));

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/menu_icon.png"))); // NOI18N

        jMenuItem3.setText("Trang chủ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem1.setText("Đổi mật khẩu");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Đăng xuất");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbldichvuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldichvuMouseClicked
        row = tbldichvu.getSelectedRow();
        addDichVu();
        fillTableDVDC();
        setTongTien();
        // TODO add your handling code here:
    }//GEN-LAST:event_tbldichvuMouseClicked

    private void tbldichvudachonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldichvudachonMouseClicked
        row2 = tbldichvudachon.getSelectedRow();
        removeDichVu(row2);
        fillTableDVDC();
        setTongTien();
        // TODO add your handling code here:
    }//GEN-LAST:event_tbldichvudachonMouseClicked

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        clearForm();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void btnTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoActionPerformed
        insert();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTaoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Changepass dmk = new Changepass();
        dmk.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Login lg = new Login();
        lg.setVisible(true);
        Auth.clear();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        NVHome homeNV = new NVHome();
        homeNV.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NVHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NVHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NVHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NVHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NVHoadon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTao;
    private javax.swing.JButton btnlammoi;
    private javax.swing.JComboBox<String> cboHinhthucthanhtoan;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblthanhtien;
    private javax.swing.JLabel lbltongtien;
    private javax.swing.JTable tbldichvu;
    private javax.swing.JTable tbldichvudachon;
    private javax.swing.JTable tbluudai;
    private javax.swing.JTextField txthoten;
    private javax.swing.JTextField txtsodienthoai;
    private javax.swing.JTextField txtuudai;
    // End of variables declaration//GEN-END:variables
}
