/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package QLForm;

import NVForm.NVHome;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import salon.Dao.ChiTietPhieuNhapDao;
import salon.Dao.ChiTietPhieuXuatDao;
import salon.Dao.CuaHangDao;
import salon.Dao.KhoDao;
import salon.Dao.NhaCungCapDao;
import salon.Dao.PhieuNhapDao;
import salon.Dao.PhieuXuatDao;
import salon.Dao.SanPhamDao;
import salon.Dao.ThongKeDAO;
import salon.entity.ChiTietPhieuNhap;
import salon.entity.ChiTietPhieuXuat;
import salon.entity.CuaHang;
import salon.entity.Kho;
import salon.entity.NhaCungCap;
import salon.entity.PhieuNhap;
import salon.entity.PhieuXuat;
import salon.entity.SanPham;
import salon.tienich.Auth;
import salon.tienich.MsgBox;
import salon.tienich.XDate;
import salon.ui.Changepass;
import salon.ui.Login;

/**
 *
 * @author trong
 */
public class QLKho extends javax.swing.JFrame {
    
    NhaCungCapDao nccDAO = new NhaCungCapDao();
    List<NhaCungCap> nhacungcap;
    CuaHangDao chDao = new CuaHangDao();
    List<CuaHang> cuahang;
    SanPhamDao spDao = new SanPhamDao();
    List<SanPham> sanpham;
    KhoDao khoDao = new KhoDao();
    List<Kho> kho;
    PhieuNhapDao phieunhapDao = new PhieuNhapDao();
    List<PhieuNhap> phieunhap;
    PhieuXuatDao phieuxuatDao = new PhieuXuatDao();
    List<PhieuXuat> phieuxuat;
    ChiTietPhieuNhapDao ctpnDao = new ChiTietPhieuNhapDao();
    List<ChiTietPhieuNhap> chitietphieunhap;
    ChiTietPhieuXuatDao ctpxDao = new ChiTietPhieuXuatDao();
    List<ChiTietPhieuXuat> chitietphieuxuat;
    int row = -1;
    int indexNCC;
    int indexCH;
    int taophieu = -1;
    int thang;
    int nam;
    int thang1;
    int nam1;
    ThongKeDAO dao = new ThongKeDAO();

    /**
     * Creates new form Manager
     */
    public QLKho() {
        initComponents();
        init();
    }
    
    void init() {
        fillComboboxNhaCungCap();
        fillComboboxCuaHang();
        updateStatus();
    }
    
    void fillComboboxNhaCungCap() {
        DefaultComboBoxModel cbomodel = (DefaultComboBoxModel) cbonhacungcap.getModel();
        cbomodel.removeAllElements();
        nhacungcap = nccDAO.selectAll();
        for (NhaCungCap ncc : nhacungcap) {
            cbomodel.addElement(ncc.getTenNhaCungCap());
        }
        fillTableSanPham(indexNCC);
    }
    
    void fillComboboxCuaHang() {
        DefaultComboBoxModel cbomodel = (DefaultComboBoxModel) cbomacuahang.getModel();
        cbomodel.removeAllElements();
        cuahang = chDao.selectAll();
        for (CuaHang ch : cuahang) {
            cbomodel.addElement(ch.getTenCuaHang());
        }
        fillTableKho(indexCH);
    }
    
    void fillTableSanPham(int index) {
        DefaultTableModel model = (DefaultTableModel) tblSanpham.getModel();
        model.setRowCount(0);
        try {
            sanpham = spDao.selecbyNhaCungCap(nhacungcap.get(index).getMaNhaCungCap());
            String trangthai;
            if (nhacungcap.get(index).isTrangThai()) {
                trangthai = "(Đang hợp tác)";
            } else {
                trangthai = "(Ngưng hợp tác)";
            }
            lblTrangthai.setText(trangthai);
            for (SanPham sp : sanpham) {
                Object[] ob = {sp.getMaSanPham(), sp.getTenSanPham(), sp.getGiaSanPham()};
                model.addRow(ob);
            }
        } catch (Exception e) {
        }
    }
    
