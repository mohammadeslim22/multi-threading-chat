/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package others;

/**
 *
 * @author M.Eslim
 */
import static others.AES_ENC.encrypt;
public class test {
    public static void main(String[] args) {
           String data = encrypt("Bar12345Bar12345", "RandomInitVector", "isamel");
           System.out.println(data);
     
    }
    
}
