package com.yorran.lojavirtual.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.yorran.lojavirtual.R
import com.yorran.lojavirtual.databinding.ActivitySignUpBinding
import utlis.Utils

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signUpBinding.signUnBtn.setOnClickListener {

            val email = signUpBinding.editTextEmail.text.toString()
            val password = signUpBinding.editTextPassword.text.toString()
            val confirmPassword = signUpBinding.editTextConfirmPassword.text.toString()

            if (password == confirmPassword){
                //Validando os campos
                val validationEmail = Utils.validation(email, this, signUpBinding.editTextEmail)
                val validationPassword = Utils.validation(password, this, signUpBinding.editTextPassword)
                val validationConfirmPassword = Utils.validation(confirmPassword, this, signUpBinding.editTextConfirmPassword)

                if (validationEmail && validationPassword && validationConfirmPassword){
                    //Criação de conta com firebase
                    signUp(email, password)
                }
            }else{
                Toast.makeText(this, "Password and Confirm Password must be the same", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun signUp(email:String, password: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { Auth->
                if (Auth.isSuccessful){
                    Toast.makeText(this,"SigUp is Successful", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { Exception->
                Toast.makeText(this,"SigUp is error", Toast.LENGTH_SHORT).show()

            }
    }
}