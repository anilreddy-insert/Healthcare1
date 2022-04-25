package com.startupai.healthcare

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.startupai.healthcare.databinding.ActivityMainBinding
import com.startupai.healthcare.databinding.ActivitySignupBinding

class Signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.btnSignup.setOnClickListener {
            if (binding.tietFnm.text.toString().isNullOrBlank()||binding.tietLnm.text.toString().isNullOrBlank()||binding.tietSigupEmail.text.toString().isNullOrBlank()||binding.tietSignupPswd.text.toString().isNullOrBlank()||binding.tietSignupRePswd.text.toString().isNullOrBlank()||!binding.cboxTandC.isChecked){
                Snackbar.make(binding.root, "It's Mandatory to fill all the fields and accept T&C", Snackbar.LENGTH_LONG).show()
            }else{
                if (comparepassword(binding.tietSignupPswd.text.toString(),binding.tietSignupRePswd.text.toString())==true){
                    Snackbar.make(binding.root, "Both Passwords Must Match to Proceed", Snackbar.LENGTH_LONG).show()
                }else{
                    try {
                        val db = DbClass(this, null)
                        db.addUser(binding.tietFnm.text.toString(), binding.tietLnm.text.toString(),binding.tietSigupEmail.text.toString(),binding.tietSignupRePswd.text.toString())
                        Snackbar.make(binding.root, "Signed up Successfully", Snackbar.LENGTH_LONG).show()
                        val mIntent = Intent(this, MainActivity::class.java)
                        startActivity(mIntent)
                        finish()
                    }catch (e:SQLiteException){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun comparepassword(pass:String,confpass:String):Boolean{
        if (pass.equals(confpass)){
            return false
        }
        return true
    }
}