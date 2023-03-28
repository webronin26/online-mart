package com.webronin_26.online_mart.data.remote

import com.google.gson.annotations.SerializedName

class Response {

    /**
     *  main page
     */
    data class MainResponse (val count: Int, val code: Int, val data: MainResponseData)

    data class MainResponseData (@SerializedName("hot_sales") val hotSales: HotSales,
                                 @SerializedName("latest") val latest: Latest,
                                 @SerializedName("daily_recommended") val dailyRecommended: DailyRecommended,
                                 @SerializedName("may_like") val mayLike: MayLike)

    data class HotSales (@SerializedName("first_id") val firstId: Int,
                         @SerializedName("first_pic") val firstPic: String,
                         @SerializedName("first_text") val firstText: String,
                         @SerializedName("first_price") val firstPrice: Float,

                         @SerializedName("second_id") val secondId: Int,
                         @SerializedName("second_pic") val secondPic: String,
                         @SerializedName("second_text")val secondText: String,
                         @SerializedName("second_price")val secondPrice: Float,

                         @SerializedName("third_id") val thirdId: Int,
                         @SerializedName("third_text")val thirdText: String,
                         @SerializedName("third_pic") val thirdPic: String,
                         @SerializedName("third_price")val thirdPrice: Float,

                         @SerializedName("four_id") val fourId: Int,
                         @SerializedName("four_text")val fourText: String,
                         @SerializedName("four_pic") val fourPic: String,
                         @SerializedName("four_price")val fourPrice: Float,)

    data class Latest (@SerializedName("first_id") val firstId: Int,
                       @SerializedName("first_pic") val firstPic: String,
                       @SerializedName("first_text") val firstText: String,
                       @SerializedName("first_price") val firstPrice: Float,

                       @SerializedName("second_id") val secondId: Int,
                       @SerializedName("second_pic") val secondPic: String,
                       @SerializedName("second_text")val secondText: String,
                       @SerializedName("second_price")val secondPrice: Float,

                       @SerializedName("third_id") val thirdId: Int,
                       @SerializedName("third_text")val thirdText: String,
                       @SerializedName("third_pic") val thirdPic: String,
                       @SerializedName("third_price")val thirdPrice: Float,

                       @SerializedName("four_id") val fourId: Int,
                       @SerializedName("four_text")val fourText: String,
                       @SerializedName("four_pic") val fourPic: String,
                       @SerializedName("four_price")val fourPrice: Float)

    data class DailyRecommended (@SerializedName("first_id") val firstId: Int,
                                 @SerializedName("first_pic") val firstPic: String,
                                 @SerializedName("first_text") val firstText: String,
                                 @SerializedName("first_price") val firstPrice: Float,

                                 @SerializedName("second_id") val secondId: Int,
                                 @SerializedName("second_pic") val secondPic: String,
                                 @SerializedName("second_text")val secondText: String,
                                 @SerializedName("second_price")val secondPrice: Float,

                                 @SerializedName("third_id") val thirdId: Int,
                                 @SerializedName("third_text")val thirdText: String,
                                 @SerializedName("third_pic") val thirdPic: String,
                                 @SerializedName("third_price")val thirdPrice: Float,

                                 @SerializedName("four_id") val fourId: Int,
                                 @SerializedName("four_text")val fourText: String,
                                 @SerializedName("four_pic") val fourPic: String,
                                 @SerializedName("four_price")val fourPrice: Float)

    data class MayLike (@SerializedName("first_id") val firstId: Int,
                        @SerializedName("first_pic") val firstPic: String,
                        @SerializedName("first_text") val firstText: String,
                        @SerializedName("first_price") val firstPrice: Float,

                        @SerializedName("second_id") val secondId: Int,
                        @SerializedName("second_pic") val secondPic: String,
                        @SerializedName("second_text")val secondText: String,
                        @SerializedName("second_price")val secondPrice: Float,

                        @SerializedName("third_id") val thirdId: Int,
                        @SerializedName("third_text")val thirdText: String,
                        @SerializedName("third_pic") val thirdPic: String,
                        @SerializedName("third_price")val thirdPrice: Float,

                        @SerializedName("four_id") val fourId: Int,
                        @SerializedName("four_text")val fourText: String,
                        @SerializedName("four_pic") val fourPic: String,
                        @SerializedName("four_price")val fourPrice: Float)

