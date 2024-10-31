/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.tienich;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author mrphu
 */
public class MsgBox {
    /*Hiển thị thông báo cho người dùng
    parent : cửa số chứa thông báo
    message: thông báo
    */
    
    public static void alert(Component parent, String message){
        JOptionPane.showMessageDialog(parent, message,"Hair Salon ACADEMY",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /*Hiển thị thông báo và yêu cầu người dùng xác nhận
    parent: cửa số chứa thông báo
    message: câu hỏi yes/no
    return: kết quả*/
    
    public static boolean confirm(Component parent, String message){
        int result = JOptionPane.showConfirmDialog(parent, message,"Hair Salon ACADEMY",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    /*Hiển thị thông báo yêu cầu nhập dữ liệu
    parent: cửa số chứa thông báo
    message: Thông báo nhắc nhở nhập
    return: kết quả nhận được từ người dùng nhập*/
    
    public static String prompt(Component parent,String message){
        return JOptionPane.showInputDialog(parent, message,"Hair Salon ACADEMY",JOptionPane.INFORMATION_MESSAGE);
    }
}
