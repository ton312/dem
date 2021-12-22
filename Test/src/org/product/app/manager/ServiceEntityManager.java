package org.product.app.manager;

import org.product.app.App;
import org.product.app.entity.ServiceEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEntityManager {
    static public void insert(ServiceEntity serviceEntity) throws SQLException {
        try(Connection c = App.getConnection()){
            String sql = "INSERT INTO SERVICE(Title, DurationInSeconds, Cost, Discount, Description, MainImagePath) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, serviceEntity.getTitle());
            ps.setInt(2, serviceEntity.getDuration());
            ps.setDouble(3, serviceEntity.getCost());
            ps.setDouble(4, serviceEntity.getDiscount());
            ps.setString(5, serviceEntity.getDesc());
            ps.setString(6, serviceEntity.getImagePath());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if(keys.next()){
                serviceEntity.setID(keys.getInt(1));
                return;
            }

            throw new SQLException("entity not added");
        }
    }
    static public void update(ServiceEntity serviceEntity) throws SQLException {
        try(Connection c = App.getConnection()){
            String sql = "update SERVICE SET Title=?, DurationInSeconds=?, Cost=?, Discount=?, Description=?, MainImagePath=? where ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, serviceEntity.getTitle());
            ps.setInt(2, serviceEntity.getDuration());
            ps.setDouble(3, serviceEntity.getCost());
            ps.setDouble(4, serviceEntity.getDiscount());
            ps.setString(5, serviceEntity.getDesc());
            ps.setString(6, serviceEntity.getImagePath());
            ps.setInt(7, serviceEntity.getID());
            ps.executeUpdate();
        }
    }
    static public List<ServiceEntity> selectAll() throws SQLException {
        try(Connection c = App.getConnection()){
            String sql = "SELECT * FROM mdb.service";
            Statement ps = c.createStatement();
            ResultSet resultSet = ps.executeQuery(sql);
            List<ServiceEntity> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(new ServiceEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("Title"),
                        resultSet.getInt("DurationInSeconds"),
                        resultSet.getDouble("Cost"),
                        resultSet.getDouble("Discount"),
                        resultSet.getString("Description"),
                        resultSet.getString("MainImagePath")
                ));
            }

            return list;
        }
    }
    public static void delete(ServiceEntity entity) throws SQLException{
        try(Connection c = App.getConnection()){
            String sql = "DELETE FROM Service WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, entity.getID());
            ps.executeUpdate();
        }

    }
}
