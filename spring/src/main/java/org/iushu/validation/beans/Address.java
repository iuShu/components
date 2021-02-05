package org.iushu.validation.beans;

/**
 * @author iuShu
 * @since 1/25/21
 */
public class Address {

    private String country;
    private String province;
    private String city;
    private String district;
    private String sectionOrVillage;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSectionOrVillage() {
        return sectionOrVillage;
    }

    public void setSectionOrVillage(String sectionOrVillage) {
        this.sectionOrVillage = sectionOrVillage;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", sectionOrVillage='" + sectionOrVillage + '\'' +
                '}';
    }
}
