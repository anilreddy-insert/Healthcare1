package com.startupai.healthcare

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.startupai.healthcare.databinding.ActivityMedicineBinding
import com.startupai.healthcare.databinding.ItemMedicinesBinding

class Medicine : AppCompatActivity() {
    private lateinit var binding: ActivityMedicineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMedicineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val shopsList = listOf(
            MedicinesShopsUrls("PharmEasy","https://play.google.com/store/apps/details?id=com.phonegap.rxpal"),
            MedicinesShopsUrls("MedPlus Mart - Online Pharmacy","https://play.google.com/store/apps/details?id=com.medplus.mobile.android"),
            MedicinesShopsUrls("Apollo 247 - Online Doctor & Apollo Pharmacy App","https://play.google.com/store/apps/details?id=com.apollo.patientapp"),
            MedicinesShopsUrls("Truemeds - Online Medicine App","https://play.google.com/store/apps/details?id=com.intellihealth.truemeds"),
            MedicinesShopsUrls("SiashMed - Pharmacy Partners","https://play.google.com/store/apps/details?id=com.siashmedpharmacy"),
            MedicinesShopsUrls("AlDawaa Pharmacies","https://play.google.com/store/apps/details?id=com.kr.aldawaa"),
            MedicinesShopsUrls("DrMed Pharmacy","https://play.google.com/store/apps/details?id=com.drMed"),
            MedicinesShopsUrls("Zeno Health - Generic Pharmacy","https://play.google.com/store/apps/details?id=com.zenohealth.android"),
            MedicinesShopsUrls("Alfath Pharmacy","https://play.google.com/store/apps/details?id=com.mobile.pharmacy.alfath"),
            MedicinesShopsUrls("Aayu: Quick medicine home delivery, online doctors","https://play.google.com/store/apps/details?id=com.medcords.rurallite"),
            MedicinesShopsUrls("Sehat Sathi: Take your medical store online","https://play.google.com/store/apps/details?id=com.medcords.mhcpanel"),
            MedicinesShopsUrls("Healthmug - Healthcare App","https://play.google.com/store/apps/details?id=com.healthmug"),
            MedicinesShopsUrls("HealthPotli - Order Medicines Online","https://play.google.com/store/apps/details?id=com.system2.solutions.healthpotli.activities"),
            MedicinesShopsUrls("Drucare Direct -Virtual Healthcare App","https://play.google.com/store/apps/details?id=in.drucare.direct_001"),
            MedicinesShopsUrls("MyHealthcare Patient Ecosystem","https://play.google.com/store/apps/details?id=com.innocirc.vcpatient"),
            MedicinesShopsUrls("Drucare CliQ - The Family Healthcare App","https://play.google.com/store/apps/details?id=drucare.patient.portal"),

        )


        binding.ivMedicinesBack.setOnClickListener {
            onBackPressed()
        }

        val medicinesViewAdapter= MedicinesViewAdapter(this,shopsList)
        binding.rvMedicines.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        binding.rvMedicines.adapter = medicinesViewAdapter
        binding.rvMedicines.setItemViewCacheSize(shopsList.size)

    }
}

class MedicinesViewHolder(val binding: ItemMedicinesBinding) : RecyclerView.ViewHolder(binding.root)

class MedicinesViewAdapter(requireContext: Context, shopsList: List<MedicinesShopsUrls>) :RecyclerView.Adapter<MedicinesViewHolder>(){
 var mList = listOf<MedicinesShopsUrls>()
    lateinit var mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinesViewHolder {
        val binding = ItemMedicinesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MedicinesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicinesViewHolder, position: Int) {

        holder.binding.tvMedicineCompany.text=mList.get(position).ShopName

        holder.binding.tvMedicineCompany.setOnClickListener {
            try {
                mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(position).urls)))
            } catch (e: ActivityNotFoundException) {
                mContext.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(position).urls)))
            }
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    init {
        this.mList=shopsList
        this.mContext=requireContext
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}


data class MedicinesShopsUrls(val ShopName:String,val urls:String)