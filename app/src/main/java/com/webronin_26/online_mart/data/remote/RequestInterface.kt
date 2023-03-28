package com.webronin_26.online_mart.data.remote

import okhttp3.ResponseBody
import retrofit2.http.*

interface RequestInterface {

    @Headers(
        "Cache-Control: max-age=0",
        "Upgrade-Insecure-Requests: 1",
        "user-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)",
        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Encoding: gzip, deflate, br",
        "Accept-Language: zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6,zh-CN;q=0.5,lb;q=0.4")

    @GET
    suspend fun downloadPicture(@Url path: String): ResponseBody

    @GET("/index")
    suspend fun mainRequest(): Response.MainResponse

    @POST("/login/member")
    suspend fun loginRequest(@Body loginRequestBody: LoginRequestBody): Response.LoginResponse

    data class LoginRequestBody (var account: String, val password: String)

    @DELETE("/member/logout")
    suspend fun logoutRequest(@Header("Authorization") token: String): Response.LogoutResponse

    @GET("/search")
    suspend fun searchRequest(@Query("q") q: String): Response.SearchResponse

    @GET("/product/{product_id}")
    suspend fun queryProductRequest(@Path("product_id") id: Int): Response.QueryProductResponse

    @GET("/company/product/{company_id}")
    suspend fun queryCompanyProductRequest(@Path("company_id") companyId: Int): Response.QueryCompanyProductResponse

    @GET("/member/cart")
    suspend fun queryCartRequest(@Header("Authorization") token: String): Response.QueryCartResponse

    @POST("/member/cart")
    suspend fun addProductToCartRequest(@Header("Authorization") token: String,
                                        @Body addProductToCartRequestBody: AddProductToCartRequestBody): Response.AddProductToCartResponse

    data class AddProductToCartRequestBody (var product_id: Int, val number: Int)

    @POST("/member/cart/check")
    suspend fun checkCartRequest(@Header("Authorization") token: String,
                                 @Body checkCartRequestBody: CheckCartRequestBody): Response.CheckCartResponse

    data class CheckCartRequestBody (var address: String)

    @GET("/member/order")
    suspend fun queryOrderListRequest(@Header("Authorization") token: String): Response.QueryOrderListResponse

    @GET("/member/order/{order_id}")
    suspend fun queryOrderRequest(@Header("Authorization") token: String,
                                  @Path("order_id") orderId: Int): Response.QueryOrderResponse
}