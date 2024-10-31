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
import javax.swing.table.DefaultTableModel;
import salon.Dao.UuDaiDao;
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
public class QLUuDai extends javax.swing.JFrame {

    int selectedRow = -1;
    int row = -1;
    UuDaiDao dao = new UuDaiDao();

    public QLUuDai() {
        initComponents();
        init();
    }

    void init() {
        this.filltable();
        updateStatus();
    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tbluudai.getModel();
        model.setRowCount(0);
        try {
            String keyword = txttimkiem.getText();
            List<UuDai> list = dao.selectbykeyword(keyword);
            for (UuDai ud : list) {
                Object[] row = {ud.getMaUuDai(), ud.getTenUuDai(), XDate.toString(ud.getNgayBatDau(), "dd-MM-yyyy"),
                    XDate.toString(ud.getNgayKetThuc(), "dd-MM-yyyy"), ud.getNoiDung(), ud.getGiamGia()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void setForm(UuDai ud) {
        txtmauudai.setText(ud.getMaUuDai());
        txttenuudai.setText(ud.getTenUuDai());
        txtngaybatdau.setText(XDate.toString(ud.getNgayBatDau(), "dd-MM-yyyy"));
        txtngayketthuc.setText(XDate.toString(ud.getNgayKetThuc(), "dd-MM-yyyy"));
        txtnoidung.setText(ud.getNoiDung());
        txtgiamgia.setText(String.valueOf(ud.getGiamGia()));
    }

    UuDai getForm() {
        UuDai ud = new UuDai();
        ud.setMaUuDai(txtmauudai.getText());
        ud.setTenUuDai(txttenuudai.getText());
        ud.setNgayBatDau(XDate.toDate(txtngaybatdau.getText(), "dd-MM-yyyy"));
        ud.setNgayKetThuc(XDate.toDate(txtngayketthuc.getText(), "dd-MM-yyyy"));
        ud.setNoiDung(txtnoidung.getText());
        ud.setGiamGia(Float.parseFloat(txtgiamgia.getText()));

        return ud;
    }

    void insert() {
        String mauudai = txtmauudai.getText().trim();
        String tenuudai = txttenuudai.getText().trim();
        String ngaybatdau = txtngaybatdau.getText().trim();
        String ngayketthuc = txtngayketthuc.getText().trim();
        String noidung = txtnoidung.getText().trim();
        String giamgia = txtgiamgia.getText().trim();

        if (mauudai.isEmpty() || tenuudai.isEmpty() || ngaybatdau.isEmpty() || ngayketthuc.isEmpty() || noidung.isEmpty() || giamgia.isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập đầy đủ thông tin");
        } else {
            try {
                float a = Float.parseFloat(giamgia);
                UuDai ud = getForm();
                dao.insert(ud);
                this.filltable();
                clearForm();
                MsgBox.alert(this, "Thêm thành công");
            } catch (NumberFormatException e) {
                MsgBox.alert(this, "Giảm giá không hợp lệ");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Thêm thất bại do trùng dữ liệu");
            }
        }
    }

    void update() {
        UuDai ud = getForm();

        try {
            dao.update(ud);
            this.filltable();
            txtmauudai.setEditable(true);
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Cập nhật thất bại");
        }
        clearForm();
    }

    void clearForm() {
        txtmauudai.setText("");
        txttenuudai.setText("");
        txtngaybatdau.setText("");
        txtngayketthuc.setText("");
        txtnoidung.setText("");
        txtgiamgia.setText(null);
        txtmauudai.setEditable(true);
        row = -1;
    }

    void timkiem() {
        String maTimKiem = txttimkiem.getText().trim();

        if (maTimKiem.isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập mã ưu đãi để tìm kiếm.");
            filltable();
        } else {
            DefaultTableModel model = (DefaultTableModel) tbluudai.getModel();
            model.setRowCount(0); // Xóa hết dữ liệu cũ

            try {
                List<UuDai> list = dao.selecByMaUuDai(maTimKiem);
                if (!list.isEmpty()) {
                    for (UuDai ud : list) {
                        Object[] row = {ud.getMaUuDai()};
                        model.addRow(row);
                    }
                } else {
                    MsgBox.alert(this, "Không tìm thấy mã ưu đãi: " + maTimKiem);
                    txttimkiem.setText("");
                    clearForm();
                    filltable(); // Hiển thị lại dữ liệu ban đầu
                    return;
                }

            } catch (Exception e) {
                System.out.println(e);
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            }
        }
        // Clear txttimkiem sau khi tìm kiếm
        filltable(); // Hiển thị lại dữ liệu sau khi tìm kiếm hoặc khi không nhập mã
        clearForm();
    }

    // Phương thức kiểm tra định dạng ngày tháng năm
    boolean validateData() {
//        if (txtmauudai.getText().trim().isEmpty()) {
//            MsgBox.alert(this, "Vui lòng chọn mã ưu đãi");
//            return false;
//        }

        if (txtmauudai.getText().trim().isEmpty() || txttenuudai.getText().trim().isEmpty()
                || txtngaybatdau.getText().trim().isEmpty()
                || txtngayketthuc.getText().trim().isEmpty()
                || txtnoidung.getText().trim().isEmpty()
                || txtgiamgia.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập đầy đủ thông tin");
            return false;
        }

        if (!isDateValid(txtngaybatdau.getText().trim(), "dd-MM-yyyy") || !isDateValid(txtngayketthuc.getText().trim(), "dd-MM-yyyy")) {
            MsgBox.alert(this, "Ngày tháng không đúng định dạng (dd-MM-yyyy)");
            return false;
        }

        try {
            float giamgia = Float.parseFloat(txtgiamgia.getText().trim());
            if (giamgia < 0 || giamgia > 100) {
                MsgBox.alert(this, "Giảm giá phải nằm trong khoảng từ 0 đến 100");
                return false;
            }
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Giảm giá không hợp lệ");
            return false;
        }

        // Kiểm tra năm của ngày bắt đầu và kết thúc
        if (!isDateValid(txtngaybatdau.getText().trim(), "dd-MM-yyyy") || !isDateValid(txtngayketthuc.getText().trim(), "dd-MM-yyyy")) {
            MsgBox.alert(this, "Ngày tháng không hợp lệ ");
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
        txtmauudai.setEditable(true);
        btnthem.setEnabled(true);
        btncapnhat.setEnabled(false);
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
        jPanel3 = new javax.swing.JPanel();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btncapnhat = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        btnthem = new javax.swing.JButton();
        txtngaybatdau = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtmauudai = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtnoidung = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        txtngayketthuc = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtgiamgia = new javax.swing.JTextField();
        txttenuudai = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btntim = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbluudai = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1250, 650));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        txtngaybatdau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtngaybatdau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtngaybatdauActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Tên ưu đãi:");

        txtmauudai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã ưu đãi:");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Giảm giá");

        txtnoidung.setColumns(20);
        txtnoidung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtnoidung.setRows(5);
        jScrollPane1.setViewportView(txtnoidung);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Nội dung:");

        txtngayketthuc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Ngày kết thúc:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Ngày bắt đầu:");

        txtgiamgia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtgiamgia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtgiamgiaActionPerformed(evt);
            }
        });