    void fillTableKho(int index) {
        DefaultTableModel model = (DefaultTableModel) tblKho.getModel();
        model.setRowCount(0);
        try {
            kho = khoDao.selectByMaCuaHang(cuahang.get(index).getMaCuaHang());
            for (Kho k : kho) {
                String tensanpham = spDao.selectById(k.getMaSanPham()).getTenSanPham();
                String manhacungcap = spDao.selectById(k.getMaSanPham()).getMaNhaCungCap();
                Object[] ob = {k.getMaSanPham(), tensanpham, k.getSoLuong(), manhacungcap};
                if (k.getSoLuong() == 0) {
                    khoDao.delete(k);
                }
                model.addRow(ob);
            }
        } catch (Exception e) {
        }
    }
    
    void clearForm() {
        txtmaphieunhapxuat.setText(null);
        txtngay.setText(null);
        txtmasanpham.setText(null);
        txtsoluong.setText(null);
        taophieu = -1;
        row = -1;
        updateStatus();
    }
    
    void updateStatus() {
        if (taophieu == -1) {
            btntaophieunhap.setEnabled(true);
            btntaophieuxuat.setEnabled(true);
            txtmasanpham.setEditable(false);
            txtsoluong.setEditable(false);
            btnthem.setEnabled(false);
            btnhuy.setEnabled(false);
            btnxacnhan.setEnabled(false);
            btnxoa.setEnabled(false);
            cbomacuahang.setEnabled(true);
            cbonhacungcap.setEnabled(true);
            lbltenphieu.setText(null);
        } else if (taophieu == 0) {
            btntaophieunhap.setEnabled(false);
            btntaophieuxuat.setEnabled(false);
            txtmasanpham.setEditable(true);
            txtsoluong.setEditable(true);
            btnthem.setEnabled(true);
            btnhuy.setEnabled(true);
            btnxoa.setEnabled(false);
            btnxacnhan.setEnabled(false);
            cbomacuahang.setEnabled(false);
            cbonhacungcap.setEnabled(false);
            lbltenphieu.setText("Nhập hàng");
        } else {
            btntaophieunhap.setEnabled(false);
            btntaophieuxuat.setEnabled(false);
            txtmasanpham.setEditable(true);
            txtsoluong.setEditable(true);
            btnthem.setEnabled(true);
            btnhuy.setEnabled(true);
            btnxoa.setEnabled(false);
            btnxacnhan.setEnabled(false);
            cbomacuahang.setEnabled(false);
            cbonhacungcap.setEnabled(false);
            lbltenphieu.setText("Xuất hàng");
        }
    }
    
    boolean Validate() {
        if (taophieu == 0) {
            if (txtmasanpham.getText().isEmpty()) {
                MsgBox.alert(this, "Chưa nhập đủ thông tin");
                return false;
            }
            if (txtsoluong.getText().isEmpty()) {
                MsgBox.alert(this, "Chưa nhập đủ thông tin");
                return false;
            }
            //Check ma san pham
            int v = 0;
            for (SanPham sp : sanpham) {
                if (txtmasanpham.getText().equals(sp.getMaSanPham())) {
                    v = 1;
                }
            }
            if (v == 0) {
                MsgBox.alert(this, "Mã sản phẩm không tồn tại trong danh sách sản phẩm của nhà cung cấp");
                return false;
            }
            //Check cột số lượng
            try {
                if (Integer.valueOf(txtsoluong.getText()) <= 0) {
                    MsgBox.alert(this, "Dữ liệu ô số lượng không chính xác");
                    return false;
                }
            } catch (NumberFormatException e) {
                MsgBox.alert(this, "Dữ liệu ô số lượng không chính xác");
                return false;
            }
            return true;
        } else {
            if (txtmasanpham.getText().isEmpty()) {
                MsgBox.alert(this, "Chưa nhập đủ thông tin");
                return false;
            }
            if (txtsoluong.getText().isEmpty()) {
                MsgBox.alert(this, "Chưa nhập đủ thông tin");
                return false;
            }
            int v = 0;
            for (Kho sp : kho) {
                if (txtmasanpham.getText().equals(sp.getMaSanPham())) {
                    v = 1;
                }
            }
            //Check ma san pham
            if (v == 0) {
                MsgBox.alert(this, "Mã sản phẩm không tồn tại trong kho hàng");
                return false;
            }
            //Check cột số lượng
            try {
                if (Integer.valueOf(txtsoluong.getText()) <= 0) {
                    MsgBox.alert(this, "Dữ liệu ô số lượng không chính xác");
                    return false;
                }
            } catch (NumberFormatException e) {
                MsgBox.alert(this, "Dữ liệu ô số lượng không chính xác");
                return false;
            }
            for (Kho spKho : kho) {
                if (txtmasanpham.getText().equals(spKho.getMaSanPham())) {
                    if (Integer.valueOf(txtsoluong.getText()) > spKho.getSoLuong()) {
                        MsgBox.alert(this, "Số lượng xuất lớn hơn số lượng trong kho");
                        return false;
                    }
                }
            }
            return true;
        }
    }
    
