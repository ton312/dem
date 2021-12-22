package org.product.app.ui;

import org.product.app.entity.ServiceEntity;
import org.product.app.manager.ServiceEntityManager;
import org.product.app.util.BaseForm;
import org.product.app.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;



public class ServiceUpdateForm extends BaseForm {
    private JTextField TitleField;
    private JSpinner DurationField;
    private JTextField CostField;
    private JTextField DescriptionField;
    private JTextField DiscountField;
    private JTextField MainImagePathField;
    private JButton backButton;
    private JButton saveButton;
    private JPanel mainPanel;
    private JButton deleteButton;
    private JTextField IDField;

    private ServiceEntity serviceEntity;

    public ServiceUpdateForm(ServiceEntity serviceEntity){
        super(425, 375);
        this.serviceEntity = serviceEntity;
        setContentPane(mainPanel);
        initField();
        initButtons();
        setVisible(true);
    }

    private void initField(){
        IDField.setEditable(false);
        IDField.setText(String.valueOf(serviceEntity.getID()));
        TitleField.setText((serviceEntity.getTitle()));
        DurationField.setValue(serviceEntity.getDuration());
        CostField.setText(String.valueOf(serviceEntity.getCost()));
        DiscountField.setText(String.valueOf(serviceEntity.getDiscount()));
        DescriptionField.setText(serviceEntity.getDesc());
        MainImagePathField.setText(serviceEntity.getImagePath());
    }

    private void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new ServiceTableForm();
        });

        deleteButton.addActionListener(e ->{
            if(JOptionPane.showConfirmDialog(this, "Вы точно хотите удалить запись?", "Подтверждение",  JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                try {
                    ServiceEntityManager.delete(serviceEntity);
                    DialogUtil.showInfo(this, "Удаление выполнено");
                    dispose();
                    new ServiceTableForm();
                } catch (SQLException throwables) {
                    DialogUtil.showError(this, "Удаление не выполнено");
                }
            }
        }
        );

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

            double cost=0;
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

            double discount=0;
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
            serviceEntity.setTitle(title);
            serviceEntity.setDuration(duration);
            serviceEntity.setCost(cost);
            serviceEntity.setDiscount(discount);
            serviceEntity.setDesc(desc);
            serviceEntity.setImagePath(imagePath);
            try {
                ServiceEntityManager.update(serviceEntity);
                DialogUtil.showInfo(this, "Запись отредактирована");
                dispose();
                new ServiceTableForm();
            } catch (SQLException throwables) {
                DialogUtil.showError(this, "Ошибка при редакции ");
                System.out.println(throwables);
            }

        });
    }
}
