package org.product.app.ui;

import org.product.app.entity.ServiceEntity;
import org.product.app.manager.ServiceEntityManager;
import org.product.app.util.BaseForm;
import org.product.app.util.CustomTableModel;
import org.product.app.util.DialogUtil;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;
    private JButton costSortButton;
    private JComboBox discountFilterBox;
    private JLabel rowCountLabel;
    private CustomTableModel<ServiceEntity> model;

    private boolean costSort = false;

    public ServiceTableForm() {
        super(1200, 800);
        setContentPane(mainPanel);
        initTable();
        initTableButton();
        initBoxes();
        setVisible(true);
    }
    private void initTable(){
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        try {
            model = new CustomTableModel<>(
                    ServiceEntity.class,
                    new String[]{"ID","Название","Длительность","Стоимость","Скидка","Описание","Путь до картинки", "Картинка"},
                    ServiceEntityManager.selectAll()
            );
            table.setModel(model);
            TableColumn column = table.getColumn("Путь до картинки");
            column.setMinWidth(0);
            column.setPreferredWidth(0);
            column.setMaxWidth(0);
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError(this, "Ошибка получения данных" + e.getMessage());
        }
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2){
                    int row = table.rowAtPoint(mouseEvent.getPoint());
                    if(row != -1) {
                        dispose();
                        new ServiceUpdateForm(model.getRows().get(row));
                    }
                }
            }
        });
        updateRowCountLabel(model.getRows().size(), model.getRows().size());
    }
    private void initBoxes(){
        discountFilterBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) applyFilters();
        });
    }
    private void applyFilters(){
        try {

            List<ServiceEntity> list = ServiceEntityManager.selectAll();
            int max = list.size();
            switch (discountFilterBox.getSelectedIndex()){
                case 1:
                    list.removeIf(s -> s.getDiscount() >= 5);
                    break;
                case 2:
                    list.removeIf(s -> s.getDiscount() < 5 || s.getDiscount() >= 15);
                    break;
                case 3:
                    list.removeIf(s -> s.getDiscount() < 15 || s.getDiscount() >= 30);
                    break;
                case 4:
                    list.removeIf(s -> s.getDiscount() < 30 || s.getDiscount() >= 70);
                    break;
                case 5:
                    list.removeIf(s -> s.getDiscount() < 70);
                    break;
            }
            model.setRows(list);
            updateRowCountLabel(list.size(), max);
            model.fireTableDataChanged();
            costSort = false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            DialogUtil.showError(this, "Ошибка при работе с сортировкой");
        }

    }

    private void updateRowCountLabel(int actual, int max){
        rowCountLabel.setText("Отображено записей: " + actual + " / " + max);
    }

    private void initTableButton(){
        addButton.addActionListener(e->{
            dispose();
            new ServiceCreateForm();
        });
        costSortButton.addActionListener(e -> {
            Collections.sort(model.getRows(), new Comparator<ServiceEntity>() {
                @Override
                public int compare(ServiceEntity entity, ServiceEntity t1) {
                    if(costSort){
                        return Double.compare(t1.getCost(), entity.getCost());
                    }else{
                        return Double.compare(entity.getCost(), t1.getCost());
                    }
                }
            });
            costSort = !costSort;
            model.fireTableDataChanged();
        });
    }
}
