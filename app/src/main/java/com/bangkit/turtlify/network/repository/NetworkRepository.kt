package com.bangkit.turtlify.network.repository

import android.util.Log
import com.bangkit.turtlify.data.network.api.ApiConfig
import com.bangkit.turtlify.data.network.api.ApiService
import com.bangkit.turtlify.ui.report.FormData
import com.bangkit.turtlify.ui.viemodels.Contact
import com.bangkit.turtlify.ui.viemodels.Response
import com.bangkit.turtlify.ui.viemodels.Turtle

class NetworkRepository{
    private val apiService: ApiService = ApiConfig().getApiService()

    fun getContacts() : List<Contact>{
        val contacts: List<Contact> = try {
            listOf(
                Contact("John Doe", "john@example.com"),
                Contact("Alice Smith", "alice@example.com")
            )
        } catch (e: Exception) {
            Log.d("ERRORNETWORKREPO", e.message.toString())
            listOf() // Return an empty list if an exception occurs
        }

        return contacts
    }

    fun getTurtles(): List<Turtle>{
        val turtles: List<Turtle> = try {
            listOf(
                Turtle("Moncong babi", "https://www.balisafarimarinepark.com/wp-content/uploads/2022/02/fosil-penyu-hidung-babi-yang-langka-ditemukan-di-australia-nmu-600x400.jpg?p=27766"),
                Turtle("Galapagos", "https://asset-a.grid.id/crop/0x0:0x0/780x800/photo/bobofoto/original/11638_kura-kura-raksa-di-pulau-aldabra.jpg")
            )
        }catch (e: Exception){
            Log.d("ERRORNETWORKREPO", e.message.toString())
            listOf()
        }

        return turtles
    }

    fun submitReportForm(formData: FormData): Response{
        Log.d("FORMDATA", formData.toString())
        return Response(true, "Data fetched successfully")
    }

}