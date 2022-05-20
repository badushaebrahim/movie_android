package com.example.emojify.webservice;


import com.example.emojify.model.Datum;
import com.example.emojify.model.getMessage;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("movie.php")
    Call<getMessage> Register(@Field("key") String apiKey,
                              @Field("name") String username,
                              @Field("director") String dir,
                              @Field("star") String star,
                              @Field("phone") String age,
                              @Field("tid") String tis,
                              @Field("lang") String ll);
    @FormUrlEncoded
    @POST("movie.php")
    Call<getMessage> generateticket(@Field("key") String apiKey,
                              @Field("mname") String username,
                              @Field("tname") String dir,
                              @Field("time") String star,
                              @Field("bdate") String age,
                              @Field("numticket") String tis,
                              @Field("amount") String ll,
                                    @Field("uid") String uid,
                                    @Field("tid") String tid,
                                    @Field("mpic") String mjj,
                                    @Field("qr") String qr);
    @FormUrlEncoded
    @POST("movie.php")
    Call<getMessage> assign(@Field("key") String apiKey,
                              @Field("mval") String username,
                              @Field("tval") String dir);
    @FormUrlEncoded
    @POST("movie.php")
    Call<getMessage> addshow(@Field("key") String apiKey,
                              @Field("mid") String username,
                              @Field("time") String dir,
                              @Field("uid") String star);
    @FormUrlEncoded
    @POST("movie.php")
    Call<getMessage> Registe1r(@Field("key") String apiKey,
                              @Field("name") String username,
                              @Field("phone") String dir);

    @FormUrlEncoded
    @POST("movie.php")
    Call<getMessage> rating(@Field("key") String apiKey,
                            @Field("mid") String username,
                            @Field("uid") String age,
                            @Field("rev") String re,
                            @Field("rat") String ra);

    @FormUrlEncoded
    @POST("movie.php")
    Call<String> Addmusic(
            @Field("key") String apiKey,
            @Field("tname") String username,
            @Field("scap") String age,
            @Field("tadd") String apiKey1,
            @Field("tphone") String username1,
            @Field("uname") String ung,
            @Field("pass") String passs);


    @GET("movie.php")
    Call<getMessage> login(@Query("key") String apiKey, @Query("phone") String uname);

    @GET("movie.php")
    Call<getMessage> recomend(@Query("key") String apiKey, @Query("lan") String uname);

    @GET("movie.php")
    Call<getMessage> spinner(@Query("key") String key);
    @GET("movie.php")
    Call<getMessage> getmymovie(@Query("key") String key,@Query("uid") String key1);
    @GET("movie.php")
    Call<getMessage> getshow(@Query("key") String key,
                                @Query("tid") String keyhgf1,
                                @Query("eid") String kehgy1);

    @GET("movie.php")
    Call<getMessage> emotion(@Query("key") String key, @Query("eid") String eid, @Query("lan") String lan);

}
