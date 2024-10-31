/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QLForm;

import NVForm.NVHome;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import salon.Dao.HoaDonDao;
import salon.entity.HoaDon;
import salon.tienich.Auth;
import salon.tienich.MsgBox;
import salon.tienich.XDate;
import salon.ui.Changepass;
import salon.ui.Login;

/**
 *
 * @author trong
 */
public class QLHoadon extends javax.swing.JFrame {

    HoaDonDao dao = new HoaDonDao();
    int row = -1;
    Date ngayTimThayDoi = null;
    DefaultTableModel model = new DefaultTableModel();

    /**
     * Creates new form Manager
     */
    public QLHoadon() {
        initComponents();
        init();
        btncapnhat.setEnabled(false);
    }

    void init() {
        this.filltable();

    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblhoadon.getModel();
        model.setRowCount(0); //xoa tat ca cac hang tren table
        try {
            List<HoaDon> list = dao.selectAll();
            for (HoaDon nv : list) {
                Object[] row = {nv.getMaHoaDon(), nv.getSdtKH(), nv.getNgay(), nv.getMaNhanVien(), nv.getMaUuDai(), nv.getHinhThucThanhToan()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }

    }

    void setform(HoaDon sp) {
        txtmakhachhang.setText(String.valueOf(sp.getSdtKH()));
        txtngay.setText(XDate.toString(sp.getNgay()));
        txtmanhanvien.setText(sp.getMaNhanVien());
        if (sp.getMaUuDai() == null) {
            txtmauudai.setText("");
        } else {
            txtmauudai.setText(sp.getMaUuDai());
        }

        cbohinhthucthanhtoan.setSelectedItem(sp.getHinhThucThanhToan());
    }

    HoaDon getform() {
        HoaDon sp = new HoaDon();
        sp.setMaHoaDon(Integer.valueOf(txtmahoadon.getText()));
        sp.setSdtKH(txtmakhachhang.getText());
        txtngay.setText(XDate.toString(sp.getNgay()));
        sp.setMaNhanVien(txtmanhanvien.getText());
        sp.setMaUuDai((String) txtmauudai.getText());
        sp.setHinhThucThanhToan((String) cbohinhthucthanhtoan.getSelectedItem());
        return sp;
    }

    void clearform() {
        HoaDon sp = new HoaDon();
        this.setform(sp);
        this.row = -1;
//        this.updatestatus();
    }

    void update() {
        HoaDon sp = getform();
        try {
            dao.update(sp);
            this.filltable();
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập  nhật thất bại");
        }

    }

    void upst() {
        if (row == -1) {
            btncapnhat.setEnabled(false);
            txtmahoadon.setEnabled(true);
        } else {
            btncapnhat.setEnabled(true);
            txtmahoadon.setEnabled(false);
        }
    }

    void fillTableFind() {
        model = (DefaultTableModel) tblhoadon.getModel();
        model.setRowCount(0);
        try {
            String ngay = XDate.toString(ngayTimThayDoi, "dd-MM-yyyy");
            Date ngayTim = XDate.toDate(ngay, "dd-MM-yyyy");
            List<HoaDon> listNgay = dao.selectByDate(ngayTim);
            System.out.println(listNgay);
            for (HoaDon nv : listNgay) {
                System.out.println(nv.getNgay());
                Object[] row = {
                    nv.getMaHoaDon(), nv.getSdtKH(), nv.getNgay(), nv.getMaNhanVien(), nv.getMaUuDai(), nv.getHinhThucThanhToan()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Lỗi dữ liệu fillTable Tìm kiếm");
        }
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
        btncapnhat = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtmahoadon = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbohinhthucthanhtoan = new javax.swing.JComboBox<>();
        txtngay = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtmakhachhang = new javax.swing.JTextField();
        txtmanhanvien = new javax.swing.JTextField();
        txtmauudai = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblhoadon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btntim = new javax.swing.JButton();
        clTimNgay = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1220, 620));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btncapnhat.setText("Cập nhật");
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Hình thức thanh toán:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Ưu đãi:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã hóa đơn:");

        txtmahoadon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtmahoadon.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Số điện thoại");

        cbohinhthucthanhtoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Nhân viên");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Ngày:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbohinhthucthanhtoan, javax.swing.GroupLayout.Alignment.LEADING, 0, 508, Short.MAX_VALUE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtmauudai, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtngay, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtmahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addGap(92, 92, 92)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel10)
                                        .addGap(110, 110, 110))
                                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtmanhanvien, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtmakhachhang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtmahoadon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmakhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmanhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtmauudai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbohinhthucthanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("QUẢN LÝ HÓA ĐƠN");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblhoadon.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblhoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã hóa đơn", "Số điện thoại", "Ngày", "Mã nhân viên", "Mã ưu đãi", "Hình thức thanh toán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblhoadon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblhoadonMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblhoadon);
        if (tblhoadon.getColumnModel().getColumnCount() > 0) {
            tblhoadon.getColumnModel().getColumn(0).setMinWidth(80);
            tblhoadon.getColumnModel().getColumn(0).setMaxWidth(80);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btntim.setText("Tìm");
        btntim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimActionPerformed(evt);
            }
        });

        clTimNgay.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clTimNgayPropertyChange(evt);
            }
        });

        jButton1.setText("Hiển thị tất cả");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(clTimNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btntim, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clTimNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btntim)
                        .addComponent(jButton1)))
                .addGap(36, 36, 36))
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
                    .addComponent(jScrollPane3)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(443, 443, 443)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap(37, Short.MAX_VALUE))
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

        jMenuItem4.setText("Đổi mật khẩu");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Đăng xuất");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

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

    private void tblhoadonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblhoadonMouseClicked
        // TODO add your handling code here:
        int selectedRow = tblhoadon.getSelectedRow();

        if (selectedRow != -1) {
            String mahoadon = tblhoadon.getValueAt(selectedRow, 0).toString();
            String makh = tblhoadon.getValueAt(selectedRow, 1).toString();
            String ngay = tblhoadon.getValueAt(selectedRow, 2).toString();
            String manv = tblhoadon.getValueAt(selectedRow, 3).toString();
            String uudai = (String) tblhoadon.getValueAt(selectedRow, 4);
            String hinhthuc = (String) tblhoadon.getValueAt(selectedRow, 5);

            txtmahoadon.setText(mahoadon);
            txtmakhachhang.setText(makh);
            txtngay.setText(ngay);
            txtmanhanvien.setText(manv);
            if (uudai == null) {
                txtmauudai.setText("");
            } else {
                txtmauudai.setText(uudai);
            }
            cbohinhthucthanhtoan.setSelectedItem(hinhthuc);

            row = selectedRow;
            upst();
        }
    }//GEN-LAST:event_tblhoadonMouseClicked

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void clTimNgayPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clTimNgayPropertyChange
        // TODO add your handling code here:
        ngayTimThayDoi = clTimNgay.getDate();
        System.out.println(clTimNgay.getDate());
    }//GEN-LAST:event_clTimNgayPropertyChange

    private void btntimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimActionPerformed
        // TODO add your handling code here:
        fillTableFind();
    }//GEN-LAST:event_btntimActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        filltable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

            QLHome homeQL = new QLHome();
            homeQL.setVisible(true);
            this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        Changepass dmk = new Changepass();
        dmk.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Login lg = new Login();
        lg.setVisible(true);
        Auth.clear();
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

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
            java.util.logging.Logger.getLogger(QLHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLHoadon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLHoadon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btntim;
    private javax.swing.JComboBox<String> cbohinhthucthanhtoan;
    private com.toedter.calendar.JDateChooser clTimNgay;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblhoadon;
    private javax.swing.JTextField txtmahoadon;
    private javax.swing.JTextField txtmakhachhang;
    private javax.swing.JTextField txtmanhanvien;
    private javax.swing.JTextField txtmauudai;
    private javax.swing.JTextField txtngay;
    // End of variables declaration//GEN-END:variables
}
