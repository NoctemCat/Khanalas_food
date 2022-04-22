package com.example.Khanalas_food;

public class URLs {
    private static final String ROOT_LOGIN_URL = "http://192.168.0.62:81/Api.php?apicall=";

    public static final String URL_REGISTER = ROOT_LOGIN_URL + "signup";
    public static final String URL_LOGIN= ROOT_LOGIN_URL + "login";

    public static final String URL_ALL_PRODUCTS = "http://192.168.0.62:81/get_all_products.php";

}
