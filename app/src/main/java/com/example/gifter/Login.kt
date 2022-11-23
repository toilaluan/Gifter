package com.example.gifter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {
    val url : String = "http://192.168.1.11/login/login.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginToSignUp = findViewById<TextView>(R.id.login_to_signup)
        loginToSignUp.setOnClickListener {
            val intent = Intent(applicationContext, SignUp::class.java)
            startActivity(intent)
            finish()
        }
        val btnLogin = findViewById<Button>(R.id.login_button)
        val etUsername = findViewById<TextInputEditText>(R.id.login_username)
        val etPassword = findViewById<TextInputEditText>(R.id.login_password)
        btnLogin.setOnClickListener {
            signupUser(etUsername, etPassword)
        }
    }
    fun signupUser(usernameInput: TextInputEditText, passwordInput: TextInputEditText){
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()
        if (!username.equals("") && !password.equals("")){

            val request: StringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
                Log.d("Loginluan", response.toString())
                if (response.toString().equals("success")){
                    val intent : Intent = Intent(this, MainActivity::class.java)
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
                    return data
                }
            }
            val queue = Volley.newRequestQueue(applicationContext);
            queue.add(request)
        }else{
            Log.d("luann", "all fields")
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_LONG).show()
        }

    }
}