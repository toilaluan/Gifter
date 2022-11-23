package com.example.gifter

import android.content.ContextParams
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SignUp : AppCompatActivity() {
    val url : String = "http://192.168.1.11/login/register.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val submitButton = findViewById<MaterialButton>(R.id.signup_button)
        val usernameInput = findViewById<TextInputEditText>(R.id.input_username)
        val passwordInput = findViewById<TextInputEditText>(R.id.input_password)
        val phoneInput = findViewById<TextInputEditText>(R.id.input_phone)
        val signUpToLogin = findViewById<TextView>(R.id.signup_to_login)
        signUpToLogin.setOnClickListener { v : View ->
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
        submitButton.setOnClickListener{ v : View ->
            Toast.makeText(applicationContext, "Something", Toast.LENGTH_SHORT)
            signupUser(usernameInput, passwordInput, phoneInput)
        }
    }
    fun signupUser(usernameInput: TextInputEditText, passwordInput: TextInputEditText, phoneInput: TextInputEditText){
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()
        val phone = phoneInput.text.toString()
        if (!username.equals("") && !password.equals("") && !phone.equals("")){

            val request: StringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
                Log.d("luaan", response.toString())
                if (response.toString().equals("success")){
                    val intent : Intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
            }, Response.ErrorListener { error : VolleyError? ->
                Toast.makeText(applicationContext, error.toString().trim(), Toast.LENGTH_LONG).show()
            }){
                override fun getParams(): Map<String, String> {

                    val data = HashMap<String, String>();
                    data.put("username", usernameInput.text.toString())
                    data.put("password", passwordInput.text.toString())
                    data.put("phone", phoneInput.text.toString())
                    return data
                }
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["User-Agent"] = "Mozilla/5.0"
                    return params
                }
            }
            val queue = Volley.newRequestQueue(applicationContext);
            queue.add(request)
        }else{
            Log.d("luann", "all fields")
            Toast.makeText(applicationContext, "All fields are required!", Toast.LENGTH_LONG)
        }

    }

}