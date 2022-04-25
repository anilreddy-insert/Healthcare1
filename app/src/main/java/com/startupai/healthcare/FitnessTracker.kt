package com.startupai.healthcare

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.startupai.healthcare.databinding.ActivityFitnessTrackerBinding
import com.startupai.healthcare.databinding.ItemMedicinesBinding


class FitnessTracker : AppCompatActivity() {
    private lateinit var binding: ActivityFitnessTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitnessTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val fitnessTrackers = listOf(
            MedicinesShopsUrls("Heart rate", "https://onlineheartrate.com/"),
            MedicinesShopsUrls(
                "ECG Measure",
                "https://www.biopac.com/application/ecg-cardiology/advanced-feature/online-ecg-analysis/"
            ),
            MedicinesShopsUrls("Steps", "https://www.google.com/fit/"),
            MedicinesShopsUrls(
                "Nutrition",
                "https://www.healthifyme.com/blog/best-indian-diet-plan-weight-loss/"
            ),
            MedicinesShopsUrls(
                "Calories Burned",
                "https://www.calculator.net/calories-burned-calculator.html"
            ),
            MedicinesShopsUrls("BMI", "https://www.calculator.net/bmi-calculator.html"),
            MedicinesShopsUrls(
                "Mindfulness",
                "https://www.artofliving.org/in-en/free-online-meditation"
            ),
        )

        val fitnessTrackerAdapter= FitnessTrackerAdapter(this,fitnessTrackers)
        binding.rvFitnessTracker.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        binding.rvFitnessTracker.adapter = fitnessTrackerAdapter
        binding.rvFitnessTracker.setItemViewCacheSize(fitnessTrackers.size)
    }

}

class FitnessTrackerViewHolder(val binding: ItemMedicinesBinding) : RecyclerView.ViewHolder(binding.root)


class FitnessTrackerAdapter(requireContext: Context, fitnessTrackers: List<MedicinesShopsUrls>):RecyclerView.Adapter<MedicinesViewHolder>(){

    var mList = listOf<MedicinesShopsUrls>()
    lateinit var mContext:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinesViewHolder {
        val binding = ItemMedicinesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MedicinesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicinesViewHolder, position: Int) {
        holder.binding.tvMedicineCompany.text=mList.get(position).ShopName
        
        
        if(position==0){
            holder.binding.tvMedicineCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart,0,0,0)
            holder.binding.tvMedicineCompany.compoundDrawablePadding=10
        }else if (position==1){
            holder.binding.tvMedicineCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ecg_svgrepo_com,0,0,0)
            holder.binding.tvMedicineCompany.compoundDrawablePadding=10
        }else if (position==2){
            holder.binding.tvMedicineCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_steps_counter,0,0,0)
            holder.binding.tvMedicineCompany.compoundDrawablePadding=10
        }else if (position==3){
            holder.binding.tvMedicineCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_nutrition,0,0,0)
            holder.binding.tvMedicineCompany.compoundDrawablePadding=10
        }else if (position==4){
            holder.binding.tvMedicineCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_calories,0,0,0)
            holder.binding.tvMedicineCompany.compoundDrawablePadding=10
        }else if (position==5){
            holder.binding.tvMedicineCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_body_mass_index_svgrepo_com,0,0,0)
            holder.binding.tvMedicineCompany.compoundDrawablePadding=10
        }else if (position==6){
            holder.binding.tvMedicineCompany.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_meditation,0,0,0)
            holder.binding.tvMedicineCompany.compoundDrawablePadding=10
        }


        holder.binding.tvMedicineCompany.setOnClickListener {
            try {
                val intent = Intent(mContext, WebView::class.java)
                intent.putExtra("url", mList.get(position).urls)
                mContext.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    init {
        this.mList=fitnessTrackers
        this.mContext=requireContext
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}