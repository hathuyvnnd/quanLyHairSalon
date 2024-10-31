/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QLForm;

import NVForm.NVHome;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import salon.Dao.BangLuongDao;
import salon.Dao.ChiTietBangLuongDao;
import salon.Dao.NhanVienDao;
import salon.entity.BangLuong;
import salon.entity.ChiTietBangLuong;
import salon.entity.NhanVien;
import salon.tienich.Auth;
import salon.tienich.MsgBox;
import salon.tienich.XDate;
import salon.ui.Changepass;
import salon.ui.Login;

/**
 *
 * @author trong
 */
public class QLNhanSu extends javax.swing.JFrame {

    NhanVienDao dao = new NhanVienDao();
    ChiTietBangLuongDao blctDao = new ChiTietBangLuongDao();
    BangLuongDao blDao = new BangLuongDao();
    int row = -1;
    int index = 0;
    int thang = -1;
    int nam = -1;

    /**
     * Creates new form Manager
     */
    public QLNhanSu() {
        initComponents();
        init();
        upst();
    }

    void init() {
        this.filltable();
        this.fillTableBangLuong();

    }

    void filltable() {
        DefaultTableModel model = (DefaultTableModel) tblnhansu.getModel();
        model.setRowCount(0); //xoa tat ca cac hang tren table
        try {
            String keywork = txttimkiem.getText();
            List<NhanVien> list = dao.selectbykeyword(keywork);
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNhanVien(), nv.getMatKhau(), nv.getHoTen(), nv.isGioiTinh() ? "Nam " : "Nữ", nv.getNgaySinh(), nv.isVaiTro() ? "Thợ chính" : "Thợ phụ", nv.getChucVu(), nv.getSoDienThoai(), nv.getEmail(), nv.getLuongCung(), nv.getHoaHong(), nv.isTrangThai() ? "Đang làm" : "Đã nghỉ", nv.getMaCuaHang()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "loi truy van du lieu");
        }

    }

    void setform(NhanVien sp) {
        txtmanhanvien.setText(sp.getMaNhanVien());
        txtmatkhau.setText(sp.getMatKhau());
        txttennhanvien.setText(sp.getHoTen());
        if (sp.isGioiTinh()) {
            rdonam.setSelected(true);
            rdonu.setSelected(false);
        } else {
            rdonam.setSelected(false);
            rdonu.setSelected(true);
        }
        txtngaysinh.setText(sp.getNgaySinh());
        if (sp.isVaiTro()) {
            rdothochinh.setSelected(true);
            rdothophu.setSelected(false);
        } else {
            rdothochinh.setSelected(false);
            rdothophu.setSelected(true);
        }
        if (sp.getChucVu().equals("Quản lý")) {
            rdoquanly.setSelected(true);
            rdonhanvien.setSelected(false);
        } else {
            rdoquanly.setSelected(false);
            rdonhanvien.setSelected(true);
        }
        txtsodt.setText(String.valueOf(sp.getSoDienThoai()));
        txtemail.setText(sp.getEmail());
        txtluongcung.setText(String.valueOf(sp.getLuongCung()));
        txthoahong.setText(String.valueOf(sp.getHoaHong()));
        if (sp.isTrangThai()) {
            rdodanglam.setSelected(true);
            rdodanghi.setSelected(false);
        } else {
            rdodanglam.setSelected(false);
            rdodanghi.setSelected(true);
        }
        cbochinhanh.setSelectedItem(sp.getMaCuaHang());
    }

    NhanVien getform() {
        NhanVien sp = new NhanVien();
        sp.setMaNhanVien(txtmanhanvien.getText());
        sp.setMatKhau(txtmatkhau.getText());
        sp.setHoTen(txttennhanvien.getText());
        sp.setGioiTinh(rdonam.isSelected());
        sp.setNgaySinh(txtngaysinh.getText());
        sp.setVaiTro(rdothochinh.isSelected());
        sp.setChucVu(String.valueOf(rdoquanly.isSelected()));
        sp.setSoDienThoai(txtsodt.getText());
        sp.setEmail(txtemail.getText());
        sp.setLuongCung(Integer.parseInt(txtluongcung.getText()));
        sp.setHoaHong(Integer.parseInt(txthoahong.getText()));
        sp.setTrangThai(rdodanglam.isSelected());
        sp.setMaCuaHang((String) cbochinhanh.getSelectedItem());
        return sp;
    }

    void clearform() {
        NhanVien sp = new NhanVien();
        this.setform(sp);
        this.row = -1;
        this.upst();
    }

    void insert() {
        if (valiDate()) {
            String manv = txtmanhanvien.getText().trim();
            String tennv = txttennhanvien.getText().trim();
            String mk = txtmatkhau.getText().trim();
            String ns = txtngaysinh.getText().trim();

            NhanVien dv = getform();
            try {
                dao.insert(dv);
                this.filltable();
                MsgBox.alert(this, "Thêm thành công");
                clearform();
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm thất bại");
            }
        }

    }

    void update() {
        if (valiDate()) {
            NhanVien sp = getform();
            try {
                dao.updateql(sp);
                this.filltable();
                MsgBox.alert(this, "Cập nhật thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Cập nhật thất bại");
            }
        }

    }

    void edit() {
        String manv = (String) tblnhansu.getValueAt(this.row, 0);
        NhanVien nv = dao.selectById(manv);
        this.setform(nv);
        tabs.setSelectedIndex(0);
        upst();
    }

    private void timkiem() {
        this.filltable();
        this.clearform();
    }

    void upst() {
        if (row == -1) {
            btnthem.setEnabled(true);
            btncapnhat.setEnabled(false);
            txtmanhanvien.setEnabled(true);
        } else {
            btnthem.setEnabled(false);
            btncapnhat.setEnabled(true);
            txtmanhanvien.setEnabled(false);
        }

    }

    boolean valiDate() {
        if (txtmanhanvien.getText().isEmpty() || txtmatkhau.getText().isEmpty() || txttennhanvien.getText().isEmpty() || txtemail.getText().isEmpty() || txtngaysinh.getText().isEmpty() || txtsodt.getText().isEmpty() || txtluongcung.getText().isEmpty() || txthoahong.getText().isEmpty()) {
            MsgBox.alert(this, "Chưa nhập đủ thông tin");
            return false;
        }
        try {
            int sdt = Integer.valueOf(txtsodt.getText());
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Số điện thoại chưa đúng định dạng");
            return false;
        }
        try {
            Date d = XDate.toDate(txtngaysinh.getText(), "yyyy-MM-dd");
        } catch (Exception e) {
            MsgBox.alert(this, "Ngày sinh phải theo định dạng yyyy-MM-dd");
            return false;
        }

        return true;
    }

    // CODE BẢNG LƯƠNG
    void fillTableBangLuong() {
        DefaultTableModel model = (DefaultTableModel) tblBangLuong.getModel();
        model.setRowCount(0);
        List<ChiTietBangLuong> list = blctDao.selectByMa(thang, nam);
        for (ChiTietBangLuong ctbl : list) {
            NhanVien nv = dao.selectById(ctbl.getMaNhanVien());
            int doanhThu = blctDao.tinhDoanhThu(thang, nam, nv.getMaNhanVien());
            int luongNgayCong = ctbl.getNgayCong() * nv.getLuongCung();
            Object[] row = {nv.getMaNhanVien(), nv.getHoTen(), ctbl.getNgayCong(), nv.getLuongCung(), nv.getHoaHong(), doanhThu,
                nv.getLuongCung() * ctbl.getNgayCong(), (doanhThu / 100) * nv.getHoaHong(),
                ((doanhThu / 100) * nv.getHoaHong()) + (nv.getLuongCung() * ctbl.getNgayCong())};
            model.addRow(row);
        }
        lblTongChiLuong.setText(String.valueOf(tongLuong()));
    }

    ChiTietBangLuong getInformationBangLuong() {
        ChiTietBangLuong ctbl = new ChiTietBangLuong();
        ctbl.setThang(clThang.getMonth() + 1);
        ctbl.setNam(clNam.getYear());
        ctbl.setMaNhanVien(lblMaNhanVien.getText());
        ctbl.setNgayCong(Integer.parseInt(txtNgayCong.getText()));
        return ctbl;
    }

    void setInformationBangLuong(int i) {
        lblMaNhanVien.setText((String) tblBangLuong.getValueAt(index, 0));
        lblHoTen.setText((String) tblBangLuong.getValueAt(index, 1));
        txtNgayCong.setText(String.valueOf(tblBangLuong.getValueAt(index, 2)));
        lblLuongCoBan.setText(String.valueOf(tblBangLuong.getValueAt(index, 3)));
        lblHoaHong.setText(String.valueOf(tblBangLuong.getValueAt(index, 4)));
        lblDoanhThu.setText(String.valueOf(tblBangLuong.getValueAt(index, 5)));
        lblLuongNgayCong.setText(String.valueOf(tblBangLuong.getValueAt(index, 6)));
        lblTienHoaHong.setText(String.valueOf(tblBangLuong.getValueAt(index, 7)));
        lblTongNhan.setText(String.valueOf(tblBangLuong.getValueAt(index, 8)));
        lblTongChiLuong.setText(String.valueOf(tongLuong()));
    }

    void updateBLCT() {
        ChiTietBangLuong btcl = getInformationBangLuong();
        System.out.println(btcl);
        try {
            blctDao.update(btcl);
            fillTableBangLuong();
            MsgBox.alert(this, "Cập nhập thành công.");
            lblTongChiLuong.setText(String.valueOf(tongLuong()));
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alert(this, "Cập nhật thất bại.");
        }
    }

    BangLuong getBangLuong() {
        BangLuong bl = new BangLuong();
        bl.setThang(clThang.getMonth() + 1);
        bl.setNam(clNam.getYear());
        return bl;
    }

    void insertBL() {
        if (blDao.selectByThangNam(thang, nam) == null) {
            BangLuong bl = getBangLuong();

            try {
                blDao.insert(bl);
                fillTableBangLuong();
                MsgBox.alert(this, "Thêm Bảng lương mới thành công");
            } catch (Exception e) {
                System.out.println(e);
                MsgBox.alert(this, "Thêm bảng lương mới thất bại");
            }
        } else {
            MsgBox.alert(this, "Bảng lương đã tồn tại");
        }
    }

    int tongLuong() {
        int tong = 0;
        for (int i = 0; i < tblBangLuong.getRowCount(); i++) {
            tong += (int) tblBangLuong.getValueAt(i, 8);
        }
        return tong;
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        rdoquanly = new javax.swing.JRadioButton();
        rdonhanvien = new javax.swing.JRadioButton();
        jLabel20 = new javax.swing.JLabel();
        rdodanglam = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        rdodanghi = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        txtmanhanvien = new javax.swing.JTextField();
        cbochinhanh = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txttennhanvien = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtmatkhau = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtngaysinh = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtsodt = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        rdonam = new javax.swing.JRadioButton();
        rdonu = new javax.swing.JRadioButton();
        btnthem = new javax.swing.JButton();
        btncapnhat = new javax.swing.JButton();
        btnlammoi = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        txtluongcung = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txthoahong = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        rdothochinh = new javax.swing.JRadioButton();
        rdothophu = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        btntim = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblnhansu = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        lblMaNhanVien = new javax.swing.JLabel();
        lblLuongNgayCong = new javax.swing.JLabel();
        lblLuongCoBan = new javax.swing.JLabel();
        lblHoaHong = new javax.swing.JLabel();
        lblDoanhThu = new javax.swing.JLabel();
        lblTienHoaHong = new javax.swing.JLabel();
        lblTongNhan = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        txtNgayCong = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        clThang = new com.toedter.calendar.JMonthChooser();
        clNam = new com.toedter.calendar.JYearChooser();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBangLuong = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        lblTongChiLuong = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1220, 620));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("QUẢN LÝ NHÂN SỰ");

        tabs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Vai trò");

        buttonGroup2.add(rdoquanly);
        rdoquanly.setSelected(true);
        rdoquanly.setText("Quản lý");

        buttonGroup2.add(rdonhanvien);
        rdonhanvien.setText("Nhân viên");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("Trạng thái:");

        buttonGroup3.add(rdodanglam);
        rdodanglam.setSelected(true);
        rdodanglam.setText("Đang làm");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Mã nhân viên:");

        buttonGroup3.add(rdodanghi);
        rdodanghi.setText("Đã nghỉ");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Chi nhánh");

        txtmanhanvien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        cbochinhanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CH01", "CH02" }));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("Tên nhân viên:");

        txttennhanvien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Mật khẩu:");

        txtmatkhau.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Ngày sinh:");

        txtngaysinh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Email:");

        txtemail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Giới tính:");

        txtsodt.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("Lương cứng");

        buttonGroup1.add(rdonam);
        rdonam.setSelected(true);
        rdonam.setText("Nam");

        buttonGroup1.add(rdonu);
        rdonu.setText("Nữ");

        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthem.setText("THÊM");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        btncapnhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btncapnhat.setText("CẬP NHẬT");
        btncapnhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncapnhatActionPerformed(evt);
            }
        });

        btnlammoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnlammoi.setText("LÀM MỚI");
        btnlammoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlammoiActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("Số điện thoại:");

        txtluongcung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("Hoa hồng");

        txthoahong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("Chức vụ:");

        buttonGroup4.add(rdothochinh);
        rdothochinh.setSelected(true);
        rdothochinh.setText("Thợ chính");

        buttonGroup4.add(rdothophu);
        rdothophu.setText("Thợ phụ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txttennhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtmanhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(140, 140, 140)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(txtluongcung, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(txthoahong, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txtsodt, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(cbochinhanh, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(rdonam)
                                .addGap(63, 63, 63))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel25)
                                    .addComponent(rdoquanly))
                                .addGap(49, 49, 49)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdonu)
                            .addComponent(rdonhanvien)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel19))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdothochinh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel21)
                                .addComponent(rdodanglam)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdodanghi)
                            .addComponent(rdothophu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(83, 83, 83))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtmanhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdonam)
                            .addComponent(rdonu))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txttennhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoquanly)
                            .addComponent(rdonhanvien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtngaysinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdothochinh)
                            .addComponent(rdothophu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdodanglam)
                            .addComponent(rdodanghi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addComponent(txtsodt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbochinhanh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btncapnhat, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnlammoi, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txthoahong)
                            .addComponent(txtluongcung))
                        .addGap(88, 88, 88))))
        );

        tabs.addTab("Quản lý nhân viên", jPanel3);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Mã nhân viên:");

        txttimkiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btntim.setText("Tìm");
        btntim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimActionPerformed(evt);
            }
        });

        tblnhansu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Mật khẩu", "Họ tên", "Giới tính", "Ngày sinh", "Vai trò", "Chức vụ", "Số đt", "Email", "Lương cứng", "Hoa hồng", "Trạng thái", "Mã cửa hàng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblnhansu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblnhansuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblnhansu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(1108, Short.MAX_VALUE)
                .addComponent(btntim, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txttimkiem)
                            .addGap(137, 137, 137)))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btntim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(507, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        tabs.addTab("Danh sách nhân viên", jPanel1);

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Mã Nhân Viên: ");

        jLabel4.setText("Họ và Tên:");

        jLabel6.setText("Lương cơ bản:");

        jLabel7.setText("Hoa Hồng:");

        jLabel8.setText("Doanh thu: ");

        jLabel9.setText("Ngày công: ");

        jLabel14.setText("Lương ngày công :");

        jLabel15.setText("Tiền hoa hồng: ");

        jLabel24.setText("Tổng nhận : ");

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHoTen.setForeground(new java.awt.Color(102, 102, 255));
        lblHoTen.setText("jLabel26");

        lblMaNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaNhanVien.setForeground(new java.awt.Color(102, 102, 255));
        lblMaNhanVien.setText("jLabel26");

        lblLuongNgayCong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLuongNgayCong.setForeground(new java.awt.Color(102, 102, 255));
        lblLuongNgayCong.setText("jLabel26");

        lblLuongCoBan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLuongCoBan.setForeground(new java.awt.Color(102, 102, 255));
        lblLuongCoBan.setText("jLabel26");

        lblHoaHong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHoaHong.setForeground(new java.awt.Color(102, 102, 255));
        lblHoaHong.setText("jLabel26");

        lblDoanhThu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDoanhThu.setForeground(new java.awt.Color(102, 102, 255));
        lblDoanhThu.setText("jLabel26");

        lblTienHoaHong.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTienHoaHong.setForeground(new java.awt.Color(102, 102, 255));
        lblTienHoaHong.setText("jLabel26");

        lblTongNhan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTongNhan.setForeground(new java.awt.Color(255, 51, 51));
        lblTongNhan.setText("jLabel26");

        jButton2.setText("Cập nhập");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(80, 80, 80)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(80, 80, 80)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblLuongCoBan, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblHoaHong, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(80, 80, 80)
                                    .addComponent(txtNgayCong, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblTienHoaHong, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblLuongNgayCong, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(1, 1, 1))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblTongNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(157, 157, 157)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblMaNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblHoTen))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtNgayCong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblLuongCoBan))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblHoaHong))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblDoanhThu))
                .addGap(30, 30, 30)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblLuongNgayCong))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblTienHoaHong))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongNhan)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(30, 30, 30))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("BẢNG LƯƠNG THÁNG: ");

        clThang.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clThangPropertyChange(evt);
            }
        });

        clNam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clNamPropertyChange(evt);
            }
        });

        jButton1.setText("Tạo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tblBangLuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Nhân Viên", "Họ Tên", "Số ngày công", "Lương cơ bản", "Hoa Hồng", "Doanh Thu", "Lương Ngày Công", "Tiền Hoa Hồng", "Thực nhận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBangLuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangLuongMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblBangLuong);

        jLabel5.setText("Tổng Chi Lương: ");

        lblTongChiLuong.setForeground(new java.awt.Color(255, 51, 51));
        lblTongChiLuong.setText("jLabel26");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clThang, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clNam, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(lblTongChiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 744, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(clNam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clThang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(lblTongChiLuong))
                        .addGap(19, 59, Short.MAX_VALUE))))
        );

        tabs.addTab("Bảng Lương", jPanel4);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(432, 432, 432)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabs)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs))
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
        insert();
    }//GEN-LAST:event_btnthemActionPerformed

    private void btncapnhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncapnhatActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btncapnhatActionPerformed

    private void tblnhansuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblnhansuMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.row = tblnhansu.getSelectedRow();
            this.edit();
            btncapnhat.setEnabled(true);

        }
    }//GEN-LAST:event_tblnhansuMouseClicked

    private void btntimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimActionPerformed
        // TODO add your handling code here:
        timkiem();
    }//GEN-LAST:event_btntimActionPerformed

    private void btnlammoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlammoiActionPerformed
        // TODO add your handling code here:
        txtmanhanvien.setText("");
        txttennhanvien.setText("");
        txtmatkhau.setText("");
        txtemail.setText("");
        txtsodt.setText("");
        txtngaysinh.setText("");
        txtluongcung.setText("");
        txthoahong.setText("");

        btnthem.setEnabled(true);
        txtmanhanvien.setEnabled(true);
    }//GEN-LAST:event_btnlammoiActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        updateBLCT();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void clThangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clThangPropertyChange
        // TODO add your handling code here:
        thang = clThang.getMonth() + 1;
        System.out.println("Thang thay doi " + thang);
        fillTableBangLuong();
    }//GEN-LAST:event_clThangPropertyChange

    private void clNamPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clNamPropertyChange
        // TODO add your handling code here:
        nam = clNam.getYear();
        fillTableBangLuong();
    }//GEN-LAST:event_clNamPropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        insertBL();
        setInformationBangLuong(index);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblBangLuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangLuongMouseClicked
        // TODO add your handling code here:
        this.index = tblBangLuong.getSelectedRow();
        String maNhanVien = (String) tblBangLuong.getValueAt(this.index, 0);
        setInformationBangLuong(index);
    }//GEN-LAST:event_tblBangLuongMouseClicked

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
            java.util.logging.Logger.getLogger(QLKhachhang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLKhachhang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLKhachhang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLKhachhang.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLNhanSu().setVisible(true);
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
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> cbochinhanh;
    private com.toedter.calendar.JYearChooser clNam;
    private com.toedter.calendar.JMonthChooser clThang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblDoanhThu;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblHoaHong;
    private javax.swing.JLabel lblLuongCoBan;
    private javax.swing.JLabel lblLuongNgayCong;
    private javax.swing.JLabel lblMaNhanVien;
    private javax.swing.JLabel lblTienHoaHong;
    private javax.swing.JLabel lblTongChiLuong;
    private javax.swing.JLabel lblTongNhan;
    private javax.swing.JRadioButton rdodanghi;
    private javax.swing.JRadioButton rdodanglam;
    private javax.swing.JRadioButton rdonam;
    private javax.swing.JRadioButton rdonhanvien;
    private javax.swing.JRadioButton rdonu;
    private javax.swing.JRadioButton rdoquanly;
    private javax.swing.JRadioButton rdothochinh;
    private javax.swing.JRadioButton rdothophu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblBangLuong;
    private javax.swing.JTable tblnhansu;
    private javax.swing.JTextField txtNgayCong;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txthoahong;
    private javax.swing.JTextField txtluongcung;
    private javax.swing.JTextField txtmanhanvien;
    private javax.swing.JTextField txtmatkhau;
    private javax.swing.JTextField txtngaysinh;
    private javax.swing.JTextField txtsodt;
    private javax.swing.JTextField txttennhanvien;
    private javax.swing.JTextField txttimkiem;
    // End of variables declaration//GEN-END:variables
}