    PhieuNhap getFormPN() {
        PhieuNhap entity = new PhieuNhap();
        entity.setNgayNhap(XDate.toDate(txtngay.getText(), "yyyy-MM-dd"));
        entity.setMaCH(cuahang.get(indexCH).getMaCuaHang());
        entity.setMaNCC(nhacungcap.get(indexNCC).getMaNhaCungCap());
        return entity;
    }
    
    PhieuXuat getFormPX() {
        PhieuXuat entity = new PhieuXuat();
        entity.setNgayXuat(XDate.toDate(txtngay.getText(), "yyyy-MM-dd"));
        entity.setMaCH(cuahang.get(indexCH).getMaCuaHang());
        return entity;
    }
    
    void createPhieuNhap() {
        if (nhacungcap.get(indexNCC).isTrangThai()) {
            taophieu = 0;
            updateStatus();
            Date NgayNhap = new Date();
            txtngay.setText((XDate.toString(NgayNhap, "yyyy-MM-dd")));
            PhieuNhap entity = getFormPN();
            phieunhapDao.insert(entity);
            phieunhap = phieunhapDao.selectAll();
            int MaPhieuNhap = phieunhap.get(phieunhap.size() - 1).getMaPhieuNhap();
            txtmaphieunhapxuat.setText(String.valueOf(MaPhieuNhap));
        } else {
            MsgBox.alert(this, "Nhà cung cấp này hiện đã ngưng hợp tác");
        }
        
    }
    
    void createPhieuXuat() {
        taophieu = 1;
        updateStatus();
        Date NgayXuat = new Date();
        txtngay.setText((XDate.toString(NgayXuat, "yyyy-MM-dd")));
        PhieuXuat entity = getFormPX();
        phieuxuatDao.insert(entity);
        phieuxuat = phieuxuatDao.selectAll();
        int MaPhieuXuat = phieuxuat.get(phieuxuat.size() - 1).getMaPhieuXuat();
        txtmaphieunhapxuat.setText(String.valueOf(MaPhieuXuat));
    }
    
    void fillTableChitietphieu() {
        DefaultTableModel model = (DefaultTableModel) tblchitietphieu.getModel();
        model.setRowCount(0);
        if (taophieu == 0) {
            chitietphieunhap = ctpnDao.selectByMaphieunhap(Integer.valueOf(txtmaphieunhapxuat.getText()));
            for (ChiTietPhieuNhap ctp : chitietphieunhap) {
                Object[] ob = {ctp.getId(), ctp.getMaSanPham(), ctp.getSoLuongnhap()};
                model.addRow(ob);
            }
        } else if (taophieu == 1) {
            chitietphieuxuat = ctpxDao.selectByMaphieuxuat(Integer.valueOf(txtmaphieunhapxuat.getText()));
            for (ChiTietPhieuXuat ctp : chitietphieuxuat) {
                Object[] ob = {ctp.getId(), ctp.getMaSanPham(), ctp.getSoLuongXuat()};
                model.addRow(ob);
            }
        }
    }
    
    void add() {
        if (taophieu == 0) {
            ChiTietPhieuNhap ctp = new ChiTietPhieuNhap(Integer.valueOf(txtmaphieunhapxuat.getText()), txtmasanpham.getText(), Integer.valueOf(txtsoluong.getText()));
            ctpnDao.insert(ctp);
        } else {
            ChiTietPhieuXuat ctp = new ChiTietPhieuXuat(Integer.valueOf(txtmaphieunhapxuat.getText()), txtmasanpham.getText(), Integer.valueOf(txtsoluong.getText()));
            ctpxDao.insert(ctp);
        }
        fillTableChitietphieu();
    }
    
