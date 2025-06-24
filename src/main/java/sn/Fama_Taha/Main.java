package sn.Fama_Taha;

import javax.swing.SwingUtilities;

import sn.Fama_Taha.config.HibernateUtils;
import sn.Fama_Taha.view.LoginView;

public class Main {
    public static void main(String[] args) {
        try {
            HibernateUtils.getSession();
            SwingUtilities.invokeLater(LoginView::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}