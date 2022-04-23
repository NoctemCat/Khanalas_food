package com.example.Khanalas_food;

public class URLs {
    private static final String ROOT_LOGIN_URL = "http://192.168.0.62:81/Api.php?apicall=";

    public static final String URL_REGISTER = ROOT_LOGIN_URL + "signup";
    public static final String URL_LOGIN= ROOT_LOGIN_URL + "login";

    public static final String URL_ALL_PRODUCTS = "http://192.168.0.62:81/get_all_products.php";

    public static final String ROOT_CART_URL = "http://192.168.0.62:81/api_cart.php?apicall=";
    public static final String URL_ADD_TO_CART= ROOT_CART_URL + "add";
    public static final String URL_GET_CART = ROOT_CART_URL + "getuser";
    public static final String URL_DELETE_CART_ITEM = ROOT_CART_URL + "delete";

}
