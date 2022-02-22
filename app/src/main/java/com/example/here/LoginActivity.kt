package com.example.here

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.here.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    //구글 로그인
    var auth: FirebaseAuth? = null
    private val GOOGLE_REQUEST_CODE = 99
    private lateinit var googleSignInClient: GoogleSignInClient


    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 구글 로그인
        auth = Firebase.auth
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //구글 로그인 버튼 클릭 리스너
        binding.googleLogin.setOnClickListener {
            signIn()
        }

        binding.goLoginButton.setOnClickListener {
            val email: String = binding.emailEditText.editText?.text.toString()
            val password = binding.passwordEditText.toString()
            if (!emailSpelling(email))
                return@setOnClickListener

            //로그인 정보 불러와야함
            loginUserId(email, password)
        }

        binding.signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun emailSpelling(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return if (email.isEmpty()) {
            binding.emailEditText.error = "이메일을 입력해주세요"
            false
        } else if (!email.matches(emailPattern.toRegex())) {
            binding.emailEditText.error = "이메일 형식이 잘못되었습니다."
            false
        } else {
            binding.emailEditText.error = null
            binding.emailEditText.isErrorEnabled = false
            true
        }
    }

    private fun loginUserId(email: String, password: String) {
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    //로그인 성공시 이벤트 발생
                    val user = auth!!.currentUser //나중에 값을 메인화면으로 갈때 값을 넘겨줘서
                    //로그인 버튼 대신에 닉네임이 나오도록 해야한다.
                    Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    //TODO 화면전환해야함 메인화면으로
                } else {
                    //로그인 실패시 이벤트 발생
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    MaterialAlertDialogBuilder(this)
                        .setTitle("로그인 오류")
                        .setMessage("이메일이나 비밀번호를 다시 확인해주세요.")
                        .setPositiveButton("확인") { _, _ ->
                        }
                        .show()
                }
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth!!.currentUser
                    loginSuccess()
                } else {
                    finish()
                }
            }
    }

    private fun loginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}