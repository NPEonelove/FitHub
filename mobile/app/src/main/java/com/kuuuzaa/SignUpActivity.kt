package com.kuuuzaa.mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kuuuzaa.ProfileActivity
import com.kuuuzaa.mobile.R
import com.kuuuzaa.retrofit.AuthRequest
import com.kuuuzaa.retrofit.MainApi
import com.kuuuzaa.retrofit.RegRequest
import com.kuuuzaa.retrofit.UrlAdress
import com.kuuuzaa.retrofit.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var mainApi: MainApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_up)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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

        val linkToAuth: TextView = findViewById(R.id.text_sign_up)

        linkToAuth.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }

        var user: User? = null
        val userEmail: EditText = findViewById(R.id.email_sign_up)
        val userPass: EditText = findViewById(R.id.password_sign_up)
        val buttonSignIn: Button = findViewById(R.id.button_sign_up)
        buttonSignIn.setOnClickListener{
            val email = userEmail.text.toString()
            val pass = userPass.text.toString()
            if(email == "" || pass == "")
                Toast.makeText(this, "Введите email и password", Toast.LENGTH_SHORT).show()
            else{
                CoroutineScope(Dispatchers.IO).launch{
                    user = mainApi.reg(
                        RegRequest(
                            email,
                            pass
                        )
                    )
                    println(user!!.accessToken)
                    val intentProfile = Intent(this@SignUpActivity, SignInActivity::class.java)
                    startActivity(intentProfile)
                }

            }
        }

    }
}