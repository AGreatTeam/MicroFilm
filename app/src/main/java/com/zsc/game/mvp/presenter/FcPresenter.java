package com.zsc.game.mvp.presenter;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.FcModel;
import com.zsc.game.mvp.model.bean.VideoCatagory;
import com.zsc.game.mvp.view.FcView;
import com.zsc.game.util.SystemUtils;

import java.util.Random;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/6  19:37
 */

public class FcPresenter extends BasePresenter<FcModel,FcView> {
    final String catalogId = "402834815584e463015584e539330016";

    int max = 108;
    int min = 1;

    @Inject
    public FcPresenter() {
    }

    public void loadata()
     {
         model.loadData(catalogId, getNextPage()+"")
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeWith(new DisposableSubscriber<VideoCatagory>() {
                     @Override
                     public void onNext(VideoCatagory responseBody) {
                         try {
                             getView().onSuccess(responseBody);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                         getView().showToast("成功");
                     }

                     @Override
                     public void onError(Throwable t) {
                         getView().onError("刷新失败");
                     }

                     @Override
                     public void onComplete() {

                     }
                 });

     }

    private int getNextPage() {
        int page = 1;
        if (SystemUtils.isNetworkConnected()) {
            page = new Random().nextInt(max) % (max - min + 1) + min;
        }
        return page;
    }


}
