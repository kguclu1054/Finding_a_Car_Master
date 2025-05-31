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
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class Register : AppCompatActivity() {

    private lateinit var binding: RegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = RegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.btnGirisYap.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Kayıt butonu için - GÜNCELLENDİ
        binding.registerButton.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()
            val name = binding.registerName.text.toString() // Yeni alan
            val phone = binding.registerPhone.text.toString() // Yeni alan

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val result = supabase.auth.signUpWith(Email) {
                            this.email = email
                            this.password = password

                            data = buildJsonObject {
                                put("name", name)
                                put("phone", phone)
                            }
                        }
                        Toast.makeText(this@Register, "Kayıt başarılı! E-posta doğrulaması kontrol edin.", Toast.LENGTH_LONG).show()

                        // Başarılı kayıttan sonra login sayfasına dön
                        val intent = Intent(this@Register, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } catch (e: Exception) {
                        Toast.makeText(this@Register, "Kayıt başarısız: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@Register, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}