package com.example.here

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.here.databinding.ActivitySignUpBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private var auth: FirebaseAuth? = null

    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        signUpPageGoogleSignIn()
        initGoogleLoginButton()
        initFacebookLoginButton()
        signUpInitButton()
    }
    
    private fun initGoogleLoginButton() {
        binding.signUpGoogleLogin.setOnClickListener {
            signIn()
        }
    }

    private fun initFacebookLoginButton() {
        binding.signUpFaceBookLogin.setOnClickListener {
            facebookLogin()
        }
    }

    private fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, listOf("email", "public_profile"))

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Toast.makeText(this@SignUpActivity, "로그인 취소!", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(this@SignUpActivity, "로그인 에러!", Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(result: LoginResult) {
                    firebaseAuthWithFacebook(result.accessToken)
                }
            })
    }

    private fun firebaseAuthWithFacebook(accessToken: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(accessToken.token)

        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loginSuccess()
                } else {
                    Toast.makeText(this, "로그인 실패!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signUpPageGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent =  googleSignInClient.signInIntent
        googleStartForResult.launch(signInIntent)
    }

    private val googleStartForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("accountToken", "${account.id}")
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.d("accountTokenError", "accountTokenError")
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("accountTokenError", "accountTokenError")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    loginSuccess()
                } else {
                    finish()
                }
            }
    }

    private fun loginSuccess() {
        Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    @SuppressLint("ShowToast")
    private fun signUpInitButton() {
        binding.signUpMakeButton.setOnClickListener {
            val name = binding.signUpNameEditText.editText?.text.toString()
            val email = binding.signUpEmailEditText.editText?.text.toString()
            val password = binding.signUpPasswordEditText.editText?.text.toString()
            val passwordCheck = binding.signUpPasswordCheckEditText.editText?.text.toString()

            if (!nameCheck(name)) {
                return@setOnClickListener
            }

            if (!emailSpellingCheck(email)) {
                return@setOnClickListener
            }

            if (!passwordCheck(password, passwordCheck)) {
                return@setOnClickListener
            }

            signUp(email, passwordCheck, it)
        }
    }

    private fun signUp(email: String, passwordCheck: String, view: View) {
        auth?.createUserWithEmailAndPassword(email, passwordCheck)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val snackbar = Snackbar.make(view, "회원가입에 성공하셨습니다.", Snackbar.LENGTH_LONG)
                    snackbar.setAction("로그인하러가기") {
                        startActivity(
                            Intent(this, LoginActivity::class.java)
                        )
                        finish()
                        snackbar.dismiss()
                    }
                    snackbar.show()
                } else {
                    Snackbar.make(view, "회원가입에 실패하였습니다.", Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    private fun nameCheck(name: String): Boolean {
        return if (name.isEmpty()) {
            binding.signUpNameEditText.error = "이름을 입력해주세요."
            false
        } else {
            binding.signUpNameEditText.apply {
                helperText = null
                error = null
                isErrorEnabled = false
            }
            true
        }
    }

    private fun emailSpellingCheck(email: String): Boolean {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS

        return if (email.isEmpty()) {
            binding.signUpEmailEditText.error = "이메일을 입력해주세요."
            false
        } else if (!emailPattern.matcher(email).matches()) {
            binding.signUpEmailEditText.error = "이메일 형식이 틀립니다."
            false
        } else {
            binding.signUpEmailEditText.helperText = null
            binding.signUpEmailEditText.error = null
            binding.signUpEmailEditText.isErrorEnabled = false
            true
        }
    }

    private fun passwordCheck(password: String, passwordCheck: String): Boolean {
        return when {
            password.isEmpty() -> {
                binding.signUpPasswordEditText.error = "비밀번호를 입력해주세요."
                false
            }
            passwordCheck.isEmpty() -> {
                binding.signUpPasswordCheckEditText.error = "비밀번호가 일치하지 않습니다."
                false
            }
            password.isEmpty() && passwordCheck.isEmpty() -> {
                binding.signUpPasswordEditText.error = "비밀번호를 입력해주세요."
                binding.signUpPasswordCheckEditText.error = "비밀번호가 일치하지 않습니다."
                false
            }
            password.length < 6 -> {
                binding.signUpPasswordEditText.error = "비밀번호는 6자리 이상입력해주세요."
                false
            }
            password != passwordCheck -> {
                binding.signUpPasswordEditText.error = "비밀번호가 일치하지 않습니다."
                binding.signUpPasswordCheckEditText.error = "비밀번호가 일치하지 않습니다."
                false
            }
            else -> {
                binding.signUpPasswordEditText.apply {
                    helperText = null
                    error = null
                    isErrorEnabled = false
                }
                binding.signUpPasswordCheckEditText.apply {
                    helperText = null
                    error = null
                    isErrorEnabled = false
                }
                true
            }
        }
    }
}