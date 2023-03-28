package com.webronin_26.online_mart.data.source

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import com.webronin_26.online_mart.data.remote.*
import com.webronin_26.online_mart.data.util.HashKeyMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://10.0.2.2:1323"
private const val PICTURE_BASE_URL = "https://i.imgur.com/"
private const val SING_IN_KEY = "" // put a private key to connect to server

class OnlineMartRepository {

    // application picture cache manager
    private val cacheSize : Int = ((Runtime.getRuntime().maxMemory() / 1024) / 4).toInt()
    private val lruCache: LruCache<String, Bitmap> = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String?, value: Bitmap?): Int {
            if (value == null) { return 0 }
            return (value.byteCount).div(1024)
        }
    }

    suspend fun getImage(url: String): Bitmap? = withContext(Dispatchers.IO) {

        val hashUrl = HashKeyMD5().hashKeyFromString(url)
        var bitmap: Bitmap?

        bitmap = lruCache.get(hashUrl)
        if (bitmap != null) { return@withContext bitmap }

        bitmap = downloadImage(url)
        if (bitmap == null) { return@withContext bitmap }
        lruCache.put(hashUrl, bitmap)

        return@withContext bitmap
    }

    private fun getRetrofitInstance(): RequestInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RequestInterface::class.java)
    }

    private suspend fun downloadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val responseBody = Retrofit.Builder()
                .baseUrl(PICTURE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RequestInterface::class.java)
                .downloadPicture(url)

            val bytes = responseBody.bytes()

            return@withContext BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            return@withContext null
        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 傳送端的參數格式錯誤 STATUS_PARAM_ERROR
     */
    suspend fun login(account: String, password: String): Result<Response.LoginResponse> = withContext(Dispatchers.IO) {
        try {
            val loginRequestBody = RequestInterface.LoginRequestBody(account = account, password = password)
            val response = getRetrofitInstance().loginRequest(loginRequestBody)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 傳送端的參數格式錯誤 STATUS_PARAM_ERROR
     */
    suspend fun search(searchString: String): Result<Response.SearchResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().searchRequest(searchString)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception(response.data.toString()))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 找不到商品 STATUS_RECORD_NOT_FOUND
     *   5. 參數錯誤 STATUS_PARAM_ERROR
     */
    suspend fun queryProduct(id: Int): Result<Response.QueryProductResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().queryProductRequest(id)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception(response.data.toString()))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 找不到商品 STATUS_RECORD_NOT_FOUND
     *   5. 參數錯誤 STATUS_PARAM_ERROR
     *   6. 庫存不足 STATUS_CREATE_POST_FAILED_INVENTORY_NUMBER
     *   7. 商品廠商不同 STATUS_CREATE_POST_FAILED_PRODUCT_COMPANY
     */
    suspend fun addProductToCart(token: String, productId: Int, number: Int): Result<Response.AddProductToCartResponse> = withContext(Dispatchers.IO) {
        try {
            val addProductToCartRequestBody = RequestInterface.AddProductToCartRequestBody(productId, number)
            val response = getRetrofitInstance().addProductToCartRequest(token, addProductToCartRequestBody)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else if (response.code == STATUS_CREATE_POST_FAILED_INVENTORY_NUMBER) {
                return@withContext Result.InventoryNumberError(Exception(response.data.toString()))
            } else if (response.code == STATUS_CREATE_POST_FAILED_PRODUCT_COMPANY) {
                return@withContext Result.ProductCompanyError(Exception(response.data.toString()))
            } else {
                return@withContext Result.Error(Exception(response.data.toString()))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 找不到商品 STATUS_RECORD_NOT_FOUND
     */
    suspend fun queryOrderList(token: String): Result<Response.QueryOrderListResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().queryOrderListRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else if (response.code == STATUS_RECORD_NOT_FOUND) {
                return@withContext Result.RecordNotFound(response)
            } else {
                return@withContext Result.Error(Exception(response.data.toString()))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 找不到商品 STATUS_RECORD_NOT_FOUND
     */
    suspend fun queryOrder(token: String, productId: Int): Result<Response.QueryOrderResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().queryOrderRequest(token, productId)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception(response.data.toString()))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 傳送端的參數格式錯誤 STATUS_PARAM_ERROR
     */
    suspend fun checkCart(token: String, memberAddress: String): Result<Response.CheckCartResponse> = withContext(Dispatchers.IO) {
        try {
            val checkCartRequestBody = RequestInterface.CheckCartRequestBody(memberAddress)
            val response = getRetrofitInstance().checkCartRequest(token, checkCartRequestBody)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 傳送端的參數格式錯誤 STATUS_PARAM_ERROR
     */
    suspend fun queryCart(token: String): Result<Response.QueryCartResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().queryCartRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception(response.data.toString()))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 傳送端的參數格式錯誤 STATUS_PARAM_ERROR
     */
    suspend fun queryCompanyProduct(companyId: Int): Result<Response.QueryCompanyProductResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().queryCompanyProductRequest(companyId)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception(response.data.toString()))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     *   4. 傳送端的參數格式錯誤 STATUS_PARAM_ERROR
     */
    suspend fun refreshMainPage(): Result<Response.MainResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().mainRequest()

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }

    /**
     * 可能回傳：
     *   1. 成功
     *   2. 伺服器端錯誤 STATUS_SERVER_ERROR
     *   3. 伺服器端錯誤 Status_SQL_ERROR_SCAN_FAILED
     */
    suspend fun logout(token: String): Result<Response.LogoutResponse> = withContext(Dispatchers.IO) {
        try {
            val response = getRetrofitInstance().logoutRequest(token)

            if (response.code == STATUS_SUCCESS) {
                return@withContext Result.Success(response)
            } else {
                return@withContext Result.Error(Exception("request error"))
            }
        } catch (e: Exception) {
            return@withContext Result.ConnectException(Exception(e.toString()))
        }
    }
}