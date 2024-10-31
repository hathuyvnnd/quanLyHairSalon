/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QLForm;

import NVForm.NVHome;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import salon.Dao.ChiPhiDao;
import salon.Dao.CuaHangDao;
import salon.Dao.PhieuchiDao;
import salon.entity.ChiPhi;
import salon.entity.CuaHang;
import salon.entity.Phieuchi;
import salon.tienich.Auth;
import salon.tienich.MsgBox;
import salon.tienich.XDate;
import salon.ui.Changepass;
import salon.ui.Login;

/**
 *
 * @author trong
 */
public class QLPhieuchi extends javax.swing.JFrame {

    int row = -1;
    ChiPhiDao lcpdao = new ChiPhiDao();
    PhieuchiDao dao = new PhieuchiDao();
    CuaHangDao chdao = new CuaHangDao();

    /**
     * Creates new form Manager
     */
    public QLPhieuchi() {
        initComponents();
        init();
    }

    void init() {
        this.filltable();
        this.fillcomboboxcuahang();
        btnupdate.setEnabled(false);
        btntao.setEnabled(true);
        txtmaphieuchi.setEditable(true);
    }

    void fillcomboboxcuahang() {
        JComboBox<CuaHang> txtmacuahang = new JComboBox<>();
        DefaultComboBoxModel<CuaHang> model = (DefaultComboBoxModel<CuaHang>) txtmacuahang.getModel();
        model.removeAllElements();

//        try {
        List<CuaHang> list = chdao.selectAll();
        System.out.println(list);
        if (!list.isEmpty()) {
            for (CuaHang cd : list) {
                model.addElement(cd);
            }
        } else {

            MsgBox.alert(this, "Không có cửa hàng nào");
        }
        // } catch (Exception e) {
        //      System.out.println(e);
        //        MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        //  }
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblchiphi.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tblloaichiphi.getModel();
        model.setRowCount(0); // Xóa hết dữ liệu cũ
        model2.setRowCount(0);

        try {
            List<Phieuchi> list = dao.selectAll();
            List<ChiPhi> list2 = lcpdao.selectAll(); // Đổi từ List<Phieuchi> sang List<ChiPhi>

            // Thêm dữ liệu vào tblchiphi
            for (Phieuchi pc : list) {
                Object[] row = {pc.getMaPhieuChi(), pc.getMaCuaHang(), pc.getMaChiPhi(),
                    XDate.toString(pc.getNgay(), "dd-MM-yyyy"),
                    pc.getThanhTien()};
                model.addRow(row);
            }

            // Thêm dữ liệu vào tblloaichiphi
            for (ChiPhi cp : list2) {
                Object[] row2 = {cp.getMaChiPhi(), cp.getTenChiPhi(), cp.getMoTa()}; // Sửa lại tên phương thức và trường dữ liệu tương ứng với ChiPhi
                model2.addRow(row2);
            }
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void setform(Phieuchi pc) {

        txtmaphieuchi.setText(pc.getMaCuaHang());
        txtmachiphi.setText(pc.getMaChiPhi());
        txtngay.setText(XDate.toString(pc.getNgay(), "dd-MM-yyyy"));
        // Đặt số tiền
        txtchiphi.setText(Integer.toString(pc.getThanhTien()));
    }

    Phieuchi getform() {
        Phieuchi pc = new Phieuchi();

        // Gán giá trị vào đối tượng Phieuchi
        pc.setMaPhieuChi(txtmaphieuchi.getText());
        pc.setNgay(XDate.toDate(txtngay.getText(), "dd-MM-yyyy"));
        pc.setMaCuaHang(cboMaCuaHang.getSelectedItem().toString());
        pc.setMaChiPhi(txtmachiphi.getText());

        pc.setThanhTien(Integer.parseInt(txtchiphi.getText()));

        return pc;
    }

    void insert() {
        String maphieuchi = txtmaphieuchi.getText().trim();
        String machiphi = txtmachiphi.getText().trim();
        String ngay = txtngay.getText().trim();
        String chiphi = txtchiphi.getText().trim();

        // Kiểm tra xem các trường có đúng là cần thiết hay không
        if (maphieuchi.isEmpty() || machiphi.isEmpty() || ngay.isEmpty() || chiphi.isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập đầy đủ thông tin");
            return;
        }

        // Tạo đối tượng Phieuchi từ form
        Phieuchi pc = getform();

        try {
            dao.insert(pc); // Thêm vào cơ sở dữ liệu
            filltable();    // Cập nhật bảng sau khi thêm thành công
            // clearForm();    // Xóa form sau khi thêm thành công (nếu cần)
            MsgBox.alert(this, "Thêm thành công");

        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm thất bại");
        }

    }

    void update() {
        Phieuchi pc = getform();
        try {
            dao.update(pc);
            this.filltable();
            MsgBox.alert(this, "Cập nhật thành công");

        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Cập nhật thất bại");
        }

    }

    private void clearForm() {

        // Xóa nội dung các trường nhập liệu
        txtmaphieuchi.setText("");
        txtmachiphi.setText("");
        txtngay.setText("");
        txtchiphi.setText(""); // Chọn lại phần tử đầu tiên trong combobox

        // Cập nhật biến row
        row = -1;
        btnupdate.setEnabled(false);

        btntao.setEnabled(true);
        txtmaphieuchi.setEnabled(true);
    }

    boolean validateData() {
        if (txtmaphieuchi.getText().trim().isEmpty() || txtmachiphi.getText().trim().isEmpty()
                || txtngay.getText().trim().isEmpty()
                || txtchiphi.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        if (!isDateValid(txtngay.getText().trim(), "dd-MM-yyyy")) {
            MsgBox.alert(this, "Ngày tháng không đúng định dạng (dd-MM-yyyy)");
            return false;
        }

        try {
            float chiphi = Float.parseFloat(txtchiphi.getText().trim());
            if (chiphi < 1) {
                MsgBox.alert(this, "Chi phí không hợp lệ");
                return false;
            }
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Chi phí không hợp lệ");
            return false;
        }

        return true;
    }

    boolean isDateValid(String dateStr, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
            LocalDate localDate = LocalDate.parse(dateStr, formatter);
            int day = localDate.getDayOfMonth();
            int month = localDate.getMonthValue();
            int year = localDate.getYear();

            if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900 || year > 2100) {
                //    MsgBox.alert(this, "Ngày tháng không hợp lệ hoặc định dạng ngày không đúng (" + format + ").");
                return false;
            }

            return true;
        } catch (Exception e) {
            // MsgBox.alert(this, "Ngày không hợp lệ hoặc định dạng ngày không đúng (" + format + ").");
            return false;
        }
    }

    void updateStatus() {

        txtmaphieuchi.setEditable(true);
        btntao.setEnabled(true);
        btnupdate.setEnabled(false);

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
        jPanel12 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        btntao = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        txtmachiphi = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtngay = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtchiphi = new javax.swing.JTextField();
        cboMaCuaHang = new javax.swing.JComboBox<>();
        txtmaphieuchi = new javax.swing.JTextField();
        btnupdate = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblchiphi = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblloaichiphi = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1250, 650));
        setMinimumSize(new java.awt.Dimension(1250, 650));
        setPreferredSize(new java.awt.Dimension(1250, 650));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel34.setText("Mã cửa hàng:");

