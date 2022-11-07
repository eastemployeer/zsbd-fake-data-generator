package pl.kpkpur.zsbddatagenerator.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address {
  public Address(
      Long addressId,
      String country,
      String region,
      String city,
      String postalCode,
      String street,
      String buildingNumber) {
    this.addressId = addressId;
    this.country = country;
    this.region = region;
    this.city = city;
    this.postalCode = postalCode;
    this.street = street;
    this.buildingNumber = buildingNumber;
  }

  @Id
  @Column(name = "ADDRESS_ID")
  private Long addressId;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "REGION")
  private String region;

  @Column(name = "CITY")
  private String city;

  @Column(name = "POSTAL_CODE")
  private String postalCode;

  @Column(name = "STREET")
  private String street;

  @Column(name = "BUILDING_NUMBER")
  private String buildingNumber;
}
