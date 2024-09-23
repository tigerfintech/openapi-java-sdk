package com.tigerbrokers.stock.openapi.client.https.response.quote;

/**
 * Description:
 *
 * @author kevin
 * @date 2022/09/29
 */
public class QuotePermission {
    private String name;
    private long expireAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(long expireAt) {
        this.expireAt = expireAt;
    }
}
