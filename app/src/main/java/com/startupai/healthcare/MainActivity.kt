package com.startupai.healthcare

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteException
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.startupai.healthcare.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    var sharedPref: SharedPreferences? = null
    lateinit var editor: SharedPreferences.Editor
    lateinit var progressBar:ProgressDialog


    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        FirebaseApp.initializeApp(this)

        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("105120610875-uirrof53fnpm6m87fgtk7pm01esp7s20.apps.googleusercontent.com")
//            .requestIdToken(getString(com.startupai.healthcare.R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()


        binding.ibtnGmail.setOnClickListener {
            progressBar= ProgressDialog(this)
            progressBar.setCancelable(false)
            progressBar.show()
            gmailLogin()
        }

        binding.tvSignup.setOnClickListener {
            val mIntent = Intent(this, Signup::class.java)
            startActivity(mIntent)
        }

        binding.btnSignin.setOnClickListener {
            if (binding.tietEmail.text.toString().isNullOrBlank()||binding.tietPassword.text.toString().isNullOrBlank()){
                Snackbar.make(binding.root, "Either Email or password is missing", Snackbar.LENGTH_LONG).show()
            }else{
                try {
                    val db = DbClass(this, null)
                    val cursor = db.getUser(binding.tietEmail.text.toString(),binding.tietPassword.text.toString())
                    if (cursor?.moveToFirst() == true){
                        val sharedPreferences: SharedPreferences? = getSharedPreferences("HealthCareapp",Context.MODE_PRIVATE)
                        val spfEditor: SharedPreferences.Editor? =  sharedPreferences?.edit()
                        spfEditor?.putString("username",cursor.getString(cursor.getColumnIndex(Constants.COL_U_F_NAME))+" "+cursor.getString(cursor.getColumnIndex(Constants.COL_U_L_NAME)))
                        spfEditor?.apply()
                        spfEditor?.commit()
                        Constants.LOGGED_USERNAME=cursor.getString(cursor.getColumnIndex(Constants.COL_U_F_NAME))+" "+cursor.getString(cursor.getColumnIndex(Constants.COL_U_L_NAME))
                        val mIntent = Intent(this, Home::class.java)
                        startActivity(mIntent)
                        finish()
                    }else{
                        Log.e("error not found", "user can't be found or database empty");
                        Snackbar.make(binding.root, "User can't be found on our database", Snackbar.LENGTH_LONG).show()
                    }
                    cursor?.close()
                    db.close()
                }catch (e: SQLiteException){
                    Snackbar.make(binding.root, "No Such account has created with above credentials"+e.message.toString(), Snackbar.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun gmailLogin() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }else{
            progressBar.dismiss()
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
                Constants.LOGGED_USERNAME = account.displayName.toString()
                val sharedPreferences: SharedPreferences? = getSharedPreferences("HealthCareapp",Context.MODE_PRIVATE)
                val spfEditor: SharedPreferences.Editor? =  sharedPreferences?.edit()
                spfEditor?.putString("username",account.displayName.toString())
                spfEditor?.apply()
                spfEditor?.commit()
            }
        } catch (e: ApiException) {
            e.printStackTrace()
            progressBar.dismiss()
            Log.e("error not found", e.message.toString());
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.READ_EXTERNAL_STORAGE) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permissions requuired, to Proceed further", Toast.LENGTH_LONG).show()
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    })
                }
                return
            }
        }
    }
}