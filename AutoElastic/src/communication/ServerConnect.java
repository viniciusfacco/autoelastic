/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import com.jcraft.jsch.UserInfo;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinicius
 */
public class ServerConnect implements UserInfo {
    
    String passwd;

    public ServerConnect(String pwd) {
        this.passwd = pwd;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return passwd;
    }

    @Override
    public boolean promptPassword(String string) {
         return true;
    }

    @Override
    public boolean promptPassphrase(String string) {
         return true;
    }

    @Override
    public boolean promptYesNo(String string) {
        return true;
    }

    @Override
    public void showMessage(String string) {
        JOptionPane.showMessageDialog(null, string);
    }
    
}
