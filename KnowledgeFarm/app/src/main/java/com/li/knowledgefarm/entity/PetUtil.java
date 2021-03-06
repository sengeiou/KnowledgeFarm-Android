package com.li.knowledgefarm.entity;

/**
 * @ClassName A
 * @Description
 * @Author 张帅华
 * @Date 2020-05-04 09:14
 */
public class PetUtil {
    private Integer id;
    private String name;
    private String description;
    private String img;
    private Integer value;
    private Integer price;
    private Integer exist;
    private PetUtilType petUtilType;

    public PetUtil(){
        this.exist = 1;
    }

    public PetUtil(String name, String description, Integer value, Integer price, PetUtilType petUtilType){
        this.name = name;
        this.description = description;
        this.value = value;
        this.price = price;
        this.petUtilType = petUtilType;
        this.exist = 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }

    public PetUtilType getPetUtilType() {
        return petUtilType;
    }

    public void setPetUtilType(PetUtilType petUtilType) {
        this.petUtilType = petUtilType;
    }

}
