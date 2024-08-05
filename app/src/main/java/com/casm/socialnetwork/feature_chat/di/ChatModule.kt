package com.casm.socialnetwork.feature_chat.di

import android.app.Application
import com.casm.socialnetwork.feature_chat.data.remote.ws.util.CustomGsonMessageAdapter
import com.casm.socialnetwork.feature_chat.data.remote.ws.util.FlowStreamAdapter
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn
object ChatModule {

    @Provides
    @Singleton
    fun provideScarlet(app: Application, gson: Gson, client: OkHttpClient): Scarlet {
        return Scarlet.Builder()
            .addMessageAdapterFactory(CustomGsonMessageAdapter.Factory(gson))
            .addStreamAdapterFactory(FlowStreamAdapter.Factory)
            .webSocketFactory(client.newWebSocketFactory("ws://10.0.2.2:8001/api/chat/websocket"))
            .lifecycle(AndroidLifecycle.ofApplicationForeground(app))
            .build()
    }
}