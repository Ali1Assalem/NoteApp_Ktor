package com.example.noteapp_ktor.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp_ktor.data.local.NoteDatabase
import com.example.noteapp_ktor.data.remote.NoteApi
import com.example.noteapp_ktor.utils.Constants
import com.example.noteapp_ktor.utils.SessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ) = SessionManager(context)



    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "note_db"
    ).build()


    @Singleton
    @Provides
    fun provideNoteDao(
        noteDb: NoteDatabase
    ) = noteDb.getNoteDao()


    @Singleton
    @Provides
    fun provideNoteApi(): NoteApi {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NoteApi::class.java)

    }

/**
    @Singleton
    @Provides
    fun provideNoteRepo(
        noteApi: NoteApi,
        noteDao: NoteDao,
        sessionManager: SessionManager
    ): NoteRepo {
        return NoteRepoImpl(
            noteApi,
            noteDao,
            sessionManager
        )
    }
*/


}