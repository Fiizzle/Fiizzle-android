package com.example.fiizzle.screens

import OnSwipeTouchListener
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fiizzle.MainActivity
import com.example.fiizzle.R
import com.example.fiizzle.data.PtoJDatabase
import com.example.fiizzle.data.dataClass.PageArray
import com.example.fiizzle.data.entity.Subject
import com.example.fiizzle.databinding.FragmentBookBinding
import com.example.fiizzle.utils.*
import com.skydoves.balloon.*
import com.skydoves.balloon.overlay.BalloonOverlayCircle


class BookFragment: Fragment() {
    private lateinit var binding : FragmentBookBinding
    lateinit var db : PtoJDatabase

    private lateinit var mContext : Context
    private lateinit var mActivity : MainActivity
    private var spinnerArray = ArrayList<String>()
    private var userIdx = 100

    private var icons = ArrayList<ImageView>()
    private var colorIcon = ArrayList<Int>()
    private var blackIcon = ArrayList<Int>()

    private val arg : BookFragmentArgs by navArgs()

    private var selectedInit = 0
    private var dayCnt = 0

    private lateinit var allSubject : ArrayList<Subject>
    private lateinit var selectedSubject : Subject



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mContext = context
            mActivity = activity as MainActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentBookBinding>(inflater, R.layout.fragment_book, container, false)
        db = PtoJDatabase.getInstance(mContext)

        userIdx = getUserIdxPref(mContext)

        appendBookIcon()
        appendColorIcon()
        appendBlackIcon()
        clickHandler()
        addSlideListener()

        selectedInit = arg.selected
        initSpinner()
        setSticker()

        return binding.root
    }

    private fun setSticker() {
        for (i in 0 until 21) {
            icons[i].setImageResource(blackIcon[i])
            icons[i].setOnClickListener {
                //nothing
            }
        }

        for(i in 0 until dayCnt){
            icons[i].setImageResource(colorIcon[i])
            icons[i].setOnClickListener {
                icons[i].showAlignBottom(makeBalloon(splitTotalPageWithDay(i + 1, selectedSubject.pageList)))
            }
        }
        if(dayCnt<11){
            binding.bookFirstLayout.visibility = View.VISIBLE;
            binding.bookSecondLayout.visibility = View.GONE;
        }
        else{
            binding.bookFirstLayout.visibility = View.GONE;
            binding.bookSecondLayout.visibility = View.VISIBLE;
        }
    }
    private fun addSlideListener(){
        binding.bookFirstLayout.setOnTouchListener(object: OnSwipeTouchListener(requireContext()) {
            override fun onSwipeLeft() {
                binding.bookFirstLayout.visibility = View.GONE;
                binding.bookSecondLayout.visibility = View.VISIBLE;
            }
        })

        binding.bookSecondLayout.setOnTouchListener(object: OnSwipeTouchListener(requireContext()) {
            override fun onSwipeRight() {
                binding.bookFirstLayout.visibility = View.VISIBLE;
                binding.bookSecondLayout.visibility = View.GONE;
            }
        })
    }

    private fun makeBalloon(pageArray: PageArray): Balloon {

        return Balloon.Builder(requireContext())
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText("오늘의 학습\n${pageArray.startPage}쪽 ~ ${pageArray.endPage}쪽")
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
    }

    private fun clickHandler() {
        binding.allBottomMypage.setOnClickListener{
            findNavController().navigate(R.id.action_bookFragment_to_mypageFragment)
        }

        binding.allSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    findNavController().navigate(R.id.action_bookFragment_to_allFragment)
                }
                else {
                    binding.bookFirstLayout.visibility = View.VISIBLE;
                    binding.bookSecondLayout.visibility = View.GONE;

                    var getSpinnerData : Thread = Thread {
                        allSubject = db.subjectDao.getAllSubject(userIdx) as ArrayList<Subject>
                    }
                    getSpinnerData.start()
                    try {
                        getSpinnerData.join()
                    } catch (e : InterruptedException) {
                        e.printStackTrace()
                    }
                    selectedSubject = allSubject[p2-1]
                    dayCnt = 21 - cal_d_day(selectedSubject.endDate)
                    if (dayCnt >= 21) {
                        dayCnt = 21
                    }
                    setSticker()

                    if (sameDays(selectedSubject.endDate)) {
                        showFinalDialog()
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun showFinalDialog() {
        FinalDialog(mContext).show()
    }
    
    private fun appendBookIcon(){
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


    private fun appendColorIcon(){
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

    private fun appendBlackIcon(){
        blackIcon.add(R.drawable.ic_book_black1)
        blackIcon.add(R.drawable.ic_book_black2)
        blackIcon.add(R.drawable.ic_book_black3)
        blackIcon.add(R.drawable.ic_book_black4)
        blackIcon.add(R.drawable.ic_book_black5)
        blackIcon.add(R.drawable.ic_book_black6)
        blackIcon.add(R.drawable.ic_book_black7)
        blackIcon.add(R.drawable.ic_book_black8)
        blackIcon.add(R.drawable.ic_book_black9)
        blackIcon.add(R.drawable.ic_book_black10)
        blackIcon.add(R.drawable.ic_book_black11)
        blackIcon.add(R.drawable.ic_book_black12)
        blackIcon.add(R.drawable.ic_book_black13)
        blackIcon.add(R.drawable.ic_book_black14)
        blackIcon.add(R.drawable.ic_book_black15)
        blackIcon.add(R.drawable.ic_book_black16)
        blackIcon.add(R.drawable.ic_book_black17)
        blackIcon.add(R.drawable.ic_book_black18)
        blackIcon.add(R.drawable.ic_book_black19)
        blackIcon.add(R.drawable.ic_book_black20)
        blackIcon.add(R.drawable.ic_book_black21)
    }

    private fun initSpinner() {  // 스피너 초기화
        spinnerArray = getSpinnerArrayPref(mContext)
        spinnerArray.add(0, "전체")
        val subject = spinnerArray.toArray()

        val subjectAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, subject)
        binding.allSpinner.adapter = subjectAdapter
        binding.allSpinner.setSelection(selectedInit)

        binding.bookFirstLayout.visibility = View.VISIBLE;
        binding.bookSecondLayout.visibility = View.GONE;

        var getSpinnerData : Thread = Thread {
            allSubject = db.subjectDao.getAllSubject(userIdx) as ArrayList<Subject>
        }
        getSpinnerData.start()
        try {
            getSpinnerData.join()
        } catch (e : InterruptedException) {
            e.printStackTrace()
        }

        selectedSubject = allSubject[selectedInit - 1]
        dayCnt = 21 - cal_d_day(selectedSubject.endDate)
        if (dayCnt >= 21) {
            dayCnt = 21
        }
    }
}

