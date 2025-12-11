package com.kuuuzaa

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuuuzaa.adapter.TrainAdapter
import com.kuuuzaa.mobile.R
import com.kuuuzaa.mobile.databinding.ActivityProfileBinding
import com.kuuuzaa.mobile.databinding.ActivityTrainsBinding
import com.kuuuzaa.retrofit.MainApi
import com.kuuuzaa.retrofit.UrlAdress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TrainsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var adapter: TrainAdapter
    lateinit var binding: ActivityTrainsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_trains)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.act_trains)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val profileUser = intent.getStringExtra("USER_ID") ?: ""
        val profileAccessToken = intent.getStringExtra("USER_ACCESSTOKEN") ?: ""
        val profileRefreshToken = intent.getStringExtra("USER_REFRESHTOKEN") ?: ""
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



        binding = ActivityTrainsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TrainAdapter()
        binding.rcViewTrains.layoutManager = LinearLayoutManager(this)
        binding.rcViewTrains.adapter = adapter





        val buttonTrains: ImageButton = findViewById(R.id.button_trains)
        val buttonCreateTrain: ImageButton = findViewById(R.id.button_add_train)
        val buttonProfile: ImageButton = findViewById(R.id.button_profile)

        buttonCreateTrain.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                mainApi.createTestTrains("Bearer " + profileAccessToken, profileUser)
            }
            val intentProfile = Intent(this@TrainsActivity, TrainsActivity::class.java).apply {
                putExtra("USER_ID", profileUser)
                putExtra("USER_ACCESSTOKEN", profileAccessToken)
                putExtra("USER_REFRESHTOKEN",profileRefreshToken)
            }

            startActivity(intentProfile)
        }

        buttonTrains.setOnClickListener {
            val intentTrains = Intent(this@TrainsActivity, TrainsActivity::class.java).apply {
                putExtra("USER_ID", profileUser)
                putExtra("USER_ACCESSTOKEN", profileAccessToken)
                putExtra("USER_REFRESHTOKEN",profileRefreshToken)
            }

            startActivity(intentTrains)
        }

        buttonProfile.setOnClickListener {
            val intentProfile = Intent(this@TrainsActivity, ProfileActivity::class.java).apply {
                putExtra("USER_ID", profileUser)
                putExtra("USER_ACCESSTOKEN", profileAccessToken)
                putExtra("USER_REFRESHTOKEN",profileRefreshToken)
            }

            startActivity(intentProfile)
        }

        binding.svTrains.setOnQueryTextListener(object: OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                CoroutineScope(Dispatchers.IO).launch {
                    val list =
                        newText?.let { mainApi.getTrainById("Bearer " + profileAccessToken, it) }
                    runOnUiThread {
                        binding.apply {
                            adapter.submitList(list)
                        }
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })

    }
}