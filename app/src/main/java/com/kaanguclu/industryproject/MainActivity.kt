package com.kaanguclu.industryproject

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kaanguclu.industryproject.databinding.ActivityMainBinding
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.*


val supabase = createSupabaseClient(
    supabaseUrl = "https://ahogblddcilfpnkwavzj.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImFob2dibGRkY2lsZnBua3dhdnpqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDgzNTEyNTcsImV4cCI6MjA2MzkyNzI1N30.ofchNs38O6e9N0Mf-d5taIeO5hLfER3xWRxEQwn5cLI"
) {
    install(Auth)
    install(Postgrest)
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isPasswordVisible = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnKayitOl.setOnClickListener {
            val intent = Intent(this@MainActivity, Register::class.java)
            startActivity(intent)
        }



        binding.loginButton.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val result = supabase.auth.signInWith(Email) {
                            this.email = email
                            this.password = password
                        }
                        Toast.makeText(this@MainActivity, "Giriş başarılı!", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(applicationContext, Register::class.java)
                        startActivity(intent)


                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "Giriş başarısız: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Lütfen e-posta ve şifre girin!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        binding.forgotPasswordButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Password_Reset::class.java)
            startActivity(intent)
        }

            binding.passwordToggle.setOnClickListener{
                isPasswordVisible = !isPasswordVisible
                if (isPasswordVisible) {
                    binding.loginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    binding.passwordToggle.setImageResource(R.drawable.visible_password)
                } else {
                    binding.loginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.passwordToggle.setImageResource(R.drawable.invisible_password)
                }
                binding.loginPassword.setSelection(binding.loginPassword.text.length)
            }

            }

        }


