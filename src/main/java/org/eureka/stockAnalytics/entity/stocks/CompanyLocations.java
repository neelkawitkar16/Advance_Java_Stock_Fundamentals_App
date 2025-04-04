package org.eureka.stockAnalytics.entity.stocks;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "company_locations", schema = "endeavour")
public class CompanyLocations {
    @Column(name = "ticker_symbol")
    @Id
    private String tickerSymbol;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private String zip;
    @OneToOne(mappedBy = "location")
    private StocksFundamentals stock;

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public StocksFundamentals getStock() {
        return stock;
    }

    public void setStock(StocksFundamentals stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompanyLocations that)) return false;
        return Objects.equals(getTickerSymbol(), that.getTickerSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTickerSymbol());
    }

    @Override
    public String toString() {
        return "CompanyLocations{" +
                "tickerSymbol='" + tickerSymbol + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                "}\n";
    }
}
