package com.tugcearas.sweety.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tugcearas.sweety.R
import com.tugcearas.sweety.databinding.FragmentSignupScreenBinding
import com.tugcearas.sweety.util.extensions.click
import com.tugcearas.sweety.util.extensions.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupScreen : Fragment() {

    private lateinit var binding : FragmentSignupScreenBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.tvSigninClick.click {
            findNavController().navigate(R.id.action_signupScreen_to_signinScreen)
        }

        checkUserInfo()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
        return emailRegex.matches(email)
    }

    private fun checkUserInfo() {
        with(binding) {
            btnSignup.click {
                val getEmail = tvSignupMail.text.toString()
                val getPassword = tvSignupPassword.text.toString()

                if (getEmail.isNotEmpty() && isValidEmail(getEmail ) && getPassword.length >= 6) {
                    if (getEmail.endsWith(".com")) {
                        signUp(getEmail, getPassword)
                    } else {
                        requireView().snackbar("Missing email address!")
                    }
                }
                else if (getEmail.isNotEmpty() && isValidEmail(getEmail) && getPassword.length < 6){
                    requireView().snackbar("Password length must be minimum 6 characters long")
                }
                else if (getEmail.isEmpty() && getPassword.isEmpty()){
                    requireView().snackbar("Fill in the blanks!")
                }
                else {
                    requireView().snackbar("Missing email address or password!")
                }
            }
        }
    }

    private fun signUp(email:String, password:String){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            findNavController().navigate(R.id.action_signupScreen_to_homeScreen)
            requireView().snackbar("Sign up successfully!")
        }.addOnFailureListener {
            requireView().snackbar("Please sign in!")
        }
    }
}