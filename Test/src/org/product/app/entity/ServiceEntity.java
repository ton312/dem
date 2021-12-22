package org.product.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.product.app.util.DialogUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class ServiceEntity {
    private int ID;
    private String title;
    private int duration;
    private double cost;
    private double discount;
    private String desc;
    private String imagePath;
    private ImageIcon image;

    public ServiceEntity(int ID, String title, int duration, double cost, double discount, String desc, String imagePath) {
        this.ID = ID;
        this.title = title;
        this.duration = duration;
        this.cost = cost;
        this.discount = discount;
        this.desc = desc;
        this.imagePath = imagePath.replaceAll(Pattern.quote("\\"), "/");

        try {
            this.image = new ImageIcon(ImageIO.read(ServiceEntity.class.getClassLoader().getResource(this.imagePath)
            ).getScaledInstance(50,50,Image.SCALE_DEFAULT));
        } catch (Exception e) {
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }
}
