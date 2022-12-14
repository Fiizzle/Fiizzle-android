package com.example.fiizzle.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.fiizzle.MainActivity
import com.example.fiizzle.R
import com.example.fiizzle.databinding.FragmentBookBinding
import com.skydoves.balloon.*
import com.skydoves.balloon.overlay.BalloonOverlayCircle


class BookFragment: Fragment() {
    private lateinit var binding : FragmentBookBinding

    var icons = ArrayList<ImageView>()
    var colorIcon = ArrayList<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentBookBinding>(inflater, R.layout.fragment_book, container, false)
        val balloon = Balloon.Builder(requireContext())
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("오늘의 학습\n161쪽 ~ 170쪽")
            .setTextColorResource(R.color.black)
            .setTextSize(15f)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(10)
            .setPaddingHorizontal(20)
            .setCornerRadius(8f)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .setLifecycleOwner(viewLifecycleOwner)
            .setAutoDismissDuration(1800L)
            .build()

        binding.bookButton.setOnClickListener {
            if(binding.bookFirstLayout.visibility == View.GONE){
                binding.bookFirstLayout.visibility = View.VISIBLE;
                binding.bookSecondLayout.visibility = View.GONE;
            }
            else {
                binding.bookSecondLayout.visibility = View.VISIBLE;
                binding.bookFirstLayout.visibility = View.GONE;
            }
        }
        appendBookIcon()
        appendColorIcon()

        for(i in 0..13){
            icons[i].setImageResource(colorIcon[i])
            icons[i].setOnClickListener {
                icons[i].showAlignBottom(balloon)
//                Toast.makeText(this.activity,
//                    "눌리지롱",
//                    Toast.LENGTH_SHORT)
//                    .show()
            }
        }

        initSpinner()

        return binding.root
    }

    fun appendBookIcon(){
        icons.add(binding.bookIcon1)
        icons.add(binding.bookIcon2)
        icons.add(binding.bookIcon3)
        icons.add(binding.bookIcon4)
        icons.add(binding.bookIcon5)
        icons.add(binding.bookIcon6)
        icons.add(binding.bookIcon7)
        icons.add(binding.bookIcon8)
        icons.add(binding.bookIcon9)
        icons.add(binding.bookIcon10)
        icons.add(binding.bookIcon11)
        icons.add(binding.bookIcon12)
        icons.add(binding.bookIcon13)
        icons.add(binding.bookIcon14)
        icons.add(binding.bookIcon15)
        icons.add(binding.bookIcon16)
        icons.add(binding.bookIcon17)
        icons.add(binding.bookIcon18)
        icons.add(binding.bookIcon19)
        icons.add(binding.bookIcon20)
        icons.add(binding.bookIcon21)
    }


    fun appendColorIcon(){
        colorIcon.add(R.drawable.ic_book_white1)
        colorIcon.add(R.drawable.ic_book_white2)
        colorIcon.add(R.drawable.ic_book_white3)
        colorIcon.add(R.drawable.ic_book_white4)
        colorIcon.add(R.drawable.ic_book_white5)
        colorIcon.add(R.drawable.ic_book_white6)
        colorIcon.add(R.drawable.ic_book_white7)
        colorIcon.add(R.drawable.ic_book_white8)
        colorIcon.add(R.drawable.ic_book_white9)
        colorIcon.add(R.drawable.ic_book_white10)
        colorIcon.add(R.drawable.ic_book_white11)
        colorIcon.add(R.drawable.ic_book_white12)
        colorIcon.add(R.drawable.ic_book_white13)
        colorIcon.add(R.drawable.ic_book_white14)
        colorIcon.add(R.drawable.ic_book_white15)
        colorIcon.add(R.drawable.ic_book_white16)
        colorIcon.add(R.drawable.ic_book_white17)
        colorIcon.add(R.drawable.ic_book_white18)
        colorIcon.add(R.drawable.ic_book_white19)
        colorIcon.add(R.drawable.ic_book_white20)
        colorIcon.add(R.drawable.ic_book_white21)
    }

    private fun initSpinner() {  // 스피너 초기화
        val subject = resources.getStringArray(R.array.spinner)

        val subjectAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, subject)
        binding.allSpinner.adapter = subjectAdapter
        binding.allSpinner.setSelection(0)
    }
}

