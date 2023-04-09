package ir.m3hdi.agahinet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import ir.m3hdi.agahinet.data.model.SigninState
import ir.m3hdi.agahinet.data.model.SignupState
import ir.m3hdi.agahinet.databinding.ActivityAuthBinding
import ir.m3hdi.agahinet.ui.viewmodel.AuthViewModel
import ir.m3hdi.agahinet.util.AppUtils
import ir.m3hdi.agahinet.util.Resultx

@AndroidEntryPoint
class AuthActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
    }

    private fun setUpUI()
    {
        binding.editTextSigninPassword.doOnTextChanged{ text, _, _, _ ->
            if (text.toString().isEmpty()){
                binding.textFieldSigninPassword.endIconMode= TextInputLayout.END_ICON_NONE
            }else{
                binding.textFieldSigninPassword.endIconMode= TextInputLayout.END_ICON_PASSWORD_TOGGLE
            }
        }
        binding.editTextSignupPassword.doOnTextChanged{ text, _, _, _ ->
            if (text.toString().isEmpty()){
                binding.textFieldSignupPassword.endIconMode= TextInputLayout.END_ICON_NONE
            }else{
                binding.textFieldSignupPassword.endIconMode= TextInputLayout.END_ICON_PASSWORD_TOGGLE
            }
        }

        binding.buttonGotoSignup.setOnClickListener {
            AppUtils.sharedAxisXTransition(binding.materialCardView,binding.layoutSignin,binding.layoutSignup)
        }
        binding.buttonBackToSignin.setOnClickListener {
            AppUtils.sharedAxisXTransition(binding.materialCardView,binding.layoutSignup,binding.layoutSignin)
        }

        binding.buttonCancel.setOnClickListener {
            finish()
        }

        binding.buttonSignin.setOnClickListener {
            val email=binding.editTextSigninEmail.text.toString()
            val password=binding.editTextSigninPassword.text.toString()
            viewModel.performSignin(email,password)
        }


        binding.buttonSignup.setOnClickListener {
            val name=binding.editTextSignupName.text.toString()
            val email=binding.editTextSignupEmail.text.toString()
            val password=binding.editTextSignupPassword.text.toString()
            val phoneNumber=binding.editTextSignupPhoneNumber.text.toString()

            viewModel.performSignup(name,email,password,phoneNumber)
        }

        viewModel.signinState.observe(this){

            when(it){
                is Resultx.Loading -> {
                    Toast.makeText(applicationContext,"LOADING",Toast.LENGTH_SHORT).show()
                }
                is Resultx.Success -> {
                    when(it.value){
                        SigninState.OK -> {
                            Toast.makeText(applicationContext,"OK",Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        SigninState.BAD_EMAIL -> {
                            Toast.makeText(applicationContext,"BAD_EMAIL",Toast.LENGTH_SHORT).show()
                        }
                        SigninState.BAD_PASSWORD -> {
                            Toast.makeText(applicationContext,"BAD_PASSWORD",Toast.LENGTH_SHORT).show()
                        }
                        SigninState.INCORRECT_CREDS -> {
                            Toast.makeText(applicationContext,"INCORRECT_CREDS",Toast.LENGTH_SHORT).show()
                        }

                    }

                }
                is Resultx.Failure -> {
                    Toast.makeText(applicationContext,"FAILED",Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.signupState.observe(this){

            when(it){
                is Resultx.Loading -> {
                    Toast.makeText(applicationContext,"LOADING",Toast.LENGTH_SHORT).show()
                }
                is Resultx.Success -> {
                    when(it.value){
                        SignupState.OK -> {
                            Toast.makeText(applicationContext,"OK",Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        SignupState.BAD_NAME -> {
                            Toast.makeText(applicationContext,"BAD_NAME",Toast.LENGTH_SHORT).show()
                        }
                        SignupState.BAD_EMAIL -> {
                            Toast.makeText(applicationContext,"BAD_EMAIL",Toast.LENGTH_SHORT).show()
                        }
                        SignupState.BAD_PASSWORD -> {
                            Toast.makeText(applicationContext,"BAD_PASSWORD",Toast.LENGTH_SHORT).show()
                        }
                        SignupState.BAD_PHONE_NUMBER -> {
                            Toast.makeText(applicationContext,"BAD_PHONE_NUMBER",Toast.LENGTH_SHORT).show()
                        }
                        SignupState.DUPLICATE_EMAIL -> {
                            Toast.makeText(applicationContext,"DUPLICATE_EMAIL",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is Resultx.Failure -> {
                    Toast.makeText(applicationContext,"FAILED",Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}