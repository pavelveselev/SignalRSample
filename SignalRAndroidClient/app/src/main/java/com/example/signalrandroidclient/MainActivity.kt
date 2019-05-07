package com.example.signalrandroidclient

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var hubConnection: HubConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            hubConnection = HubConnectionBuilder.create("https://signalrserver20190507124505.azurewebsites.net/test").build()

            hubConnection?.on("SendTest", { message ->
                Log.i("SIGNALR!", message)
            }, String::class.java)

            HubConnectionTask().execute(hubConnection)
        } catch (e: Exception) {

            Log.e("SIGNALR!", e.message)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        hubConnection?.stop()
    }
}

internal class HubConnectionTask : AsyncTask<HubConnection, Void, Void>() {

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg hubConnections: HubConnection): Void? {
        val hubConnection = hubConnections[0]
        hubConnection.start().blockingAwait()
        return null
    }
}