    /**
     *  login
     */
    data class LoginResponse (val count: Int, val code: Int, val data: LoginResponseData)

    data class LoginResponseData (val token: String, val id: Int, val name: String)

    /**
     *  logout
     */
    data class LogoutResponse (val count: Int, val code: Int)

    /**
     *  query cart
     */
    data class QueryCartResponse (val count: Int, val code: Int, val data: QueryCart)

    data class QueryCart (@SerializedName("id") val id: Int,
                          @SerializedName("total_price") val totalPrice: Float,
                          @SerializedName("company_id") val companyId: Int,
                          @SerializedName("product_list") val productList: String)

    // 伺服器回應是字串類型，這部分將在 model層裡面將 productList 轉型為 CartProduct class
    data class CartProduct (@SerializedName("id") val id: Int,
                            @SerializedName("name") val name: String,
                            @SerializedName("number") val number: Int,
                            @SerializedName("image_url") val imageUrl: String,
                            @SerializedName("price") val price: Float)

    /**
     *  search
     */
    data class SearchResponse (val count: Int, val code: Int, val data: Array<SearchProduct>)

    data class SearchProduct (val id: Int,
                              val name: String,
                              val price: Float,
                              @SerializedName("image_url") val imageUrl: String)

    /**
     *  query product
     */
    data class QueryProductResponse (val count: Int, val code: Int, val data: QueryProduct)

    data class QueryProduct (@SerializedName("id") val id: Int,
                             @SerializedName("name") val name: String,
                             @SerializedName("image_url") val imageUrl: String,
                             @SerializedName("summary") val summary: String,
                             @SerializedName("information") val information: String,
                             @SerializedName("price") val price: Float,
                             @SerializedName("inventory_number") val inventoryNumber: Int,
                             @SerializedName("max_buy") val maxBuy: Int,

                             @SerializedName("company_id") val companyId: Int,
                             @SerializedName("company_name") val companyName:String,
                             @SerializedName("company_address") val companyAddress: String)

    /**
     *  add product to cart
     */
    data class AddProductToCartResponse (val count: Int, val code: Int, val data: AddProductToCart)

    data class AddProductToCart (@SerializedName("id") val id: Int,
                                 @SerializedName("product_list") val productList: String,
                                 @SerializedName("total_price") val totalPrice: String)

    /**
     *  check cart
     */
    data class CheckCartResponse (val count: Int, val code: Int)

    /**
     *  query order list
     */
    data class QueryOrderListResponse (val count: Int, val code: Int, val data: Array<QueryOrderList>)

    data class QueryOrderList (@SerializedName("id") val id: Int,
                               @SerializedName("order_number") val orderNumber: String,
                               @SerializedName("product_list") val productList: String,
                               @SerializedName("total_price") val totalPrice: Float)

    /**
     *  query order
     */
    data class QueryOrderResponse (val count: Int, val code: Int, val data: QueryOrder)

    data class QueryOrder (@SerializedName("id") val id: Int,
                           @SerializedName("order_number") val orderNumber: String,
                           @SerializedName("order_address") val orderAddress: String,
                           @SerializedName("product_list") val productList: String,
                           @SerializedName("total_price") val totalPrice: Float,
                           @SerializedName("order_status") val orderStatus: Int,
                           @SerializedName("paid_time") val paidTime: String,
                           @SerializedName("company_id") val companyId: Int)

    /**
     *  query company product
     */
    data class QueryCompanyProductResponse (val count: Int, val code: Int, val data: QueryCompanyProductResponseData)

    data class QueryCompanyProductResponseData (@SerializedName("company_id") val id: Int,
                                                @SerializedName("company_name") val companyName: String,
                                                @SerializedName("company_address") val companyAddress: String,
                                                @SerializedName("products") val products: Array<QueryCompanyProduct>)

    data class QueryCompanyProduct (@SerializedName("id") val id: Int,
                                    @SerializedName("name") val name: String,
                                    @SerializedName("image_url") val imageUrl: String,
                                    @SerializedName("price") val price: Float)
}
