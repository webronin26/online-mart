package com.webronin_26.online_mart.main

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import com.webronin_26.online_mart.data.remote.Response
import kotlinx.coroutines.launch
import com.webronin_26.online_mart.data.remote.Result

class MainViewModel : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    /**
     * hotSales area
     */
    val hotSalesId01 = MutableLiveData<Int>()
    val hotSalesImage01 = MutableLiveData<Bitmap>()
    val hotSalesText01 = MutableLiveData<String>()
    val hotSalesPrice01 = MutableLiveData<Float>()

    val hotSalesId02 = MutableLiveData<Int>()
    val hotSalesImage02 = MutableLiveData<Bitmap>()
    val hotSalesText02 = MutableLiveData<String>()
    val hotSalesPrice02 = MutableLiveData<Float>()

    val hotSalesId03 = MutableLiveData<Int>()
    val hotSalesImage03 = MutableLiveData<Bitmap>()
    val hotSalesText03 = MutableLiveData<String>()
    val hotSalesPrice03 = MutableLiveData<Float>()

    val hotSalesId04= MutableLiveData<Int>()
    val hotSalesImage04 = MutableLiveData<Bitmap>()
    val hotSalesText04 = MutableLiveData<String>()
    val hotSalesPrice04 = MutableLiveData<Float>()

    /**
     * latest area
     */
    val latestId01 = MutableLiveData<Int>()
    val latestImage01 = MutableLiveData<Bitmap>()
    val latestText01 = MutableLiveData<String>()
    val latestPrice01 = MutableLiveData<Float>()

    val latestId02 = MutableLiveData<Int>()
    val latestImage02 = MutableLiveData<Bitmap>()
    val latestText02 = MutableLiveData<String>()
    val latestPrice02 = MutableLiveData<Float>()

    val latestId03 = MutableLiveData<Int>()
    val latestImage03 = MutableLiveData<Bitmap>()
    val latestText03 = MutableLiveData<String>()
    val latestPrice03 = MutableLiveData<Float>()

    val latestId04= MutableLiveData<Int>()
    val latestImage04 = MutableLiveData<Bitmap>()
    val latestText04 = MutableLiveData<String>()
    val latestPrice04 = MutableLiveData<Float>()

    /**
     * dailyRecommended area
     */
    val dailyRecommendedId01 = MutableLiveData<Int>()
    val dailyRecommendedImage01 = MutableLiveData<Bitmap>()
    val dailyRecommendedText01 = MutableLiveData<String>()
    val dailyRecommendedPrice01 = MutableLiveData<Float>()

    val dailyRecommendedId02 = MutableLiveData<Int>()
    val dailyRecommendedImage02 = MutableLiveData<Bitmap>()
    val dailyRecommendedText02 = MutableLiveData<String>()
    val dailyRecommendedPrice02 = MutableLiveData<Float>()

    val dailyRecommendedId03 = MutableLiveData<Int>()
    val dailyRecommendedImage03 = MutableLiveData<Bitmap>()
    val dailyRecommendedText03 = MutableLiveData<String>()
    val dailyRecommendedPrice03 = MutableLiveData<Float>()

    val dailyRecommendedId04= MutableLiveData<Int>()
    val dailyRecommendedImage04 = MutableLiveData<Bitmap>()
    val dailyRecommendedText04 = MutableLiveData<String>()
    val dailyRecommendedPrice04 = MutableLiveData<Float>()

    /**
     * mayLike area
     */
    val mayLikeId01 = MutableLiveData<Int>()
    val mayLikeImage01 = MutableLiveData<Bitmap>()
    val mayLikeText01 = MutableLiveData<String>()
    val mayLikePrice01 = MutableLiveData<Float>()

    val mayLikeId02 = MutableLiveData<Int>()
    val mayLikeImage02 = MutableLiveData<Bitmap>()
    val mayLikeText02 = MutableLiveData<String>()
    val mayLikePrice02 = MutableLiveData<Float>()

    val mayLikeId03 = MutableLiveData<Int>()
    val mayLikeImage03 = MutableLiveData<Bitmap>()
    val mayLikeText03 = MutableLiveData<String>()
    val mayLikePrice03 = MutableLiveData<Float>()

    val mayLikeId04= MutableLiveData<Int>()
    val mayLikeImage04 = MutableLiveData<Bitmap>()
    val mayLikeText04 = MutableLiveData<String>()
    val mayLikePrice04 = MutableLiveData<Float>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshMainPage() {

        viewModelScope.launch {
            repository?.refreshMainPage().let { result ->
                when (result) {
                    is Result.Success -> {
                        setData(result.data.data)
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    }
                    is Result.ConnectException -> {
                        setNullData()
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    }
                    else -> {
                        setNullData()
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                    }
                }
            }
        }
    }

    private fun setData(data: Response.MainResponseData) {

        hotSalesId01.value = data.hotSales.firstId
        setImage(data.hotSales.firstPic, hotSalesImage01)
        hotSalesText01.value = data.hotSales.firstText
        hotSalesPrice01.value = data.hotSales.firstPrice

        hotSalesId02.value = data.hotSales.secondId
        setImage(data.hotSales.secondPic, hotSalesImage02)
        hotSalesText02.value = data.hotSales.secondText
        hotSalesPrice02.value = data.hotSales.secondPrice

        hotSalesId03.value = data.hotSales.thirdId
        setImage(data.hotSales.thirdPic, hotSalesImage03)
        hotSalesText03.value = data.hotSales.thirdText
        hotSalesPrice03.value = data.hotSales.thirdPrice

        hotSalesId04.value = data.hotSales.fourId
        setImage(data.hotSales.fourPic, hotSalesImage04)
        hotSalesText04.value = data.hotSales.fourText
        hotSalesPrice04.value = data.hotSales.fourPrice

        latestId01.value = data.latest.firstId
        setImage(data.latest.firstPic, latestImage01)
        latestText01.value = data.latest.firstText
        latestPrice01.value = data.latest.firstPrice

        latestId02.value = data.latest.secondId
        setImage(data.latest.secondPic, latestImage02)
        latestText02.value = data.latest.secondText
        latestPrice02.value = data.latest.secondPrice

        latestId03.value = data.latest.thirdId
        setImage(data.latest.thirdPic, latestImage03)
        latestText03.value = data.latest.thirdText
        latestPrice03.value = data.latest.thirdPrice

        latestId04.value = data.latest.fourId
        setImage(data.latest.fourPic, latestImage04)
        latestText04.value = data.latest.fourText
        latestPrice04.value = data.latest.fourPrice

        dailyRecommendedId01.value = data.dailyRecommended.firstId
        setImage(data.dailyRecommended.firstPic, dailyRecommendedImage01)
        dailyRecommendedText01.value = data.dailyRecommended.firstText
        dailyRecommendedPrice01.value = data.dailyRecommended.firstPrice

        dailyRecommendedId02.value = data.dailyRecommended.secondId
        setImage(data.dailyRecommended.secondPic, dailyRecommendedImage02)
        dailyRecommendedText02.value = data.dailyRecommended.secondText
        dailyRecommendedPrice02.value = data.dailyRecommended.secondPrice

        dailyRecommendedId03.value = data.dailyRecommended.thirdId
        setImage(data.dailyRecommended.thirdPic, dailyRecommendedImage03)
        dailyRecommendedText03.value = data.dailyRecommended.thirdText
        dailyRecommendedPrice03.value = data.dailyRecommended.thirdPrice

        dailyRecommendedId04.value = data.dailyRecommended.fourId
        setImage(data.dailyRecommended.fourPic, dailyRecommendedImage04)
        dailyRecommendedText04.value = data.dailyRecommended.fourText
        dailyRecommendedPrice04.value = data.dailyRecommended.fourPrice

        mayLikeId01.value = data.mayLike.firstId
        setImage(data.mayLike.firstPic, mayLikeImage01)
        mayLikeText01.value = data.mayLike.firstText
        mayLikePrice01.value = data.mayLike.firstPrice

        mayLikeId02.value = data.mayLike.secondId
        setImage(data.mayLike.secondPic, mayLikeImage02)
        mayLikeText02.value = data.mayLike.secondText
        mayLikePrice02.value = data.mayLike.secondPrice

        mayLikeId03.value = data.mayLike.thirdId
        setImage(data.mayLike.thirdPic, mayLikeImage03)
        mayLikeText03.value = data.mayLike.thirdText
        mayLikePrice03.value = data.mayLike.thirdPrice

        mayLikeId04.value = data.mayLike.fourId
        setImage(data.mayLike.fourPic, mayLikeImage04)
        mayLikeText04.value = data.mayLike.fourText
        mayLikePrice04.value = data.mayLike.fourPrice
    }

    private fun setImage(urlString: String?, liveData: MutableLiveData<Bitmap>) {
        if (urlString != null) {
            viewModelScope.launch {
                repository?.getImage(urlString).let { bitmap ->
                    liveData.value = bitmap
                }
            }
        }
    }

    private fun setNullData() {

        hotSalesId01.value = 0
        hotSalesText01.value = "韓國熱銷 百搭設計質感項鍊"
        hotSalesPrice01.value = 599.0f

        hotSalesId02.value = 0
        hotSalesText02.value = "韓國熱銷 百搭設計質感項鍊"
        hotSalesPrice02.value = 599.0f

        hotSalesId03.value = 0
        hotSalesText03.value = "韓國熱銷 百搭設計質感項鍊"
        hotSalesPrice03.value = 599.0f

        hotSalesId04.value = 0
        hotSalesText04.value = "韓國熱銷 百搭設計質感項鍊"
        hotSalesPrice04.value = 599.0f

        latestId01.value = 0
        latestText01.value = "韓國熱銷 百搭設計質感項鍊"
        latestPrice01.value = 599.0f

        latestId02.value = 0
        latestText02.value = "韓國熱銷 百搭設計質感項鍊"
        latestPrice02.value = 599.0f

        latestId03.value = 0
        latestText03.value = "韓國熱銷 百搭設計質感項鍊"
        latestPrice03.value = 599.0f

        latestId04.value = 0
        latestText04.value = "韓國熱銷 百搭設計質感項鍊"
        latestPrice04.value = 599.0f

        dailyRecommendedId01.value = 0
        dailyRecommendedText01.value = "韓國熱銷 百搭設計質感項鍊"
        dailyRecommendedPrice01.value = 599.0f

        dailyRecommendedId02.value = 0
        dailyRecommendedText02.value = "韓國熱銷 百搭設計質感項鍊"
        dailyRecommendedPrice02.value = 599.0f

        dailyRecommendedId03.value = 0
        dailyRecommendedText03.value = "韓國熱銷 百搭設計質感項鍊"
        dailyRecommendedPrice03.value = 599.0f

        dailyRecommendedId04.value = 0
        dailyRecommendedText04.value = "韓國熱銷 百搭設計質感項鍊"
        dailyRecommendedPrice04.value = 599.0f

        mayLikeId01.value = 0
        mayLikeText01.value = "韓國熱銷 百搭設計質感項鍊"
        mayLikePrice01.value = 599.0f

        mayLikeId02.value = 0
        mayLikeText02.value = "韓國熱銷 百搭設計質感項鍊"
        mayLikePrice02.value = 599.0f

        mayLikeId03.value = 0
        mayLikeText03.value = "韓國熱銷 百搭設計質感項鍊"
        mayLikePrice03.value = 599.0f

        mayLikeId04.value = 0
        mayLikeText04.value = "韓國熱銷 百搭設計質感項鍊"
        mayLikePrice04.value = 599.0f
    }
}