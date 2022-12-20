package com.example.fiizzle.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fiizzle.R
import com.example.fiizzle.data.PtoJDatabase
import com.example.fiizzle.data.entity.User
import com.example.fiizzle.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {


    private lateinit var binding : FragmentSignInBinding
    lateinit var db : PtoJDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSignInBinding>(inflater, R.layout.fragment_sign_in, container, false)
        db = PtoJDatabase.getInstance(requireContext())

        clickHandler()

        return binding.root
    }

    private fun clickHandler() {
        binding.signInCompleteBtn.setOnClickListener {
            if(binding.signInEdtPw1.text.toString() != binding.signInEdtPw2.text.toString()){
                Toast.makeText(this.activity,
                    "동일한 비밀번호를 입력해주세요.",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                insertUserDataInDB()
                findNavController().navigate(R.id.action_signInFragment_to_loginFragment)
            }
        }
    }

    private fun insertUserDataInDB(){
        val user = User(
            binding.signInEdtName.text.toString(),
            binding.signInEdtEmail.text.toString(),
            binding.signInEdtPw1.text.toString()
        )
        Thread {
            db.userDao.insertUser(user)
        }.start()
    }
}