        btntao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntao.setText("Tạo");
        btntao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntaoActionPerformed(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel38.setText("Mã chi phí:");

        txtmachiphi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtmachiphi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmachiphiActionPerformed(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setText("Ngày:");

        txtngay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtngay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtngayActionPerformed(evt);
            }
        });

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel40.setText("Chi phí:");

        txtchiphi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtchiphi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtchiphiActionPerformed(evt);
            }
        });

        cboMaCuaHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboMaCuaHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CH01", "CH02" }));
        cboMaCuaHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaCuaHangActionPerformed(evt);
            }
        });

        txtmaphieuchi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaphieuchiActionPerformed(evt);
            }
        });

        btnupdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnupdate.setText("Cập nhật");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlammoi.setText("Làm mới");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setText("Mã phiếu chi:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel39)
                                .addComponent(txtngay, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel40)
                                .addComponent(txtchiphi, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel38)
                        .addComponent(jLabel34)
                        .addComponent(txtmachiphi)
                        .addComponent(cboMaCuaHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtmaphieuchi, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(btntao, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnupdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnlammoi)))
                .addGap(93, 93, 93))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmaphieuchi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMaCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmachiphi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtchiphi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntao, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("TẠO PHIẾU CHI");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblchiphi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblchiphi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã phiếu chi", "Mã cửa hàng", "Mã chi phí", "Ngày", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblchiphi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblchiphiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblchiphi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Bảng phiếu chi", jPanel4);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblloaichiphi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblloaichiphi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã chi phí", "Tên chi phí", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblloaichiphi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblloaichiphiMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblloaichiphi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Bảng loại chi phí", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 1238, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 26, Short.MAX_VALUE))
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

        jMenuItem5.setText("Đổi mật khẩu");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("Đăng xuất");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

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
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btntaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntaoActionPerformed
        // TODO add your handling code here:
        if (validateData()) {
            insert();
            clearForm();
            updateStatus();

        }
    }//GEN-LAST:event_btntaoActionPerformed

    private void cboMaCuaHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaCuaHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaCuaHangActionPerformed

    private void txtmaphieuchiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmaphieuchiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmaphieuchiActionPerformed

    private void txtmachiphiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmachiphiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmachiphiActionPerformed

    private void txtngayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtngayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtngayActionPerformed

    private void txtchiphiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtchiphiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtchiphiActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        if (validateData()) {
            update();
            clearForm();
            txtmaphieuchi.setEditable(true);

        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        // TODO add your handling code here:

        clearForm();

        updateStatus();
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void tblchiphiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblchiphiMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblchiphi.getSelectedRow();
        txtmaphieuchi.setEditable(false);

        if (selectedRow != -1) {
            String MaPhieuChi = tblchiphi.getValueAt(selectedRow, 0).toString();
            String MaCuaHang = tblchiphi.getValueAt(selectedRow, 1).toString();
            String MaChiPhi = tblchiphi.getValueAt(selectedRow, 2).toString();
            String Ngay = tblchiphi.getValueAt(selectedRow, 3).toString();
            String ChiPhi = tblchiphi.getValueAt(selectedRow, 4).toString();

            txtmaphieuchi.setText(MaPhieuChi);
            cboMaCuaHang.setSelectedItem(MaCuaHang);
            txtmachiphi.setText(MaChiPhi);
            txtngay.setText(Ngay);
            txtchiphi.setText(ChiPhi);
            row = selectedRow;
            btnupdate.setEnabled(true);
        }
        btntao.setEnabled(false);
    }//GEN-LAST:event_tblchiphiMouseClicked

    private void tblloaichiphiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblloaichiphiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblloaichiphiMouseClicked

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        QLHome homeQL = new QLHome();
        homeQL.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Changepass dmk = new Changepass();
        dmk.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        Login lg = new Login();
        lg.setVisible(true);
        Auth.clear();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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
            java.util.logging.Logger.getLogger(QLPhieuchi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLPhieuchi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLPhieuchi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLPhieuchi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new QLPhieuchi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btntao;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<String> cboMaCuaHang;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable tblchiphi;
    private javax.swing.JTable tblloaichiphi;
    private javax.swing.JTextField txtchiphi;
    private javax.swing.JTextField txtmachiphi;
    private javax.swing.JTextField txtmaphieuchi;
    private javax.swing.JTextField txtngay;
    // End of variables declaration//GEN-END:variables
}
