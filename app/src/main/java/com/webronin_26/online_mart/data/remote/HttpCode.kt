package com.webronin_26.online_mart.data.remote

const val STATUS_SUCCESS                                  = 2001    // 成功

const val STATUS_PARAM_ERROR                              = 4001  // 參數有誤
const val STATUS_PARAM_VALIDATE_FAILED                    = 4002  // 參數數值有誤
const val STATUS_BIND_FAILED                              = 4003  // 參數綁定失敗
const val STATUS_RECORD_NOT_FOUND                         = 4004  // 找不到紀錄
const val STATUS_CREATE_POST_FAILED                       = 4005  // Post 建立失敗
const val STATUS_CREATE_POST_FAILED_INVENTORY_NUMBER      = 40051 // Post 建立失敗，庫存數量問題
const val STATUS_CREATE_POST_FAILED_CREATE_RECORD         = 40052 // Post 建立失敗，建立新紀錄問題
const val STATUS_CREATE_POST_FAILED_PRODUCT_COMPANY       = 40053 // Post 建立失敗，商品廠商異常
const val STATUS_CREATE_POST_FAILED_CONVERT               = 40054 // Post 建立失敗，資料轉型失敗
const val STATUS_UPDATE_POST_FAILED                       = 4006  // Post 更新失敗
const val STATUS_UPDATE_POST_FAILED_INVENTORY_NOT_ENOUGH  = 40061 // Post 更新失敗，庫存不足

const val STATUS_TOKEN_ERROR                              = 4011    // Token 有誤
const val STATUS_ACCOUNT_ERROR                            = 4041    // 帳密認證有誤

const val STATUS_SERVER_ERROR                             = 5001    // 伺服器錯誤
const val STATUS_SQL_ERROR                                = 5002    // 資料庫錯誤
const val Status_SQL_ERROR_SCAN_FAILED                    = 50021   // 資料庫錯誤，轉型錯誤
