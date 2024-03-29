package com.resmenu.constants;

public class ApiUrls {

    // user login
    public static final String mUrlLogin="http://webapi.emenuhotels.in/token";

    // all categotry
    public static final String mUrlMenuList = "http://webapi.emenuhotels.in/api/Menu/GetActiveMenu";

    //get Staff
    public static final String mUrlStaff="http://webapi.emenuhotels.in/api/Account/GetWaiters";

    public static final String mUrlSubCategories = "http://webapi.emenuhotels.in/api/Item/GetItemByMenuId";
    public static final String mUrlSubmitOrder = "http://webapi.emenuhotels.in/api/kitchen/SendOrderTokitchen";

   // http://webapi.emenuhotels.in/api/ItemType/GetItemTypeList?HotelId=1
    //get all table
    public static final String mUrlTableList="http://webapi.emenuhotels.in/api/Table/GetAllActiveTable";
    public static final String mUrlGetOrder="http://webapi.emenuhotels.in/api/kitchen/FetchOrderForKitchenByTableId";
    public static final String mUrlGetBill="http://webapi.emenuhotels.in/api/Order/GenerateBill";
    public static final String mUrlSubmitFeedBack="http://webapi.emenuhotels.in/api/FeedBack/CreateFeedback";
    public static final String mUrlSearch="http://webapi.emenuhotels.in/api/Item/SearchItemByName";
    public static final String mUrlConfirmOrder="http://webapi.emenuhotels.in/api/kitchen/ConfirmOrderByTableId";

}
