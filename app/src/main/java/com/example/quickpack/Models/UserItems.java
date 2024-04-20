package com.example.quickpack.Models;

import com.example.quickpack.Constants.MyConstants;
import com.example.quickpack.Data.UserItemData;
import com.example.quickpack.Models.UserItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserItems {
    private String uid;
    private Map<String, UserItem> itemNameToUserItemMap;

    public UserItems() {

    }

    public UserItems(String uid, Map<String, UserItem> itemNameToUserItemMap) {
        this.uid = uid;
        this.itemNameToUserItemMap = itemNameToUserItemMap;
    }

    public String getUid() {
        return uid;
    }

    public Map<String, UserItem> getItemNameToUserItemMap() {
        if(itemNameToUserItemMap == null) {
            return new HashMap<String, UserItem>();
        } else {
            return itemNameToUserItemMap;
        }
    }

    public boolean setUserItemChecked(String userItemName, boolean checked) {
        if(!getItemNameToUserItemMap().containsKey(userItemName)) {
            return false;
        }

        getItemNameToUserItemMap().get(userItemName).setChecked(checked);

        return true;
    }

    public boolean addUserItem(UserItem userItem) {
        if(getItemNameToUserItemMap().containsKey(userItem.getName())) {
            return false;
        }
        if(itemNameToUserItemMap == null) {
            itemNameToUserItemMap = new HashMap<>();
        }

        itemNameToUserItemMap.put(userItem.getName(), userItem);
        return true;
    }

    public boolean removeUserItem(String userItemName) {
        if(!getItemNameToUserItemMap().containsKey(userItemName)) {
            return false;
        }

        itemNameToUserItemMap.remove(userItemName);

        if(itemNameToUserItemMap.size() == 0) {
            itemNameToUserItemMap = null;
        }

        return true;
    }

    public void resetCategoryToDefault(String category) {
        clearCategoryUserItems(category);

        for(UserItem userItem: UserItemData.getDefaultUserItemsByCategory(category)) {
            addUserItem(userItem);
        }
    }

    public void clearCategoryUserItems(String category) {
        List<UserItem> userItemsToRemoveList = new ArrayList<>();

        for(UserItem userItem: getItemNameToUserItemMap().values()) {
            if(userItem.getCategory().equals(category)) {
                userItemsToRemoveList.add(userItem);
            }
        }

        for(UserItem userItem : userItemsToRemoveList) {
            removeUserItem(userItem.getName());
        }
    }

}
