package com.example.appmydogcat.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appmydogcat.service.DogCatEntity
import com.example.appmydogcat.service.DogCatRetrofit
import com.example.appmydogcat.R
import com.example.appmydogcat.ui.theme.AppMyDogCatTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private val dogCatImageUrlList = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppMyDogCatTheme {
                MainScreen(dogCatImageUrlList)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            if (isNetworkAvailable(this@MainActivity)) {
                callAPI()
            } else {
                showToast()
            }
        }

    }

    private fun callAPI() {
        val service = DogCatRetrofit().getRetrofitInstance()
        val call: Call<List<DogCatEntity>> = service.keyList()
        call.enqueue(object : Callback<List<DogCatEntity>> {
            override fun onResponse(
                call: Call<List<DogCatEntity>>,
                response: Response<List<DogCatEntity>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                            entities ->
                        val urls = entities.map { it.url }
                        dogCatImageUrlList.addAll(urls)
                    }
                } else {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Erro")
                        .setMessage("Erro ao fazer a chamada da API: ${response.errorBody()!!.string()}")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }

            }

            override fun onFailure(call: Call<List<DogCatEntity>>, t: Throwable) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Erro")
                    .setMessage("Erro ao fazer a chamada da API: ${t.message}")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        })
    }

    private fun showToast() {
        runOnUiThread {
            Toast.makeText(this@MainActivity, R.string.ERROR_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(images: List<String>) {

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = "App My Dog/Cat",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    },
                    colors = topAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.White
                    )
                )
                Box(
                    modifier = Modifier.fillMaxWidth().height(12.dp).background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Gray),
                            startY = 20f,
                            endY = 0f)
                    )
                )
            }
        },
        content = {
            Column {
                Spacer(modifier = Modifier.height(56.dp))
                LazyColumn {
                    itemsIndexed(images) { _, item ->
                        DogCatItem(item)
                    }
                }
            }
        }
    )

}




