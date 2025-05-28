package com.example.receipegenerator.di

import android.app.Application
import androidx.room.Room
import com.example.receipegenerator.data.local.dao.RecipeDao
import com.example.receipegenerator.data.local.db.RecipeDatabase
import com.example.receipegenerator.data.repository.FavRecipeRepository
import com.example.receipegenerator.data.repository.RecipeRepository
import com.example.receipegenerator.network.GeminiApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //TODO give up on this because spent too much on find a good and free API tools with endpoint
    /*val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)*/

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideGeminiApi(gson: Gson): GeminiApi = GeminiApi(gson)

    @Provides
    @Singleton
    fun provideRecipeRepository(api: GeminiApi): RecipeRepository = RecipeRepository(api)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "recipe_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(db: RecipeDatabase): RecipeDao = db.recipeDao()

    @Provides
    @Singleton
    fun provideFavRecipeRepository(dao: RecipeDao): FavRecipeRepository =
        FavRecipeRepository(dao)
}