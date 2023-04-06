# online-mart
本 Online Mart 是一個擁有 server端，會員用戶端以及管理人員端 app 的專案。
<br>提供了多項使用功能，包含購物車，結帳，管理人員產品上架，訂單管理以及登入登出等等的功能。
<br>同時也有完整的介面wireframe，資料庫關聯圖，和API文件等，供對照查詢。
<br><br>而目前的專案是給 會員用戶以及訪客使用的 app 前端部分。

![](main_page.png)
![](product_page.png)
![](cart_page.png)

## 相關連的專案為
- [golang server 端](https://github.com/webronin26/online-mart-server)
- [廠商人員使用的 android app](https://github.com/webronin26/online-mart-retailer)
- [管理人員使用的 android app](https://github.com/webronin26/online-mart-admin)

## 專案文件
- [介面 wireframe](https://www.figma.com/file/aw8HkloMpbDB66tuxrSU4x/Online-Mart?node-id=0%3A1)
- [資料庫關聯圖](https://dbdiagram.io/d/627cc0397f945876b6044ce9)
- [API 文件](https://app.swaggerhub.com/apis/webronin26/Online-Mart-API/1.0.0)

## 專案使用到的主要技術為
kotlin / MVVM / jetpack (livedata, coroutine, databinding, navigation...) / retrofit... 等等

## 本專案包含的功能

|一般用戶|
|-----------|
|首頁|
|會員登入 / 登出|
|查詢商品|
|查看特定商家商品列表|

|會員|
|-----------|
|加入購物車|
|查自己的購物車|
|結帳購物車|
|查自己的訂單列表|
|查自己詳細的訂單|

|商家|
|-----------|
|登入 / 登出 後台|
|查看商家自己的資訊|
|更新商家自己的資訊|
|看到商品列表|
|查看某個商品詳細資訊|
|更新商品資訊|
|上架商品|
|查看全部的訂單|
|查看某個特定的訂單明細|

|管理者|
|-----------|
|登入 / 登出 後台|
|查看目前商家列表|
|查看某個特定的商家資料|
