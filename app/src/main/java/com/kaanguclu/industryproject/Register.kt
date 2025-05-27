package com.kaanguclu.industryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kaanguclu.industryproject.databinding.RegisterBinding
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.launch

class Register : AppCompatActivity() {

    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Glide.with(this)
            .load(R.drawable.car_img2)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(35, 2))) // (blurRadius, sampling)
            .into(binding.registerBackground)

        binding.btnGirisYap.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Kayıt butonu için
        binding.registerButton.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val result = supabase.auth.signUpWith(Email) {
                            this.email = email
                            this.password = password
                        }
                        Toast.makeText(this@Register, "Kayıt başarılı! E-posta doğrulaması kontrol edin.", Toast.LENGTH_LONG).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@Register, "Kayıt başarısız: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}