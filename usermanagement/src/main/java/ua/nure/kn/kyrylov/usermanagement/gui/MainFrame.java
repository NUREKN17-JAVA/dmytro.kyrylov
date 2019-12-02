package ua.nure.kn.kyrylov.usermanagement.gui;

import ua.nure.kn.kyrylov.usermanagement.db.classes.utils.DaoFactory;
import ua.nure.kn.kyrylov.usermanagement.db.exception.DatabaseException;
import ua.nure.kn.kyrylov.usermanagement.db.interfaces.dao.UserDao;
import ua.nure.kn.kyrylov.usermanagement.util.Messages;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static final int HEIGHT = 600;
    public static final int WIDTH = 800;
    private JPanel contentPanel;
    private BrowsePanel browsePanel;
    private JPanel addPanel;
    private UserDao userDao;
    private JPanel deletePanel;
    private JPanel editPanel;
    private JPanel detailsPanel;

    public MainFrame() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super();
        setUserDao(DaoFactory.getInstance().getUserDao());
        initialize();
    }

    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle(Messages.getString("userManagementText"));
        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() {
        if (this.contentPanel == null) {
            this.contentPanel = new JPanel();
            this.contentPanel.setLayout(new BorderLayout());
            this.contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }

    private JPanel getBrowsePanel() {
        if (this.browsePanel == null) {
            this.browsePanel = new BrowsePanel(this);
        }
        this.browsePanel.initTable();
        return browsePanel;
    }

    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    public void showDeletePanel(long userId) {
        showPanel(getDeletePanel(userId));
    }

    private JPanel getDeletePanel(long userId) {
        try {
            return new DeletePanel(this,getUserDao().findUser(userId));
        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showEditPanel(long userId) {
        showPanel(getEditPanel(userId));
    }

    private JPanel getEditPanel(long userId) {
        try {
            return new EditPanel(this,getUserDao().findUser(userId));
        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showDetailsPanel(long userId) {
        showPanel(getDetailsPanel(userId));
    }

    private JPanel getDetailsPanel(long userId) {
        try {
            return new DetailsPanel(this,getUserDao().findUser(userId));
        } catch (DatabaseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void showPanel(JPanel panel) {
        getContentPanel().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private JPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
