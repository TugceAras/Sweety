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
import com.tugcearas.sweety.databinding.FragmentSigninScreenBinding
import com.tugcearas.sweety.util.extensions.click
import com.tugcearas.sweety.util.extensions.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninScreen : Fragment() {

    private lateinit var binding: FragmentSigninScreenBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.let {
            findNavController().navigate(R.id.action_signinScreen_to_homeScreen)
        }

        binding.tvSignupClick.click {
            findNavController().navigate(R.id.action_signinScreen_to_signupScreen)
        }

        checkUserInfo()
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
        return emailRegex.matches(email)
    }

    private fun checkUserInfo() {
        with(binding) {
            btnSignin.click {
                val getEmail = tvSigninMail.text.toString()
                val getPassword = tvSigninPassword.text.toString()

                if (getEmail.isNotEmpty() && isValidEmail(getEmail) && getPassword.length >= 6) {
                    if (getEmail.endsWith(".com")) {
                        signIn(getEmail, getPassword)
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

    private fun signIn(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            findNavController().navigate(R.id.action_signinScreen_to_homeScreen)
            requireView().snackbar("Sign in successfully!")
        }.addOnFailureListener {
            requireView().snackbar("Please sign up!")
        }
    }
}