package com.startupai.healthcare

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.Time
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.startupai.healthcare.databinding.ActivityBookingAppointmentBinding
import com.startupai.healthcare.databinding.DialogDownloadedSentBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class BookingAppointment : AppCompatActivity() {
    private lateinit var binding: ActivityBookingAppointmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        var gender = ArrayList<String>()
        gender.add("Select gender")
        gender.add("Male")
        gender.add("Female")
        gender.add("Other")

        val ad: ArrayAdapter<*> = ArrayAdapter<String>(
            this,
            R.layout.simple_spinner_item,
            gender
        )
        binding.spnrGender.adapter=ad


        binding.tietBaDate.setOnClickListener {
            datePicker(this,binding.tietBaDate)
        }

        binding.tietBaTime.setOnClickListener {
            TimePicker(this,binding.tietBaTime)
        }



        binding.btnBook.setOnClickListener {
            if (binding.tietBaFnm.text.toString().isNullOrBlank()||binding.tietBaLnm.text.toString().isNullOrBlank()||binding.spnrGender.selectedItemPosition==0||
                binding.tietBaAge.text.toString().isNullOrBlank()||binding.tietBaPhoneNumber.text.toString().isNullOrBlank()||binding.tietBaEmail.text.toString().isNullOrBlank()||
                binding.tietBaDate.text.toString().isNullOrBlank()||binding.tietBaTime.text.toString().isNullOrBlank()){
                Snackbar.make(binding.root, "It's Mandatory to fill all the fields and Select Gender", Snackbar.LENGTH_LONG).show()
            }else{
                try {
                    val db = DbAppointmentClass(this, null)
                    db.addAppointment(binding.tietBaFnm.text.toString(), binding.tietBaLnm.text.toString(),binding.spnrGender.selectedItem.toString(),
                        binding.tietBaAge.text.toString(),binding.tietBaPhoneNumber.text.toString(),
                        binding.tietBaEmail.text.toString(),binding.tietBaDate.text.toString(),binding.tietBaTime.text.toString())
                    appointmentSuccessfull(this)
                }catch (e: SQLiteException){
                    e.printStackTrace()
                }
            }
        }



    }

    fun datePicker(context: Context, tv: TextInputEditText){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            val chosenDate = Time()
            chosenDate.set(dayOfMonth, monthOfYear, year)
            val dtDob = chosenDate.toMillis(true)

            val format = SimpleDateFormat("dd/MM/yyyy")
            val dpdDate = format.format(dtDob)

            tv.setText(dpdDate)


        }, year, month, day)

        dpd.datePicker.minDate= System.currentTimeMillis()
        dpd.show()
    }


    fun TimePicker(context: Context, tv: TextInputEditText){
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        // date picker dialog
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            c.set(Calendar.HOUR_OF_DAY, hour)
            c.set(Calendar.MINUTE, minute)
            tv.setText(SimpleDateFormat("hh:mm aa").format(c.time))
        }
        val timePickerDialog = TimePickerDialog(this,
            AlertDialog.THEME_HOLO_LIGHT, timeSetListener, hour, minute,
            false)
        timePickerDialog.show()

    }

    fun appointmentSuccessfull(context:Context){
        val bind:DialogDownloadedSentBinding
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        bind = DialogDownloadedSentBinding.inflate(layoutInflater)
        dialog.setContentView(bind.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bind.tvDialogSuccessText.setText("Booked Successfully")
        dialog.setOnDismissListener {
            val mIntent = Intent(this, Home::class.java)
            startActivity(mIntent)
            finish()
        }
        dialog.show()
    }

}