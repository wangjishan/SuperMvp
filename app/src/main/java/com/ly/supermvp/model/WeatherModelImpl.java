package com.ly.supermvp.model;

import com.ly.supermvp.model.entity.ShowApiWeather;
import com.ly.supermvp.server.RetrofitService;
import com.ly.supermvp.model.entity.ShowApiResponse;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <Pre>
 * 天气预报实现类
 * </Pre>
 *
 * @author 刘阳
 * @version 1.0
 *          <p/>
 *          Create by 2016/3/1 14:48
 */
public class WeatherModelImpl implements WeatherModel {
    @Override
    public void netLoadWeatherWithLocation(String area, String needMoreDay, String needIndex,
                                           String needAlarm, String need3HourForcast,
                                           final OnLoadWeatherListener listener) {
        //使用RxJava响应Retrofit
        Observable<ShowApiResponse<ShowApiWeather>> observable = RetrofitService.getInstance().
                createNewsApi().getWeather(area, needMoreDay, needIndex, needAlarm, need3HourForcast);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ShowApiResponse<ShowApiWeather>>() {
                    @Override
                    public void onCompleted() {
                        //请求完成
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onNext(ShowApiResponse<ShowApiWeather> showApiWeatherShowApiResponse) {
                        listener.onSuccess(showApiWeatherShowApiResponse.showapi_res_body);
                    }
                });
    }
}