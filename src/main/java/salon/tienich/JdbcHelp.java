/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salon.tienich;
import java.sql.*;
/**
 *
 * @author mrphu
 */
public class JdbcHelp {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String url ="jdbc:sqlserver://localhost;database=DUAN1;trustServerCertificate=true;";
    private static String user ="sa";
    private static String pass = "18B122532a";
    
    static{
        try{
            Class.forName(driver);
        }
        catch(ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }
    }
     /* Xây dựng PreparedStatement
    sql là câu lệnh SQL có thể chứa tham số, nó có thể là 1 lời gọi thủ tục lưu
    args là danh sách các giá trị đưuọc cung cấp cho các tham số trong câu lênh sql
    return PreparedStatement tạo được
    */
    public static PreparedStatement getStmt(String sql,Object... args)throws SQLException{
        Connection connection = DriverManager.getConnection(url, user, pass);
        PreparedStatement pstmt = null;
        if(sql.trim().startsWith("{")){
            pstmt = connection.prepareCall(sql);
        }
        else{
            pstmt = connection.prepareStatement(sql);
        }
        for(int i =0; i< args.length; i++){
            pstmt.setObject(i+1, args[i]);
        }
        return pstmt;
    }
    
    /*Thực hiện câu lệnh SQL thao tác (INSERT, UPDATE,DELETE) hoặc thủ tục lưu 
    sql là câu lệnh SQL có thể chứa tham số, có thể là 1 lời gọi thử tục lưu*/
    public static int update(String sql,Object... args){
        try{
            PreparedStatement stmt = getStmt(sql,args);
            try{
                return stmt.executeUpdate();
            }
            finally{
                stmt.getConnection().close();
            }
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    /*Thực hiện câu lệnh SQL truy vấn (select) hoặc thủ tục*/
    public static ResultSet query(String sql, Object... args){
        try{
            PreparedStatement stmt = getStmt(sql,args);
            return stmt.executeQuery();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public static Object value(String sql, Object... args){
        try{
            ResultSet rs = query(sql,args);
            if(rs.next()){
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void main(String[] args) throws SQLException {
        JdbcHelp a = new JdbcHelp();
        Connection cn = DriverManager.getConnection(url, user, pass);
        System.out.println("connect");
    }
}
