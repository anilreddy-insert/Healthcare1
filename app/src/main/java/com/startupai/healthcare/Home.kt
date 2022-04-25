package com.startupai.healthcare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.picasso.PicassoImageLoaderFactory
import com.startupai.healthcare.databinding.ActivityHomeBinding


class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    companion object {
        private const val SLIDE_NUMBER = 10
    }

    private val imageUrls = arrayListOf(
        "https://hackernoon.com/hn-images/1*JR4Iw9bXbUyo8-KsgyHywg.jpeg",
        "https://www.news-medical.net/image.axd?picture=2021%2F1%2Fshutterstock_1067569886_(1).jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbXre8R7-If2rbqLzniCwaVX-wSxT5-FJk_Td74xzFZspGl3nZ0SiZj6PQyyJrctwG67I&usqp=CAU",
        "https://media.istockphoto.com/photos/healthcare-business-concept-medical-examination-and-growth-graph-data-picture-id1274428125?k=20&m=1274428125&s=612x612&w=0&h=Sq02xTXwuOkFi5tA4uVfeG4hKD-e54R8a7uA7zX7b74=",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQsiqtVGcFpnLlOiqFKICFCJvfgSPs2nae74g&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR7b6HlXdUIwLutJ5uGjSkYrEPo92KD85RKug&usqp=CAU"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
            .requestIdToken("105120610875-uirrof53fnpm6m87fgtk7pm01esp7s20.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val sharedPreferences: SharedPreferences? = getSharedPreferences("HealthCareapp",
            Context.MODE_PRIVATE)
        val spfEditor: SharedPreferences.Editor? =  sharedPreferences?.edit()

        binding.tvHomeUserName.text="Welcome "+sharedPreferences?.getString("username","")+","


        binding.btnlogout.setOnClickListener {
            mGoogleSignInClient.signOut()
            getSharedPreferences("HealthCareapp",Context.MODE_PRIVATE).edit().clear().apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.imageSlider.adapter=SliderAdapter(this,PicassoImageLoaderFactory(),imageUrls=imageUrls)

        binding.tvEmergencyCall.setOnClickListener {
            val phone = "911"
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)
        }

        binding.tvMedicneShops.setOnClickListener {
            val intent = Intent(this, Medicine::class.java)
            startActivity(intent)
        }

        binding.tvFitnessReport.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.fitness")))
        }

        binding.tvContactUs.setOnClickListener {
            val intent = Intent(this, WebView::class.java)
            intent.putExtra("url", "https://www.healthcare.gov/contact-us/")
            startActivity(intent)
        }

        binding.tvBookApp.setOnClickListener {
            val intent = Intent(this, BookingAppointment::class.java)
            startActivity(intent)
        }


        binding.tvFindADoctor.setOnClickListener {
            val intent = Intent(this, WebView::class.java)
            intent.putExtra("url", "https://www.nyp.org/")
            startActivity(intent)
        }

        binding.tvOnlineConsulting.setOnClickListener {
            val intent = Intent(this, WebView::class.java)
            intent.putExtra("url", "https://secondmedic.com/app/promotion-second-opinion?utm_source=Google&utm_medium=Paid&utm_campaign=CHLEAR_SecondMedic_Second_Opinion_14th_Apr%2722&gclid=Cj0KCQjw6pOTBhCTARIsAHF23fJyGRbb4GkgnX1UGwNhg1B0Rq2K0AG2aK4_IpuKSW6Yn43xWbnUwYwaAtfREALw_wcB")
            startActivity(intent)
        }

        binding.tvFitnessTracker.setOnClickListener {
            val intent = Intent(this, FitnessTracker::class.java)
            startActivity(intent)
        }


    }

}