package com.example.tap_sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.tap_sample.databinding.ActivityLoginBinding
import com.example.tap_sample.network.Constants
import com.example.tap_sample.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch



@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fetchData()

        binding.btnLogin.setOnClickListener(){
            val username = binding.edtUserName.text.toString()
            val password = binding.edtPassWord.text.toString()
            lifecycleScope.launch {
                val login = loginViewModel.login(username, password)


                if (login!=null) {
                       val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.putExtra("userDetails",login)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this@LoginActivity, "Please Enter Valid Credentials", Toast.LENGTH_LONG).show()
                    }

            }

        }

    }

    private fun fetchData() {

        binding.btnLogin.visibility = View.INVISIBLE

        loginViewModel.progress.observe(this) { progress ->
            binding.progressBar.progress = progress
            if (progress == 100) {
                binding.progressBar.visibility = View.INVISIBLE
                binding.btnLogin.visibility = View.VISIBLE
                binding.txtDeviceActivation.apply {
                    text = "Device Activated"
                    setBackgroundColor(ContextCompat.getColor(context, R.color.scan_id_background_green))
                }
                Toast.makeText(this, "Download Complete", Toast.LENGTH_SHORT).show()
            }

        }
        loginViewModel.downloadAndExtractDataFromUrl(Constants.DOWNLOAD_URL)

        loginViewModel.fetchDataResponse()

      /*  loginViewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {

                    Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {

                }
            }

        }*/
    }
}