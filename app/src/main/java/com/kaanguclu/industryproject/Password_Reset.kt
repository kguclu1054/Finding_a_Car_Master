package com.kaanguclu.industryproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kaanguclu.industryproject.databinding.PasswordResetBinding
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class Password_Reset : AppCompatActivity() {

    private lateinit var binding: PasswordResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PasswordResetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendResetLink.setOnClickListener {
            val email = binding.resetEmail.text.toString()

            if (email.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        // Güncellenmiş API kullanımı - redirectTo parametresi eklendi
                        supabase.auth.resetPasswordForEmail(email)

                        Toast.makeText(
                            this@Password_Reset,
                            "Şifre sıfırlama linki e-posta adresinize gönderildi.",
                            Toast.LENGTH_LONG
                        ).show()

                        finish() // Bu aktiviteyi kapat
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@Password_Reset,
                            "Hata oluştu: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this@Password_Reset,
                    "Lütfen e-posta adresinizi girin!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
