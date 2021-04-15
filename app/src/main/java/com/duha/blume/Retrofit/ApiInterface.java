package com.duha.blume.Retrofit;


import com.duha.blume.MVP.AddToWishlistResponse;
import com.duha.blume.MVP.CartistResponse;
import com.duha.blume.MVP.CategoryListResponse;
import com.duha.blume.MVP.FAQResponse;
import com.duha.blume.MVP.MyOrdersResponse;
import com.duha.blume.MVP.Product;
import com.duha.blume.MVP.RecommendedProductsResponse;
import com.duha.blume.MVP.RegistrationResponse;
import com.duha.blume.MVP.RestaurantDetailResponse;
import com.duha.blume.MVP.SignUpResponse;
import com.duha.blume.MVP.StripeResponse;
import com.duha.blume.MVP.TermsResponse;
import com.duha.blume.MVP.UserProfileResponse;
import com.duha.blume.MVP.WishlistResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiInterface {

    // API's endpoints
    @GET("/BlumeTrend/app_dashboard/JSON/allitem.php")
    public void getAllProducts(
            Callback<List<Product>> callback);

    @GET("/BlumeTrend/app_dashboard/JSON/recom.php")
    public void getRecommendedProducts(
            Callback<RecommendedProductsResponse> callback);

    @GET("/BlumeTrend/app_dashboard/JSON/pbyc.php")
    public void getCategoryList(Callback<List<CategoryListResponse>> callback);

    @GET("/BlumeTrend/app_dashboard/JSON/resdetails.php")
    public void getRestaurantDetail(Callback<RestaurantDetailResponse> callback);

    @GET("/BlumeTrend/app_dashboard/JSON/faq.php")
    public void getFAQ(Callback<FAQResponse> callback);

    @GET("/BlumeTrend/app_dashboard/JSON/terms.php")
    public void getTerms(Callback<TermsResponse> callback);

    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/pushadd.php")
    public void sendAccessToken(@Field("accesstoken") String accesstoken, Callback<RegistrationResponse> callback);

    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/addwishlist.php")
    public void addToWishList(@Field("product_id") String product_id, @Field("user_id") String user_id, Callback<AddToWishlistResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/add-cart.php")
    public void addToCart(@Field("product_id") String product_id, @Field("userid") String user_id,
                          @Field("varient_id") String varient_id, @Field("varient_quantity") String varient_quantity,
                          @Field("json_param") String json_param, @Field("varient_name") String varient_name,
                          @Field("varient_price") String varient_price, @Field("product_name") String product_name,
                          Callback<AddToWishlistResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/deletecart.php")
    public void deleteCartItem(@Field("user_id") String user_id,
                               @Field("varient_id") String varient_id,
                               @Field("product_id") String product_id, Callback<AddToWishlistResponse> callback);

    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/wishcheck.php")
    public void checkWishList(@Field("product_id") String product_id, @Field("user_id") String user_id, Callback<AddToWishlistResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/wishlist.php")
    public void getWishList(@Field("user_id") String user_id, Callback<WishlistResponse> callback);

    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/vieworders.php")
    public void getMyOrders(@Field("user_id") String user_id, Callback<MyOrdersResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/viewcart.php")
    public void getCartList(@Field("user_id") String user_id, Callback<CartistResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/userprofile.php")
    public void getUserProfile(@Field("user_id") String user_id, Callback<UserProfileResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/updateprofile.php")
    public void updateProfile(@Field("user_id") String user_id,
                              @Field("name") String name,
                              @Field("city") String city,
                              @Field("state") String state,
                              @Field("pincode") String pincode,
                              @Field("local") String local,
                              @Field("flat") String flat,
                              @Field("gender") String gender,
                              @Field("phone") String phone,
                              @Field("landmark") String landmark,
                              Callback<SignUpResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/resentmail.php")
    public void resentEmail(@Field("email") String email, Callback<SignUpResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/login.php")
    public void login(@Field("email") String email, @Field("password") String password, @Field("logintype") String logintype, Callback<SignUpResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/paystripe.php")
    public void stripePayment(@Field("stripeToken") String stripeToken,
                              @Field("total") String total,
                              @Field("user_id") String user_id,
                              @Field("cart_id") String cart_id,
                              @Field("address") String address,
                              @Field("phone") String phone,
                              Callback<StripeResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/addorders.php")
    public void addOrder(@Field("user_id") String user_id,
                         @Field("cart_id") String cart_id,
                         @Field("address") String address,
                         @Field("phone") String phone,
                         @Field("paymentref") String paymentref,
                         @Field("paystatus") String paystatus,
                         @Field("total") String total,
                         @Field("paymentmode") String paymentmode,
                         Callback<SignUpResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/forgot.php")
    public void forgotPassword(@Field("email") String email, Callback<SignUpResponse> callback);


    @FormUrlEncoded
    @POST("/BlumeTrend/app_dashboard/JSON/register.php")
    public void registration(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("logintype") String logintype, Callback<SignUpResponse> callback);


}