    void cancel() {
        boolean confirm = MsgBox.confirm(this, "Xác nhận hủy?");
        if (confirm) {
            if (taophieu == 0) {
                ctpnDao.deleteBympn(Integer.valueOf(txtmaphieunhapxuat.getText()));
                phieunhapDao.delete(Integer.valueOf(txtmaphieunhapxuat.getText()));
            } else {
                ctpxDao.deleteBympx(Integer.valueOf(txtmaphieunhapxuat.getText()));
                phieuxuatDao.delete(Integer.valueOf(txtmaphieunhapxuat.getText()));
            }
            clearForm();
            fillTableChitietphieu();
            MsgBox.alert(this, "Đã hủy phiếu");
        }
    }
    
    void delete() {
        if (row == -1) {
            MsgBox.alert(this, "Chưa chọn dòng muốn xóa");
        } else {
            boolean confirm = MsgBox.confirm(this, "Xác nhận xóa?");
            if (confirm) {
                if (taophieu == 0) {
                    Integer id = (Integer) tblchitietphieu.getValueAt(row, 0);
                    ctpnDao.delete(id);
                } else {
                    Integer id = (Integer) tblchitietphieu.getValueAt(row, 0);
                    ctpxDao.delete(id);
                }
                fillTableChitietphieu();
                MsgBox.alert(this, "Đã xóa");
            }
        }
    }
    
    boolean checkKho(String msp) {
        for (Kho k : kho) {
            if (msp.equals(k.getMaSanPham())) {
                return true;
            }
        }
        return false;
    }
    
    void updateKho() {
        if (taophieu == 0) {
            for (int i = 0; i < tblchitietphieu.getRowCount(); i++) {
                String macuahang = cuahang.get(indexCH).getMaCuaHang();
                String masanpham = (String) tblchitietphieu.getValueAt(i, 1);
                int soluong = (Integer) tblchitietphieu.getValueAt(i, 2);
                Kho entity = new Kho(macuahang, masanpham, soluong);
                if (checkKho(masanpham)) {
                    khoDao.update(entity);
                } else {
                    khoDao.insert(entity);
                }
            }
            MsgBox.alert(this, "Nhập hàng thành công");
        } else {
            for (int i = 0; i < tblchitietphieu.getRowCount(); i++) {
                String macuahang = cuahang.get(indexCH).getMaCuaHang();
                String masanpham = (String) tblchitietphieu.getValueAt(i, 1);
                int soluong = -(Integer) tblchitietphieu.getValueAt(i, 2);
                Kho entity = new Kho(macuahang, masanpham, soluong);
                khoDao.update(entity);
            }
            MsgBox.alert(this, "Xuất hàng thành công");
        }
    }

    // Tab lich su phieu xuat
    void fillTablePhieuXuatbyDAY(int t, int n) {
        DefaultTableModel model = (DefaultTableModel) tblPhieuxuat.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getLichSuXuatHang(t, n);
        for (Object[] ob : list) {
            model.addRow(ob);
        }
    }
    
