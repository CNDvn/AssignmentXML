/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.product;

/**
 *
 * @author CND
 */
public class ProductDTO {
    private String id;
    private String idCategory;
    private String name;
    private String image;
    private double price;
    private String dateCreate;
    private String description;

    public ProductDTO(String id, String idCategory, String name, String image, double price, String dateCreate, String description) {
        this.id = id;
        this.idCategory = idCategory;
        this.name = name;
        this.image = image;
        this.price = price;
        this.dateCreate = dateCreate;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "id=" + id + ", idCategory=" + idCategory + ", name=" + name + ", image=" + image + ", price=" + price + ", dateCreate=" + dateCreate + ", description=" + description + '}';
    }

}
