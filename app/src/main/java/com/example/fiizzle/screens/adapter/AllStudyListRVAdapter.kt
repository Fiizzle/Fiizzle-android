package com.example.fiizzle.screens.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fiizzle.data.dataClass.StudyList
import com.example.fiizzle.databinding.ItemStudyListBinding

class AllStudyListRVAdapter : RecyclerView.Adapter<AllStudyListRVAdapter.ViewHolder>() {

    private val studyList = ArrayList<StudyList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemStudyListBinding = ItemStudyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(studyList[position])
    }

    override fun getItemCount(): Int = studyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addList(studyList : ArrayList<StudyList>) {
        this.studyList.clear()
        this.studyList.addAll(studyList)
    }

    inner class ViewHolder(val binding : ItemStudyListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list : StudyList) {
            binding.itemStudyListTitleTv.text = list.title
            binding.itemStudyListEndDayTv.text = list.endDate
            binding.itemStudyListDDayTv.text = list.d_day.toString()
        }

    }

}