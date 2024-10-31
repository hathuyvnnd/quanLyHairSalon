/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package NVForm;

import QLForm.QLHome;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import salon.Dao.KhachHangDao;
import salon.Dao.LichHenDao;
import salon.entity.KhachHang;
import salon.entity.LichHen;
import salon.tienich.Auth;
import salon.tienich.MsgBox;
import salon.tienich.XDate;
import salon.ui.Changepass;
import salon.ui.Login;

/**
 *
 * @author trong
 */
public class NVLichhen extends javax.swing.JFrame {

    LichHenDao lhDao = new LichHenDao();
    KhachHangDao khDao = new KhachHangDao();
    int index = -1;
    Date ngayThayDoi = null;
    Date ngayTimThayDoi = null;
    DefaultTableModel model = new DefaultTableModel();
    String checkSDT = "[0-9]{10}";
    String gioHen = "(0[0-9]|1[0-9]):([0-5][0-9])";

    public NVLichhen() {
        initComponents();
        fillTable();

        updateStatusClose();
        lblMaNhanVien.setText(Auth.user.getMaNhanVien());
        String a = "09:00";
        String b = "0971191359";
        if (a.matches(gioHen)) {
            System.out.println("OK");
        } else {
            System.out.println("NOT");
        }

    }

    void fillTable() {
        model = (DefaultTableModel) tblLichHen.getModel();
        model.setRowCount(0);
        try {
            List<LichHen> listLH = lhDao.selectAll();
            System.out.println(listLH);
            for (LichHen lh : listLH) {
//                System.out.println(lh.getNgayHen());
                Object[] row = {
                    lh.getMaLichHen(), lh.getSodienthoaiKH(), lh.getNgayHen(), lh.getGioHen(), lh.getMaNhanVien(), lh.getGhiChu(), lh.getTrangThai() ? "Đã đến" : "Chưa đến"
                };
                System.out.println(row[2]);
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Lỗi dữ liệu fillTable");
        }

    }

    void fillTableFind() {
        model = (DefaultTableModel) tblLichHen.getModel();
        model.setRowCount(0);
        try {
            String ngay = XDate.toString(ngayTimThayDoi, "dd-MM-yyyy");
            Date ngayTim = XDate.toDate(ngay, "dd-MM-yyyy");
            List<LichHen> listNgay = lhDao.selectByDate(ngayTim);
            System.out.println(listNgay);
            for (LichHen lh : listNgay) {
                System.out.println(lh.getNgayHen());
                Object[] row = {
                    lh.getMaLichHen(), lh.getSodienthoaiKH(), lh.getNgayHen(), lh.getGioHen(), lh.getMaNhanVien(), lh.getGhiChu()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Lỗi dữ liệu fillTable Tìm kiếm");
        }
    }

    void setForm(LichHen lh) {
        txtSoDienThoai.setText(String.valueOf(lh.getSodienthoaiKH()));
        KhachHang kh = khDao.selectById(lh.getSodienthoaiKH());
        txtHoTen.setText(kh.getHoTen());
        txtThoiGian.setText(lh.getGioHen());
        txtGhiChu.setText(lh.getGhiChu());
        lblMaNhanVien.setText(lh.getMaNhanVien());
        clLichHen.setDate(lh.getNgayHen());
        lblMaLichHen.setText(String.valueOf(lh.getMaLichHen()));
        rdodaden.setSelected(lh.getTrangThai());
        rdokhongden.setSelected(!lh.getTrangThai());

    }

    boolean checkBieuThuc() {
        if (!txtSoDienThoai.getText().matches(checkSDT)) {
            MsgBox.alert(this, "Chưa đúng định dạng.(Gồm 10 số từ 0-9)");
            return false;
        }
        return true;
    }

    boolean checkBieuThucThoiGian() {
        if (!txtThoiGian.getText().matches(gioHen)) {
            MsgBox.alert(this, "Sai định dạng giờ.(hh:pp)");
            return false;
        }
        return true;
    }

    boolean checkNull() {
        if (txtSoDienThoai.getText().isEmpty() || txtHoTen.getText().isEmpty()) {
            MsgBox.alert(this, "Số điện thoại và họ tên phải nhập đầy đủ.");
            this.requestFocus();
            return false;
        }
        return true;
    }

    boolean checkNullThoiGian() {
        if (txtThoiGian.getText().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập giờ");
            this.requestFocus();
            return false;
        }
        return true;
    }

    LichHen getForm() {
        LichHen lh = new LichHen();
        lh.setSodienthoaiKH(txtSoDienThoai.getText());
        if (lblMaNhanVien.getText().equals(Auth.user.getMaNhanVien())) {
            lh.setMaNhanVien(Auth.user.getMaNhanVien());
        } else {
            lh.setMaNhanVien(lblMaNhanVien.getText());
        }
        lh.setGioHen(txtThoiGian.getText());

        if (ngayThayDoi == null) {
            lh.setNgayHen(clLichHen.getDate());
        } else {
            String ngay = XDate.toString(ngayThayDoi, "dd-MM-yyyy");
            lh.setNgayHen(XDate.toDate(ngay, "dd-MM-yyyy"));
        }
        lh.setGhiChu(txtGhiChu.getText());
        lh.setTrangThai(rdodaden.isSelected());
//        lh.setMaLichHen(Integer.parseInt(lblMaLichHen.getText()));

        return lh;
    }

    void checkKhachHang() {
        if (checkBieuThuc() == true && checkNull() == true) {
            KhachHang kh = khDao.selectById(txtSoDienThoai.getText());
            if (kh == null) {
                kh = new KhachHang();
                kh.setHoTen(txtHoTen.getText());
                kh.setSoDienThoai(txtSoDienThoai.getText());
                khDao.insert(kh);
                MsgBox.alert(this, "Khách hàng mới đã được thêm");

            } else {
                txtHoTen.setText(kh.getHoTen());
            }
        }
    }

    void edit() {
        try {
            Integer maLichHen = (Integer) tblLichHen.getValueAt(this.index, 0);
            LichHen lh = lhDao.selectById(maLichHen);
            if (lh != null) {
                setForm(lh);
            }
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Lỗi dữ liệu.");
        }
    }

    void insert() {
        LichHen lh = getForm();
        lhDao.insert(lh);
        MsgBox.alert(this, "Thêm mới thành công");
        this.fillTable();
    }

    void update() {
        LichHen lh = getForm();
        try {
            lh.setMaLichHen(Integer.parseInt(lblMaLichHen.getText()));
            lhDao.update(lh);
            MsgBox.alert(this, "Cập nhập thành công");
            this.fillTable();

        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Cập nhập thất bại");
        }

    }

    void clearForm() {
        txtSoDienThoai.setText("");
        txtHoTen.setText("");
        lblMaNhanVien.setText(Auth.user.getMaNhanVien());
        lblMaLichHen.setText("");
        txtThoiGian.setText("");
        txtGhiChu.setText("");
        updateStatusClose();
    }

    void updateStatusOpen() {
        txtSoDienThoai.setEnabled(false);
        txtHoTen.setEnabled(false);
        clLichHen.setEnabled(true);
        txtThoiGian.setEnabled(true);
        txtGhiChu.setEnabled(true);

    }

    void updateStatusClose() {
        txtSoDienThoai.setEnabled(true);
        txtHoTen.setEnabled(true);
        clLichHen.setEnabled(false);
        txtThoiGian.setEnabled(false);
        txtGhiChu.setEnabled(false);
        btncapnhat.setEnabled(false);
        btnthem.setEnabled(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        btnthem = new javax.swing.JButton();
        btncapnhat = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        txtThoiGian = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblMaNhanVien = new javax.swing.JLabel();
        btnKiemTra = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        clLichHen = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblMaLichHen = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        rdodaden = new javax.swing.JRadioButton();
        rdokhongden = new javax.swing.JRadioButton();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLichHen = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnTim = new javax.swing.JButton();
        clTimNgay = new com.toedter.calendar.JDateChooser();
        btnHienThiTatCa = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1220, 620));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Số điện thoại");

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane4.setViewportView(txtGhiChu);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Ghi chú:");

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Họ tên khách hàng");

        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoDienThoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoDienThoaiActionPerformed(evt);
            }
        });

        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btncapnhat.setText("Cập nhật");
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlammoi.setText("Làm mới");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        txtThoiGian.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Trạng thái:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("Mã Nhân Viên");

        lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMaNhanVien.setForeground(new java.awt.Color(0, 0, 255));
        lblMaNhanVien.setText("jLabel1");

        btnKiemTra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnKiemTra.setText("Kiểm tra");
        btnKiemTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKiemTraActionPerformed(evt);
            }
        });

        clLichHen.setDateFormatString("dd-MM-yyyy");
        clLichHen.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clLichHenPropertyChange(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("Ngày:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Mã Lịch Hẹn");

        lblMaLichHen.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMaLichHen.setForeground(new java.awt.Color(255, 0, 51));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Thời gian:");

        buttonGroup1.add(rdodaden);
        rdodaden.setText("Đã đến");

        buttonGroup1.add(rdokhongden);
        rdokhongden.setText("Chưa đến");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(btnKiemTra))
                                        .addGap(174, 174, 174)
                                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel12Layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(0, 106, Short.MAX_VALUE))
                                            .addComponent(txtHoTen)))
                                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel15))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(lblMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                            .addComponent(lblMaLichHen, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(66, 66, 66))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                            .addComponent(jLabel17)
                                            .addGap(83, 83, 83)))))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(clLichHen, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel13)))
                        .addGap(0, 47, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdodaden)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdokhongden)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnKiemTra, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblMaNhanVien))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(lblMaLichHen)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(clLichHen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdodaden)
                    .addComponent(rdokhongden))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("QUẢN LÝ LỊCH HẸN ");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblLichHen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblLichHen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã LH", "Số điện thoại KH", "Ngày hẹn", "Giờ hẹn", "Mã Nhân Viên", "Ghi chú", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLichHen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLichHenMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblLichHenMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblLichHen);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnTim.setText("Tìm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        clTimNgay.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clTimNgayPropertyChange(evt);
            }
        });

        btnHienThiTatCa.setText("Hiển thị tất cả");
        btnHienThiTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiTatCaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(clTimNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(btnHienThiTatCa, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clTimNgay, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(btnHienThiTatCa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(443, 443, 443)
                .addComponent(jLabel37)
                .addContainerGap(487, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addGap(18, 33, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        if (checkNullThoiGian() == true && checkBieuThucThoiGian() == true) {
            insert();
            clearForm();
            updateStatusClose();
        }

    }//GEN-LAST:event_btnthemActionPerformed

    private void btnKiemTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKiemTraActionPerformed
        // TODO add your handling code here:
        if (checkNull() == true && checkBieuThuc() == true) {
            checkKhachHang();
            updateStatusOpen();
        }

    }//GEN-LAST:event_btnKiemTraActionPerformed

    private void tblLichHenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichHenMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblLichHenMouseClicked

    private void txtSoDienThoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoDienThoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoDienThoaiActionPerformed

    private void tblLichHenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLichHenMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.index = tblLichHen.rowAtPoint(evt.getPoint());
            Integer maLichHen = (Integer) tblLichHen.getValueAt(this.index, 0);
            edit();
            btnthem.setEnabled(false);
            btncapnhat.setEnabled(true);
            updateStatusOpen();
        }
    }//GEN-LAST:event_tblLichHenMousePressed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        // TODO add your handling code here:
        if (checkNullThoiGian() == true && checkBieuThucThoiGian() == true) {
            update();
            clearForm();
            updateStatusClose();
        }

//        fillTable();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void clLichHenPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clLichHenPropertyChange
        ngayThayDoi = clLichHen.getDate();
    }//GEN-LAST:event_clLichHenPropertyChange

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        // TODO add your handling code here:
        clearForm();
        updateStatusClose();
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:  
        fillTableFind();
    }//GEN-LAST:event_btnTimActionPerformed

    private void clTimNgayPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clTimNgayPropertyChange
        // TODO add your handling code here:
        ngayTimThayDoi = clTimNgay.getDate();
        System.out.println(clTimNgay.getDate());
    }//GEN-LAST:event_clTimNgayPropertyChange

    private void btnHienThiTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiTatCaActionPerformed
        // TODO add your handling code here:
        fillTable();
    }//GEN-LAST:event_btnHienThiTatCaActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        NVHome homeNV = new NVHome();
        homeNV.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

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
            java.util.logging.Logger.getLogger(NVLichhen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NVLichhen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NVLichhen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NVLichhen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NVLichhen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHienThiTatCa;
    private javax.swing.JButton btnKiemTra;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnthem;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser clLichHen;
    private com.toedter.calendar.JDateChooser clTimNgay;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblMaLichHen;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JRadioButton rdodaden;
    private javax.swing.JRadioButton rdokhongden;
    private javax.swing.JTable tblLichHen;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtThoiGian;
    // End of variables declaration//GEN-END:variables
}
