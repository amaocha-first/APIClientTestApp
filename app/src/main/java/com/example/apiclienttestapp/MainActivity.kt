package com.example.apiclienttestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.apiclienttestapp.api.ApiClientManager
import com.example.apiclienttestapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Call
import retrofit2.Callback
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import com.example.apiclienttestapp.api.response.ZipResponse

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val compositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buttonSubmit.setOnClickListener { _ ->
            val zipcode = binding.editText.text.toString()
            if (!TextUtils.isEmpty(zipcode)) {
//                compositeSubscription.clear()
//                compositeSubscription.add(
//                    ApiClientManager.apiClient.getZipCode(zipcode)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(rx.schedulers.Schedulers.newThread())
//                        .doOnNext {
//                            Log.d("タグ", "rx response=$it")
//                            //binding.textViewResponse.text = Gson().toJson(it)
//                        }
//                        .doOnError {
//                            Log.d("エラー", "rx response=$it")
//                        }
//                        .doOnCompleted {
//                        }
//                        .subscribe())


                // 【おまけ】 getZipCodeCall()の場合
                ApiClientManager.apiClient.getZipCodeCall(zipcode).enqueue(object:
                    Callback<ZipResponse> {
                    override fun onResponse(call: Call<ZipResponse>, response: retrofit2.Response<ZipResponse>) {
                        if (response.isSuccessful) {
                            Log.d(TAG, "call response=${response.body()}")
                            val textView = findViewById(R.id.text_view_response) as TextView
                            textView.text = Gson().toJson(response.body())
                        }
                    }
                    override fun onFailure(call: Call<ZipResponse>, t: Throwable) {

                    }
                })
            }
        }
    }

    override fun onDestroy() {
        compositeSubscription.clear()
        super.onDestroy()
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName!!
    }
}
