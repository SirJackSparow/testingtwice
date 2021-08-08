package com.startup.twiscodetest.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.startup.twiscodetest.data.server.GlobalInterceptor
import com.startup.twiscodetest.data.server.NetworkService
import com.startup.twiscodetest.data.localdb.AppDataBase
import com.startup.twiscodetest.repository.MainRepo
import com.startup.twiscodetest.repository.MainRepoImpl
import com.startup.twiscodetest.view.cart.CartViewModel
import com.startup.twiscodetest.view.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDataBase::class.java, "test_db").build()
    }
    single { createOkHttpClient(get()) }
    single { webService<NetworkService>() }
}

val vmModule = module {
    single { MainRepoImpl(get()) as MainRepo }
    single { get<AppDataBase>().keranjang() }
    viewModel { MainViewModel(get(),get()) }
    viewModel { CartViewModel(get()) }

}

fun createOkHttpClient(interceptor: GlobalInterceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val timeout = 10L
    return OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(interceptor)
        .build()
}

inline fun <reified T> webService(): T {
    val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://ranting.twisdev.com/index.php/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

val moduleApp = listOf(appModule, vmModule)