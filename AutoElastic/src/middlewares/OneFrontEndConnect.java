/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middlewares;

import com.jcraft.jsch.UserInfo;
import javax.swing.JOptionPane;

/**
 *
 * @author vinicius.rodrigues
 */
public class OneFrontEndConnect implements UserInfo {

    String passwd;

    public OneFrontEndConnect(String senha) {
        this.passwd = senha;
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