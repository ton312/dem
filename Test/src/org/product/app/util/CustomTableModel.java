package org.product.app.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;

@AllArgsConstructor
public class CustomTableModel<T> extends AbstractTableModel {

    private Class<T> cls;
    private String[] columnNames;

    @Getter @Setter
    private List<T> rows;

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public String getColumnName(int i) {
        return i < columnNames.length ? columnNames[i] : "Column title";
    }

    @Override
    public Class<?> getColumnClass(int i) {
        return cls.getDeclaredFields()[i].getType();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "Error";
        }

    }
}