    void fillTablePhieuXuatAll() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuxuat.getModel();
        model.setRowCount(0);
        List<PhieuXuat> list = phieuxuatDao.selectAll();
        for (PhieuXuat ob : list) {
            model.addRow(new Object[]{ob.getMaPhieuXuat(), ob.getNgayXuat(), ob.getMaCH()});
        }
    }
    
    void fillTableCTPX(int mapx) {
        DefaultTableModel model = (DefaultTableModel) tblChitietphieuxuat.getModel();
        model.setRowCount(0);
        List<ChiTietPhieuXuat> list = ctpxDao.selectByMaphieuxuat(mapx);
        for (ChiTietPhieuXuat ob : list) {
            model.addRow(new Object[]{ob.getMaPhieuXuat(), ob.getMaSanPham(), ob.getSoLuongXuat()});
        }
    }
    
    void clearTableCTPX() {
        DefaultTableModel model = (DefaultTableModel) tblChitietphieuxuat.getModel();
        model.setRowCount(0);
    }

    // Tab lich su phieu nhap
    void fillTablePhieuNhapbyDAY(int t, int n) {
        DefaultTableModel model = (DefaultTableModel) tblPhieunhap.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getLichSuNhapHang(t, n);
        for (Object[] ob : list) {
            model.addRow(ob);
        }
    }
    
    void fillTablePhieuNhapAll() {
        DefaultTableModel model = (DefaultTableModel) tblPhieunhap.getModel();
        model.setRowCount(0);
        List<PhieuNhap> list = phieunhapDao.selectAll();
        for (PhieuNhap ob : list) {
            model.addRow(new Object[]{ob.getMaPhieuNhap(), ob.getNgayNhap(), ob.getMaNCC(), ob.getMaCH()});
        }
    }
    
    void fillTableCTPN(int mapn) {
        DefaultTableModel model = (DefaultTableModel) tblChitietphieunhap.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getChiTietPhieuNhap(mapn);
        for (Object[] ob : list) {
            model.addRow(ob);
        }
        List<Object[]> tt = dao.getTongTienNhap(mapn);
        
        for (Object[] ob : tt) {
            if (ob[1] == null) {
                lblTienNhap.setText("0");
            } else {
                lblTienNhap.setText(String.valueOf(ob[1]));
            }            
        }
    }
    
    void clearTableCTPN() {
        DefaultTableModel model = (DefaultTableModel) tblChitietphieunhap.getModel();
        model.setRowCount(0);
        lblTienNhap.setText("0");
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanpham = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        cbonhacungcap = new javax.swing.JComboBox<>();
        lblTrangthai = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        txtngay = new javax.swing.JTextField();
        jLabel121 = new javax.swing.JLabel();
        txtmasanpham = new javax.swing.JTextField();
        jLabel122 = new javax.swing.JLabel();
        txtmaphieunhapxuat = new javax.swing.JTextField();
        jLabel123 = new javax.swing.JLabel();
        btnthem = new javax.swing.JButton();
        txtsoluong = new javax.swing.JTextField();
        jLabel124 = new javax.swing.JLabel();
        btntaophieunhap = new javax.swing.JButton();
        btntaophieuxuat = new javax.swing.JButton();
        btnhuy = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cbomacuahang = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblchitietphieu = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        lbltenphieu = new javax.swing.JLabel();
        btnxacnhan = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblKho = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnRSNhaphang = new javax.swing.JButton();
        clNam1 = new com.toedter.calendar.JYearChooser();
        clThang1 = new com.toedter.calendar.JMonthChooser();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblPhieunhap = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblChitietphieunhap = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblTienNhap = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnRSXuathang = new javax.swing.JButton();
        clNam = new com.toedter.calendar.JYearChooser();
        clThang = new com.toedter.calendar.JMonthChooser();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPhieuxuat = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblChitietphieuxuat = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1196, 614));
        setMinimumSize(new java.awt.Dimension(1196, 614));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("QUẢN LÝ KHO");

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblSanpham.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblSanpham.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblSanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanpham.setEnabled(false);
        jScrollPane3.setViewportView(tblSanpham);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Nhà cung cấp:");

        cbonhacungcap.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbonhacungcap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbonhacungcapActionPerformed(evt);
            }
        });

        lblTrangthai.setForeground(new java.awt.Color(255, 0, 0));
        lblTrangthai.setText("jLabel1");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbonhacungcap, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTrangthai, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbonhacungcap)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTrangthai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel40.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtngay.setEditable(false);
        txtngay.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel121.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel121.setText("Mã phiếu nhập/xuất:");

        txtmasanpham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel122.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel122.setText("Ngày:");

        txtmaphieunhapxuat.setEditable(false);
        txtmaphieunhapxuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel123.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel123.setText("Mã sản phẩm:");

        btnthem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        txtsoluong.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel124.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel124.setText("Số lượng:");

        btntaophieunhap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntaophieunhap.setText("Tạo phiếu nhập");
        btntaophieunhap.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        btntaophieunhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntaophieunhapActionPerformed(evt);
            }
        });

        btntaophieuxuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btntaophieuxuat.setText("Tạo phiếu xuất");
        btntaophieuxuat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        btntaophieuxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntaophieuxuatActionPerformed(evt);
            }
        });

        btnhuy.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnhuy.setForeground(new java.awt.Color(255, 0, 0));
        btnhuy.setText("Hủy");
        btnhuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel121)
                    .addComponent(txtmaphieunhapxuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jLabel123)
                    .addComponent(txtmasanpham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(btntaophieunhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtngay)
                    .addComponent(jLabel122)
                    .addComponent(btntaophieuxuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel124)
                        .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btntaophieunhap, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(btntaophieuxuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmaphieunhapxuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel122, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtngay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel123, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtmasanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnthem, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhuy, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Mã cửa hàng:");

        cbomacuahang.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbomacuahang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbomacuahang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbomacuahangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbomacuahang, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(171, 171, 171))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbomacuahang)
                .addContainerGap())
        );

        jScrollPane7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblchitietphieu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblchitietphieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã sản phẩm", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblchitietphieu.setEnabled(false);
        tblchitietphieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblchitietphieuMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblchitietphieu);

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("Bảng chi tiết phiếu:");

        lbltenphieu.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lbltenphieu.setText("jLabel1");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(lbltenphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addComponent(lbltenphieu))
        );

        btnxacnhan.setBackground(new java.awt.Color(0, 153, 255));
        btnxacnhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxacnhan.setForeground(new java.awt.Color(255, 255, 255));
        btnxacnhan.setText("Xác nhận");
        btnxacnhan.setBorder(null);
        btnxacnhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxacnhanActionPerformed(evt);
            }
        });

        btnxoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnxoa.setForeground(new java.awt.Color(255, 0, 0));
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(btnxacnhan, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnxacnhan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnxoa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jScrollPane4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblKho.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tblKho.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Mã nhà cung cấp"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKho.setEnabled(false);
        jScrollPane4.setViewportView(tblKho);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1187, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane4)
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Nhập/Xuất hàng", jPanel1);

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnRSNhaphang.setText("Hiển thị toàn bộ");
        btnRSNhaphang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRSNhaphangActionPerformed(evt);
            }
        });

        clNam1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clNam1PropertyChange(evt);
            }
        });

        clThang1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clThang1PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(377, 377, 377)
                .addComponent(clThang1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clNam1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnRSNhaphang, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRSNhaphang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clNam1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clThang1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("BẢNG LỊCH SỬ PHIẾU NHẬP"));

        tblPhieunhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã phiếu nhập", "Ngày nhập", "Mã nhà cung cấp", "Mã cửa hàng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieunhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieunhapMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblPhieunhap);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("BẢNG CHI TIẾT PHIẾU NHẬP"));

        tblChitietphieunhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã phiếu nhập", "Mã sản phẩm", "Số lượng nhập", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tblChitietphieunhap);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Tổng tiền nhập:");

        lblTienNhap.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        lblTienNhap.setForeground(new java.awt.Color(255, 0, 0));
        lblTienNhap.setText("0");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTienNhap)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblTienNhap))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Lịch sử nhập hàng", jPanel5);

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnRSXuathang.setText("Hiển thị toàn bộ");
        btnRSXuathang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRSXuathangActionPerformed(evt);
            }
        });

        clNam.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clNamPropertyChange(evt);
            }
        });

        clThang.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                clThangPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(377, 377, 377)
                .addComponent(clThang, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clNam, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnRSXuathang, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnRSXuathang, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clNam, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("BẢNG LỊCH SỬ PHIẾU XUẤT"));

        tblPhieuxuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã phiếu xuất", "Ngày xuất", "Mã cửa hàng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuxuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuxuatMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblPhieuxuat);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("BẢNG CHI TIẾT PHIẾU XUẤT"));

        tblChitietphieuxuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã phiếu xuất", "Mã sản phẩm", "Số lượng xuất"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tblChitietphieuxuat);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Lịch sử xuất hàng", jPanel8);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jLabel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void cbonhacungcapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbonhacungcapActionPerformed
        indexNCC = cbonhacungcap.getSelectedIndex();
        fillTableSanPham(indexNCC);
        // TODO add your handling code here:
    }//GEN-LAST:event_cbonhacungcapActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
        if (Validate()) {
            btnxacnhan.setEnabled(true);
            btnxoa.setEnabled(true);
            tblchitietphieu.setEnabled(true);
            add();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnthemActionPerformed

    private void btntaophieunhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntaophieunhapActionPerformed
        createPhieuNhap();
        // TODO add your handling code here:
    }//GEN-LAST:event_btntaophieunhapActionPerformed

    private void btntaophieuxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntaophieuxuatActionPerformed
        createPhieuXuat();
        // TODO add your handling code here:
    }//GEN-LAST:event_btntaophieuxuatActionPerformed

    private void btnhuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhuyActionPerformed
        cancel();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnhuyActionPerformed

    private void cbomacuahangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbomacuahangActionPerformed
        indexCH = cbomacuahang.getSelectedIndex();
        fillTableKho(indexCH);
        // TODO add your handling code here:
    }//GEN-LAST:event_cbomacuahangActionPerformed

    private void tblchitietphieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblchitietphieuMouseClicked
        row = tblchitietphieu.getSelectedRow();
        // TODO add your handling code here:
    }//GEN-LAST:event_tblchitietphieuMouseClicked

    private void btnxacnhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxacnhanActionPerformed
        updateKho();
        indexCH = cbomacuahang.getSelectedIndex();
        clearForm();
        fillTableKho(indexCH);
        fillTableChitietphieu();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnxacnhanActionPerformed

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
        delete();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnRSXuathangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRSXuathangActionPerformed
        clearTableCTPX();
        fillTablePhieuXuatAll();
    }//GEN-LAST:event_btnRSXuathangActionPerformed

    private void clNamPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clNamPropertyChange
        // TODO add your handling code here:
        nam = clNam.getYear();
        System.out.println(nam);
        fillTablePhieuXuatbyDAY(thang, nam);
        clearTableCTPX();
    }//GEN-LAST:event_clNamPropertyChange

    private void clThangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clThangPropertyChange
        // TODO add your handling code here:
        thang = clThang.getMonth() + 1;
        System.out.println(thang);
        fillTablePhieuXuatbyDAY(thang, nam);
        clearTableCTPX();
    }//GEN-LAST:event_clThangPropertyChange

    private void tblPhieuxuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuxuatMouseClicked
        int mapx = (Integer) tblPhieuxuat.getValueAt(Integer.valueOf(tblPhieuxuat.getSelectedRow()), 0);
        fillTableCTPX(mapx);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPhieuxuatMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        fillTablePhieuXuatAll();
        clearTableCTPX();
        fillTablePhieuNhapAll();
        clearTableCTPN();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void btnRSNhaphangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRSNhaphangActionPerformed
        clearTableCTPN();
        fillTablePhieuNhapAll();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRSNhaphangActionPerformed

    private void clNam1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clNam1PropertyChange
        nam1 = clNam1.getYear();
        fillTablePhieuNhapbyDAY(thang1, nam1);
        clearTableCTPX();
        // TODO add your handling code here:
    }//GEN-LAST:event_clNam1PropertyChange

    private void clThang1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_clThang1PropertyChange
        thang1 = clThang1.getMonth() + 1;
        fillTablePhieuNhapbyDAY(thang1, nam1);
        clearTableCTPN();
        // TODO add your handling code here:
    }//GEN-LAST:event_clThang1PropertyChange

    private void tblPhieunhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieunhapMouseClicked
        int mapn = (Integer) tblPhieunhap.getValueAt(Integer.valueOf(tblPhieunhap.getSelectedRow()), 0);
        fillTableCTPN(mapn);
        // TODO add your handling code here:
    }//GEN-LAST:event_tblPhieunhapMouseClicked

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
            java.util.logging.Logger.getLogger(QLKho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QLKho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QLKho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
            
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QLKho.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QLKho().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRSNhaphang;
    private javax.swing.JButton btnRSXuathang;
    private javax.swing.JButton btnhuy;
    private javax.swing.JButton btntaophieunhap;
    private javax.swing.JButton btntaophieuxuat;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btnxacnhan;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbomacuahang;
    private javax.swing.JComboBox<String> cbonhacungcap;
    private com.toedter.calendar.JYearChooser clNam;
    private com.toedter.calendar.JYearChooser clNam1;
    private com.toedter.calendar.JMonthChooser clThang;
    private com.toedter.calendar.JMonthChooser clThang1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTienNhap;
    private javax.swing.JLabel lblTrangthai;
    private javax.swing.JLabel lbltenphieu;
    private javax.swing.JTable tblChitietphieunhap;
    private javax.swing.JTable tblChitietphieuxuat;
    private javax.swing.JTable tblKho;
    private javax.swing.JTable tblPhieunhap;
    private javax.swing.JTable tblPhieuxuat;
    private javax.swing.JTable tblSanpham;
    private javax.swing.JTable tblchitietphieu;
    private javax.swing.JTextField txtmaphieunhapxuat;
    private javax.swing.JTextField txtmasanpham;
    private javax.swing.JTextField txtngay;
    private javax.swing.JTextField txtsoluong;
    // End of variables declaration//GEN-END:variables
}
