package com.example.quickpack.Data;

import com.example.quickpack.Constants.MyConstants;
import com.example.quickpack.Models.UserItem;
import com.example.quickpack.Models.UserItems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class UserItemData {
    public static List<UserItem> getDefaultBasicNeedsUserItems() {
        return Arrays.asList(new UserItem("visa", MyConstants.BASIC_NEEDS_CAMEL_CASE, false,true),
                            new UserItem("passport", MyConstants.BASIC_NEEDS_CAMEL_CASE, false, true),
                            new UserItem("tickets", MyConstants.BASIC_NEEDS_CAMEL_CASE, false,true),
                            new UserItem("wallet", MyConstants.BASIC_NEEDS_CAMEL_CASE, false, true)
                            , new UserItem("driving license", MyConstants.BASIC_NEEDS_CAMEL_CASE, false,true)
                         ,new UserItem("currency", MyConstants.BASIC_NEEDS_CAMEL_CASE, false, true)
                        , new UserItem("book", MyConstants.BASIC_NEEDS_CAMEL_CASE, false,true)
                    , new UserItem("house key", MyConstants.BASIC_NEEDS_CAMEL_CASE, false, true)
                , new UserItem("travel pillow", MyConstants.BASIC_NEEDS_CAMEL_CASE, false,true)
                , new UserItem("umbrella", MyConstants.BASIC_NEEDS_CAMEL_CASE, false, true)
                , new UserItem("note book", MyConstants.BASIC_NEEDS_CAMEL_CASE,false, true));
    }

    public static List<UserItem> getDefaultPersonalCareUserItem() {
        return Arrays.asList(new UserItem("tooth-brush", MyConstants.PERSONAL_CARE_CAMEL_CASE, false, true),
                 new UserItem("tooth-paste", MyConstants.PERSONAL_CARE_CAMEL_CASE, false, true),
                new UserItem("floss", MyConstants.PERSONAL_CARE_CAMEL_CASE, false, true),
             new UserItem("soap", MyConstants.PERSONAL_CARE_CAMEL_CASE, false,true),
             new UserItem("makeup", MyConstants.PERSONAL_CARE_CAMEL_CASE, false, true),
            new UserItem("perfume", MyConstants.PERSONAL_CARE_CAMEL_CASE, false,true),
             new UserItem("pad", MyConstants.PERSONAL_CARE_CAMEL_CASE, false, true),
            new UserItem("brush", MyConstants.PERSONAL_CARE_CAMEL_CASE, false,true)


        );
    }
    public static List<UserItem> getDefaultClothingUserItem() {
        return Arrays.asList(new UserItem("shirt", MyConstants.CLOTHING_CAMEL_CASE, false, true),
                new UserItem("underwear", MyConstants.CLOTHING_CAMEL_CASE, false,true),
         new UserItem("pajamas", MyConstants.CLOTHING_CAMEL_CASE, false, true),
         new UserItem("t-shirt", MyConstants.CLOTHING_CAMEL_CASE, false,true),
         new UserItem("dress", MyConstants.CLOTHING_CAMEL_CASE, false, true),
         new UserItem("skirt", MyConstants.CLOTHING_CAMEL_CASE, false, true),
         new UserItem("jacket", MyConstants.CLOTHING_CAMEL_CASE, false,true),
         new UserItem("suit", MyConstants.CLOTHING_CAMEL_CASE, false, true),
         new UserItem("jeans", MyConstants.CLOTHING_CAMEL_CASE, false,true),
         new UserItem("vest", MyConstants.CLOTHING_CAMEL_CASE, false, true)
        );
    }

    public static List<UserItem> getDefaultBabyNeedsUserItem() {
        return Arrays.asList(new UserItem("jumpsuit", MyConstants.BABY_NEEDS_CAMEL_CASE, false, true),
                 new UserItem("blanket", MyConstants.BABY_NEEDS_CAMEL_CASE, false,true),
         new UserItem("diaper", MyConstants.BABY_NEEDS_CAMEL_CASE, false, true),
         new UserItem("stroller", MyConstants.BABY_NEEDS_CAMEL_CASE, false,true),
         new UserItem("wet wiper", MyConstants.BABY_NEEDS_CAMEL_CASE, false, true),
         new UserItem("potty", MyConstants.BABY_NEEDS_CAMEL_CASE, false, true),
         new UserItem("toys", MyConstants.BABY_NEEDS_CAMEL_CASE, false,true),
         new UserItem("probiotic power", MyConstants.BABY_NEEDS_CAMEL_CASE, false, true)

        );
    }

    public static List<UserItem> getDefaultHealthUserItem() {
        return Arrays.asList(new UserItem("aspirin", MyConstants.HEALTH_CAMEL_CASE, false, true),

               new UserItem("vitamins", MyConstants.HEALTH_CAMEL_CASE, false,true),
        new UserItem("plaster", MyConstants.HEALTH_CAMEL_CASE, false, true),
         new UserItem("pain reliever", MyConstants.HEALTH_CAMEL_CASE, false,true),
         new UserItem("fever reducer", MyConstants.HEALTH_CAMEL_CASE, false, true)

        );
    }

    public static List<UserItem> getDefaultTechnologyUserItem() {
        return Arrays.asList(new UserItem("mobile phone", MyConstants.TECHNOLOGY_CAMEL_CASE, false, true),
                new UserItem("camera", MyConstants.TECHNOLOGY_CAMEL_CASE, false,true),
         new UserItem("head phone", MyConstants.TECHNOLOGY_CAMEL_CASE, false, true),
         new UserItem("flash light", MyConstants.TECHNOLOGY_CAMEL_CASE, false, true),
         new UserItem("laptop", MyConstants.TECHNOLOGY_CAMEL_CASE, false,true),
         new UserItem("power bank", MyConstants.TECHNOLOGY_CAMEL_CASE, false, true),
         new UserItem("charger", MyConstants.TECHNOLOGY_CAMEL_CASE, false, true),
         new UserItem("extension cable", MyConstants.TECHNOLOGY_CAMEL_CASE, false,true),
         new UserItem("voltage adapter", MyConstants.TECHNOLOGY_CAMEL_CASE, false, true)

        );
    }

    public static List<UserItem> getDefaultUserItemsByCategory(String category) {
        if(category.equals(MyConstants.BASIC_NEEDS_CAMEL_CASE)) {
            return getDefaultBasicNeedsUserItems();
        } else if(category.equals(MyConstants.BABY_NEEDS_CAMEL_CASE)) {
            return getDefaultBabyNeedsUserItem();
        } else if(category.equals(MyConstants.TECHNOLOGY_CAMEL_CASE)) {
            return getDefaultTechnologyUserItem();
        } else if(category.equals(MyConstants.CLOTHING_CAMEL_CASE)) {
            return getDefaultClothingUserItem();
        }else if(category.equals(MyConstants.PERSONAL_CARE_CAMEL_CASE)) {
            return getDefaultPersonalCareUserItem();
        } else {
            return getDefaultHealthUserItem();
        }
    }

    public static UserItems getDefaultUserItems(String uid) {
        UserItems userItems = new UserItems(uid, null);

        for(UserItem userItem : getDefaultPersonalCareUserItem()) {
            userItems.addUserItem(userItem);
        }
        for(UserItem userItem : getDefaultTechnologyUserItem()) {
            userItems.addUserItem(userItem);
        }
        for(UserItem userItem : getDefaultHealthUserItem()) {
            userItems.addUserItem(userItem);
        }
        for(UserItem userItem : getDefaultClothingUserItem()) {
            userItems.addUserItem(userItem);
        }
        for(UserItem userItem : getDefaultBabyNeedsUserItem()) {
            userItems.addUserItem(userItem);
        }
        for(UserItem userItem : getDefaultBasicNeedsUserItems()) {
            userItems.addUserItem(userItem);
        }

        return userItems;
    }
}
