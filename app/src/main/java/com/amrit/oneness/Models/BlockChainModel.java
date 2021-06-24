package com.amrit.oneness.Models;

import com.amrit.oneness.RealTimeDatabase.Item;

public class BlockChainModel {
    Item item;
    String ownerKey;

    public BlockChainModel(String ownerKey, Item item) {
        this.item = item;
        this.ownerKey = ownerKey;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getOwnerKey() {
        return ownerKey;
    }

    public void setOwnerKey(String ownerKey) {
        this.ownerKey = ownerKey;
    }
}
