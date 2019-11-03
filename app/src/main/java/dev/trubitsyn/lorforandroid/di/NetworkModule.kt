package dev.trubitsyn.lorforandroid.di

import com.google.gson.Gson
import dagger.Module
import dev.trubitsyn.lorforandroid.Const
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Const.SITE_ROOT + "api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}