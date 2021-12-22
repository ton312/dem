package org.product.app.ui;

import org.product.app.entity.ServiceEntity;
import org.product.app.manager.ServiceEntityManager;
import org.product.app.util.BaseForm;
import org.product.app.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class ServiceCreateForm extends BaseForm {
    private JTextField TitleField;
    private JSpinner DurationField;
    private JTextField CostField;
    private JTextField DescriptionField;
    private JTextField DiscountField;
    private JTextField MainImagePathField;
    private JButton backButton;
    private JButton saveButton;
    private JPanel mainPanel;

    public ServiceCreateForm(){
        super(425, 375);
        setContentPane(mainPanel);
        initButtons();
        setVisible(true);
    }
    private void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });
        saveButton.addActionListener(e -> {
            String title = TitleField.getText();
            if(title.isEmpty() || title.length() > 100 ) {
                DialogUtil.showError(this, "Название не введено или слишком короткое");
                return;
            }

            int duration =(int) DurationField.getValue();
            if(duration<0) {
                DialogUtil.showError(this, "Длительность введена не корректно");
                return;
            }

            double cost =0;
            try {
                cost = Double.parseDouble(CostField.getText());
            } catch (Exception ex) {
                DialogUtil.showError(this, "Стоимость");
                return;
            }
            if(cost<0) {
                DialogUtil.showError(this, "Длительность введена не корректно");
                return;
            }

            double discount =0;
            try {
                discount = Double.parseDouble(CostField.getText());
            } catch (Exception ex) {
                DialogUtil.showError(this, "Стоимость");
                return;
            }
            if(discount<0) {
                DialogUtil.showError(this, "Длительность введена не корректно");
                return;
            }
            String desc = DiscountField.getText();
            String imagePath = MainImagePathField.getText();
            try {
                ServiceEntityManager.insert(new ServiceEntity(-1, title, duration, cost, discount, desc, imagePath));
                DialogUtil.showInfo(this, "Запись добавлена");
                dispose();
                new ServiceTableForm();
            } catch (SQLException throwables) {
                DialogUtil.showError(this, "Ошибка при создании");
            }

        });
    }
}
