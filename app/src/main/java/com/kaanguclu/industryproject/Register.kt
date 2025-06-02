package com.kaanguclu.industryproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
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


        binding.registerButton.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val password = binding.passwordVerification.text.toString()
            val passwordVerification = binding.registerPasswordVerification.text.toString()
            val name = binding.registerName.text.toString()
            val phone = binding.registerPhone.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty()) {

                if(password != passwordVerification){
                    Toast.makeText(this@Register, "Şifreler Uyuşmuyor!" , Toast.LENGTH_SHORT).show()

                    binding.registerEmail.setText("")
                    binding.passwordVerification.setText("")
                    binding.registerPasswordVerification.setText("")
                    binding.registerName.setText("")
                    binding.registerPhone.setText("")

                    return@setOnClickListener
                }

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


                        val intent = Intent(this@Register, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } catch (e: Exception) {
                        Toast.makeText(this@Register, "Kayıt başarısız: ${e.message}", Toast.LENGTH_SHORT).show()
                        binding.registerEmail.setText("")
                        binding.passwordVerification.setText("")
                        binding.registerPasswordVerification.setText("")
                        binding.registerName.setText("")
                        binding.registerPhone.setText("")
                    }
                }
            } else {
                Toast.makeText(this@Register, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                binding.registerEmail.setText("")
                binding.passwordVerification.setText("")
                binding.registerPasswordVerification.setText("")
                binding.registerName.setText("")
                binding.registerPhone.setText("")
            }
        }
        var isVerificationPasswordVisible = false
        binding.passwordVerificationToggle.setOnClickListener {
            isVerificationPasswordVisible = !isVerificationPasswordVisible
            if (isVerificationPasswordVisible) {
                binding.registerPasswordVerification.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordVerificationToggle.setImageResource(R.drawable.visible_password)
            } else {
                binding.registerPasswordVerification.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordVerificationToggle.setImageResource(R.drawable.invisible_password)
            }
            binding.registerPasswordVerification.setSelection(binding.registerPasswordVerification.text.length)
        }

        var isPasswordVisible = false
        binding.passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.passwordVerification.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordToggle.setImageResource(R.drawable.visible_password)
            } else {
                binding.passwordVerification.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordToggle.setImageResource(R.drawable.invisible_password)
            }
            binding.passwordVerification.setSelection(binding.registerPasswordVerification.text.length)
        }

    }
}