package com.kuuuzaa

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuuuzaa.adapter.TrainAdapter
import com.kuuuzaa.mobile.R
import com.kuuuzaa.mobile.databinding.ActivityMainBinding
import com.kuuuzaa.mobile.databinding.ActivityProfileBinding
import com.kuuuzaa.retrofit.MainApi
import com.kuuuzaa.retrofit.UrlAdress
import com.kuuuzaa.retrofit.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var adapter: TrainAdapter
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.act_profile)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TrainAdapter()
        binding.rcViewProfile.layoutManager = LinearLayoutManager(this)
        binding.rcViewProfile.adapter = adapter


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val urladress = UrlAdress()

        val retrofit = Retrofit.Builder()
            .baseUrl(urladress.getAdress()).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(MainApi::class.java)

        val profileUserId: TextView = findViewById(R.id.user_id)

        val profileUser = intent.getStringExtra("USER_ID") ?: ""
        val profileAccessToken = "Bearer " + intent.getStringExtra("USER_ACCESSTOKEN") ?: ""
        val profileRefreshToken = intent.getStringExtra("USER_REFRESHTOKEN") ?: ""

        profileUserId.text = profileUser.toString()


        println("__________________________________________________________________________________________________")
        println("__________________________________________________________________________________________________")
        println("__________________________________________________________________________________________________")
        println("UserId:" + profileUser)
        println(profileAccessToken)

        CoroutineScope(Dispatchers.IO).launch{
            val list = mainApi.getAllTrainsByIdAuth(profileAccessToken, profileUser)
            runOnUiThread{
                binding.apply{
                    adapter.submitList(list)
                }
            }
        }
        }

    }
