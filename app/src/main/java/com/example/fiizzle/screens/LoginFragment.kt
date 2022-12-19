package com.example.fiizzle.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fiizzle.R
import com.example.fiizzle.data.PtoJDatabase
import com.example.fiizzle.databinding.FragmentAllBinding
import com.example.fiizzle.databinding.FragmentLoginBinding
import com.example.fiizzle.utils.putUserIdxPref

class LoginFragment : Fragment() {



    private lateinit var binding : FragmentLoginBinding
    lateinit var db : PtoJDatabase

    private var userIdx : Int = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
        db = PtoJDatabase.getInstance(requireContext())

        clickHandler()


        return binding.root
    }

    private fun clickHandler() {
        binding.loginSignInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }

        binding.loginLoginBtn.setOnClickListener {
            if (binding.loginEdtId.text.toString().isNullOrBlank() || binding.loginEdtPw.text.toString().isNullOrBlank()) {
                Toast.makeText(requireContext(), "아이디와 비밀번호를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                var getIdx : Thread = Thread {
                    userIdx = db.userDao.getUserIdxWithLogin(binding.loginEdtId.text.toString(), binding.loginEdtPw.text.toString())
                    Log.d("LOGIN_USER", db.userDao.getUserInfo(userIdx).toString())
                }
                getIdx.start()

                try {
                    getIdx.join()
                } catch (e : InterruptedException) {
                    e.printStackTrace()
                }

                putUserIdxPref(requireContext(), userIdx)
                findNavController().navigate(R.id.action_loginFragment_to_allFragment)
            }
        }
    }
}