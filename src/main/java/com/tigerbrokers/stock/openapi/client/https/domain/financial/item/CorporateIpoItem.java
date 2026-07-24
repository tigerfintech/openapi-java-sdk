package com.tigerbrokers.stock.openapi.client.https.domain.financial.item;

import java.time.LocalDate;

public class CorporateIpoItem extends CorporateActionItem {

  private String country;
  private LocalDate listingDate;
  private Double listingPrice;
  private Long sharesOutstanding;
  private Long sharesFloat;
  private Double offerAmount;
  private String priceRange;
  private String currency;
  private Integer minPurchaseQuantity;
  private Double leverageRatio;
  private String ipoName;

  public String getCountry() { return country; }
  public void setCountry(String country) { this.country = country; }

  public LocalDate getListingDate() { return listingDate; }
  public void setListingDate(LocalDate listingDate) { this.listingDate = listingDate; }

  public Double getListingPrice() { return listingPrice; }
  public void setListingPrice(Double listingPrice) { this.listingPrice = listingPrice; }

  public Long getSharesOutstanding() { return sharesOutstanding; }
  public void setSharesOutstanding(Long sharesOutstanding) { this.sharesOutstanding = sharesOutstanding; }

  public Long getSharesFloat() { return sharesFloat; }
  public void setSharesFloat(Long sharesFloat) { this.sharesFloat = sharesFloat; }

  public Double getOfferAmount() { return offerAmount; }
  public void setOfferAmount(Double offerAmount) { this.offerAmount = offerAmount; }

  public String getPriceRange() { return priceRange; }
  public void setPriceRange(String priceRange) { this.priceRange = priceRange; }

  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }

  public Integer getMinPurchaseQuantity() { return minPurchaseQuantity; }
  public void setMinPurchaseQuantity(Integer minPurchaseQuantity) { this.minPurchaseQuantity = minPurchaseQuantity; }

  public Double getLeverageRatio() { return leverageRatio; }
  public void setLeverageRatio(Double leverageRatio) { this.leverageRatio = leverageRatio; }

  public String getIpoName() { return ipoName; }
  public void setIpoName(String ipoName) { this.ipoName = ipoName; }

  @Override
  public String toString() {
    return "CorporateIpoItem{" +
        "ipoName='" + ipoName + '\'' +
        ", listingDate=" + listingDate +
        ", listingPrice=" + listingPrice +
        ", country='" + country + '\'' +
        ", sharesOutstanding=" + sharesOutstanding +
        ", sharesFloat=" + sharesFloat +
        ", offerAmount=" + offerAmount +
        ", priceRange='" + priceRange + '\'' +
        ", currency='" + currency + '\'' +
        ", minPurchaseQuantity=" + minPurchaseQuantity +
        ", leverageRatio=" + leverageRatio +
        ", symbol='" + getSymbol() + '\'' +
        ", market='" + getMarket() + '\'' +
        ", executeDate=" + getExecuteDate() +
        ", actionType=" + getActionType() +
        '}';
  }
}
