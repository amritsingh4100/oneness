package com.amrit.oneness.RealTimeDatabase;

public class Item {
    String itemName,hash;

    public Item(String itemName, String hashKey) {
        this.itemName=itemName;
        this.hash = hashKey;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
