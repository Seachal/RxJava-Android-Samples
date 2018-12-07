package com.morihacky.android.rxjava.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.morihacky.android.rxjava.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 注意：此页面的顺序是根据 列表中的log从下往上读
 */
public class ConcurrencyWithSchedulersDemoFragment extends BaseFragment {

    private String TAG = "Concurrency";

    @BindView(R.id.progress_operation_running)
    ProgressBar _progress;

    @BindView(R.id.list_threading_log)
    ListView _logsList;

    private LogAdapter _adapter;
    private List<String> _logs;
    private CompositeDisposable _disposables = new CompositeDisposable();
    private Unbinder unbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        _disposables.clear();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _setupLogger();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_concurrency_schedulers, container, false);
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    @OnClick(R.id.btn_start_operation)
    public void startLongOperation() {

        _progress.setVisibility(View.VISIBLE);
        _log("Button Clicked");

        DisposableObserver<Boolean> d = _getDisposableObserver();

        _getObservable()
//                将耗时操作切换到后台线程
                .subscribeOn(Schedulers.io())
//                操作完成后回到主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d);

        _disposables.add(d);
    }

    private Observable<Boolean> _getObservable() {
        return Observable.just(true)
                .map(
                        aBoolean -> {
                            _log("Within Observable");
                            _doSomeLongOperation_thatBlocksCurrentThread();
                            return aBoolean;
                        });
    }

    /**
     * Observer that handles the result through the 3 important actions:
     *
     * <p>1. onCompleted 2. onError 3. onNext
     */
    private DisposableObserver<Boolean> _getDisposableObserver() {
        return new DisposableObserver<Boolean>() {

            @Override
            public void onComplete() {
                _log("On complete");
                _progress.setVisibility(View.INVISIBLE);
                Log.i(TAG, "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Error in RxJava Demo concurrency");
                _log(String.format("Boo! Error %s", e.getMessage()));
                _progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNext(Boolean bool) {
                _log(String.format("onNext with return value \"%b\"", bool));
                Log.i(TAG, "onNext");
            }
        };
    }

    // -----------------------------------------------------------------------------------
    // Method that help wiring up the example (irrelevant to RxJava) 帮助连接示例的方法(与RxJava无关)
//     Blocks：阻塞 ，irrelevant 不相干的
    private void _doSomeLongOperation_thatBlocksCurrentThread() {
        _log("performing long operation");//执行耗时操作

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Timber.d("Operation was interrupted");
        }
    }

    private void _log(String logMsg) {

        if (_isCurrentlyOnMainThread()) {// 如果在主线程 add log
            _logs.add(0, logMsg + " (main thread) ");
            _adapter.clear();
//            更新 list
            _adapter.addAll(_logs);
        } else {   // 如果不在主线程 add log
            _logs.add(0, logMsg + " (NOT main thread) ");

            /**
             * You can only do below stuff on main thread.你只能在主线程上做下面的事情。
             * 下面的代码是在非主线程， 获取主线程，然后让主线程做一些事情。
             */
            new Handler(Looper.getMainLooper())
                    .post(
                            () -> {
                                _adapter.clear();
//                                更新 list
                                _adapter.addAll(_logs);
                            });
        }
    }

    private void _setupLogger() {
        _logs = new ArrayList<>();
        _adapter = new LogAdapter(getActivity(), new ArrayList<>());
        _logsList.setAdapter(_adapter);
    }

    /**
     * 判断在主线程
     *
     * @return
     */
    private boolean _isCurrentlyOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     *
     */
    private class LogAdapter extends ArrayAdapter<String> {

        public LogAdapter(Context context, List<String> logs) {
            super(context, R.layout.item_log, R.id.item_log, logs);
        }
    }
}