        txttenuudai.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txttenuudai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttenuudaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                    .addComponent(txtmauudai)
                    .addComponent(txttenuudai)
                    .addComponent(txtgiamgia)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel22)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtngaybatdau)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtngayketthuc)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmauudai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttenuudai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtngayketthuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtngaybatdau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtgiamgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("QUẢN LÝ ƯU ĐÃI");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btntim.setText("Tìm");
        btntim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btntimMouseClicked(evt);
            }
        });
        btntim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimActionPerformed(evt);
            }
        });
        btntim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btntimKeyPressed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Mã ưu đãi");

        txttimkiem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txttimkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttimkiemActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tbluudai.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbluudai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Ưu Đãi", "Tên Ưu Đãi", "Ngày Bắt Đầu", "Ngày Kết thúc", "Nội Dung", "Giảm Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbluudai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbluudaiMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbluudaiMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbluudai);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txttimkiem, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btntim, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txttimkiem)
                        .addComponent(jLabel20))
                    .addComponent(btntim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(443, 443, 443)
                .addComponent(jLabel37)
                .addContainerGap(554, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(153, 171, 190));

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/menu_icon.png"))); // NOI18N

        jMenuItem4.setText("Trang chủ");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        // TODO add your handling code here:
        if (validateData()) {
            insert();
            clearForm();

        }
    }//GEN-LAST:event_btnthemActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        // TODO add your handling code here:
        if (validateData()) {
            update();
            btncapnhat.setEnabled(false);
            btnthem.setEnabled(true);

        }

    }//GEN-LAST:event_btncapnhatActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        // TODO add your handling code here:
        clearForm();
        btncapnhat.setEnabled(false);
        btnthem.setEnabled(true);
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void tbluudaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbluudaiMouseClicked
//        this.selectedRow = tbluudai.getSelectedRow();
        selectedRow = tbluudai.getSelectedRow();
        if (selectedRow >= 0) {
            setForm(dao.selectById(tbluudai.getValueAt(selectedRow, 0).toString()));
        }
        row = tbluudai.getSelectedRow();
        txtmauudai.setEditable(false);

        if (row != -1) {
            String ma = tbluudai.getValueAt(row, 0).toString();
            String ten = tbluudai.getValueAt(row, 1).toString();
            String ngaybd = tbluudai.getValueAt(row, 2).toString();
            String ngaykt = tbluudai.getValueAt(row, 3).toString();
            String nd = tbluudai.getValueAt(row, 4).toString();
            String gg = tbluudai.getValueAt(row, 5).toString();

            txtmauudai.setText(ma);
            txttenuudai.setText(ten);
            txtngaybatdau.setText(ngaybd);
            txtngayketthuc.setText(ngaykt);
            txtnoidung.setText(nd);
            txtgiamgia.setText(gg);

        }
        btncapnhat.setEnabled(true);
        btnthem.setEnabled(false);


    }//GEN-LAST:event_tbluudaiMouseClicked

    private void txtngaybatdauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtngaybatdauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtngaybatdauActionPerformed

    private void txtgiamgiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtgiamgiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtgiamgiaActionPerformed

    private void tbluudaiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbluudaiMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbluudaiMousePressed

    private void txttenuudaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttenuudaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttenuudaiActionPerformed

    private void btntimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btntimKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btntimKeyPressed

    private void btntimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntimMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_btntimMouseClicked

    private void txttimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttimkiemActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txttimkiemActionPerformed

    private void btntimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimActionPerformed
        // TODO add your handling code here:

        timkiem();
    }//GEN-LAST:event_btntimActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        QLHome homeQL = new QLHome();
        homeQL.setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

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
            java.util.logging.Logger.getLogger(QLUuDai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLUuDai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLUuDai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLUuDai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new QLUuDai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncapnhat;
    private javax.swing.JButton btnlammoi;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntim;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbluudai;
    private javax.swing.JTextField txtgiamgia;
    private javax.swing.JTextField txtmauudai;
    private javax.swing.JTextField txtngaybatdau;
    private javax.swing.JTextField txtngayketthuc;
    private javax.swing.JTextArea txtnoidung;
    private javax.swing.JTextField txttenuudai;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
