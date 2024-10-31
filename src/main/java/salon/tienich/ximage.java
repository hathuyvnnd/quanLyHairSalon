/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salon.tienich;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author Duy Toan
 */
public class ximage {
    public static Image getappion(){
        URL url = ximage.class.getResource("C:\\ki 4\\duanmau\\src\\img");
        return new ImageIcon(url).getImage();
    }
    
    
//    public static ImageIcon read (String filename){
//        File path = new File("logos",filename);
//        return new ImageIcon(path.getAbsolutePath());
//    }
//    
//    public static void save(File src){
//        File dst = new File("logos", src.getName());
//        if(!dst.getParentFile().exists()){
//            dst.getParentFile().mkdirs();
//        }
//        try {
//            Path from =Paths.get(src.getAbsolutePath());
//            Path to  = Paths.get(dst.getAbsolutePath());
//            Files.copy(from, to,StandardCopyOption.REPLACE_EXISTING);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
        public static ImageIcon read(String filename) {
        // Construct the file path based on the "logos" directory
        File path = new File("logos", filename);
        // Check if the file exists
        if (!path.exists()) {
            // If the file doesn't exist, return null
            return null;
        }
        // Create and return ImageIcon from the file path
        return new ImageIcon(path.getAbsolutePath());
    }

    // Method to save a file to the "logos" directory
    public static void save(File src) {
        // Create a file object representing the destination directory
        File dstDir = new File("logos");
        // Ensure the destination directory exists; if not, create it
        if (!dstDir.exists()) {
            dstDir.mkdirs();
        }
        // Create a file object representing the destination file
        File dstFile = new File(dstDir, src.getName());
        try {
            // Copy the source file to the destination file
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dstFile.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            // Handle any exceptions by throwing a runtime exception
            throw new RuntimeException("Error saving file: " + e.getMessage());
        }
    }
}
