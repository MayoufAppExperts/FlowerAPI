package com.example.theappexperts.flowerapi;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.theappexperts.flowerapi.services.ConnectionService;
import com.example.theappexperts.flowerapi.services.reqInterface;
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity;
import com.github.pwittchen.reactivenetwork.library.rx2.ConnectivityPredicate;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private reqInterface varReqInter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mySwipeRefreshLayout;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseRecyclerView();
        connectionStuff();
        reactiveNetwork();


        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Refresh", "onRefresh called from SwipeRefreshLayout");
                myUpdateOperation();
            }
        }
        );

    }

    public void reactiveNetwork(){
        ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Connectivity>() {
                    @Override public void accept(final Connectivity connectivity) {
                        toast.makeText(getApplicationContext(), connectivity.getType(), Toast.LENGTH_LONG).show();
                        Log.d("Connection", connectivity.toString());

                    }
                });

    }

    public void connectionStuff(){
        varReqInter = ConnectionService.getConnectionService();
        varReqInter.getFlowerList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(this::onSuccess, this::onError);
    }

    public void myUpdateOperation() {
        connectionStuff();
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        mySwipeRefreshLayout.setRefreshing(false);
    }

    private void onError(Throwable throwable) {
        Log.i("CakeList", throwable.getMessage());
    }

    private void onSuccess(List<FlowerListModel> flowerListModels) {
        Log.i("CakeList", flowerListModels.get(0).getName());
        recyclerView.setAdapter(new FlowerAdapter(flowerListModels, R.layout.row, getApplicationContext()));

    }

    public void initialiseRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerFlower);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}

