package com.example.theappexperts.flowerapi.services;

import com.example.theappexperts.flowerapi.API_Constants;
import com.example.theappexperts.flowerapi.FlowerListModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by TheAppExperts on 27/09/2017.
 */

public interface reqInterface {

    @GET(API_Constants.FLOWER_LIST_API)
    Observable<List<FlowerListModel>> getFlowerList();
}
