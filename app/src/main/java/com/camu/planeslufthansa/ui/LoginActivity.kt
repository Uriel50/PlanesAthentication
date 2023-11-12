package com.camu.planeslufthansa.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.camu.planeslufthansa.R
import com.camu.planeslufthansa.databinding.ActivityLoginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private var email =""

    private var contrasenia= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            if (!validaCampos()) return@setOnClickListener

            binding.progressBar.visibility = View.GONE

            //autenticando el usuario
            autenticaUsuario(email, contrasenia)
        }

        binding.btnRegistrarse.setOnClickListener {
            if (!validaCampos()) return@setOnClickListener

            binding.progressBar.visibility = View.VISIBLE

            //Registrando al usuario

            firebaseAuth.createUserWithEmailAndPassword(email,contrasenia).addOnCompleteListener{authResult->
                if (authResult.isSuccessful){
                    //Enviar correo para verificacion
                    var user_fb = firebaseAuth.currentUser
                    user_fb?.sendEmailVerification()?.addOnSuccessListener {
                        Toast.makeText(this, getString(R.string.el_correo_de_verificacion_se_ha_enviando),Toast.LENGTH_LONG).show()
                    }?.addOnFailureListener {
                        Toast.makeText(this, getString(R.string.no_se_pudo_enviar_el_correo_de_verificacion),Toast.LENGTH_LONG).show()
                    }

                    Toast.makeText(this, getString(R.string.usuario_creado),Toast.LENGTH_LONG).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("psw",contrasenia)
                    startActivity(intent)
                    finish()
                }else{
                    binding.progressBar.visibility = View.GONE
                    manejaErrores(authResult)
                }
            }
        }

        binding.tvRestablecerPassword.setOnClickListener {
            val resetMail = EditText(it.context)
            resetMail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

            val passwordResetDialog = AlertDialog.Builder(it.context)
                .setTitle(getString(R.string.restablecer_contrase))
                .setMessage(getString(R.string.ingresa_tu_correo_para_recibir_el_enlace_para_restablecer))
                .setView(resetMail)
                .setPositiveButton(getString(R.string.enviar)){ _, _ ->
                    val mail = resetMail.text.toString()
                    if(mail.isNotEmpty()){
                        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
                            Toast.makeText(this, getString(R.string.el_enlace_para_restablecer_la_contrase_a_ha_sido_enviado_a_su_correo),Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(this, getString(R.string.el_enlace_no_se_ha_podido_enviar, it.message),Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, getString(R.string.favor_de_ingresar_la_direccion_de_correo),Toast.LENGTH_SHORT).show()
                    }
                }.setNegativeButton(getString(R.string.cancelar)){ dialog, _->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun validaCampos(): Boolean{
        email = binding.tietEmail.text.toString().trim() //para que quite espacios en blanco
        contrasenia = binding.tietContrasenia.text.toString().trim()

        if(email.isEmpty()){
            binding.tietEmail.error = getString(R.string.se_requiere_el_correo)
            binding.tietEmail.requestFocus()
            return false
        }

        if(contrasenia.isEmpty() || contrasenia.length < 6){
            binding.tietContrasenia.error = getString(R.string.se_requiere_una_contrase_a_o_la_contrase_a_no_tiene_por_lo_menos_6_caracteres)
            binding.tietContrasenia.requestFocus()
            return false
        }

        return true
    }

    private fun manejaErrores(task: Task<AuthResult>){
        var errorCode = ""

        try{
            errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
            e.printStackTrace()
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this,
                    getString(R.string.error_el_correo_electr_nico_no_tiene_un_formato_correcto), Toast.LENGTH_SHORT).show()
                binding.tietEmail.error =  getString(R.string.error_el_correo_electr_nico_no_tiene_un_formato_correcto)
                binding.tietEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this,
                    getString(R.string.error_la_contrase_a_no_es_v_lida), Toast.LENGTH_SHORT).show()
                binding.tietContrasenia.error = getString(R.string.la_contrase_a_no_es_v_lida)
                binding.tietContrasenia.requestFocus()
                binding.tietContrasenia.setText("")

            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this,
                    getString(R.string.error_una_cuenta_ya_existe_con_el_mismo_correo_pero_con_diferentes_datos_de_ingreso), Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, getString(R.string.error_el_correo_electr_nico_ya_est_en_uso_con_otra_cuenta), Toast.LENGTH_LONG).show()
                binding.tietEmail.error = getString(R.string.error_el_correo_electr_nico_ya_est_en_uso_con_otra_cuenta)
                binding.tietEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, getString(R.string.error_la_sesi_n_ha_expirado_favor_de_ingresar_nuevamente), Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, getString(R.string.error_no_existe_el_usuario_con_la_informaci_n_proporcionada), Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, getString(R.string.la_contrase_a_porporcionada_es_inv_lida), Toast.LENGTH_LONG).show()
                binding.tietContrasenia.error = getString(R.string.la_contrase_a_debe_de_tener_por_lo_menos_6_caracteres)
                binding.tietContrasenia.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, getString(R.string.red_no_disponible_o_se_interrumpi_la_conexi_n), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, getString(R.string.error_no_se_pudo_autenticar_exitosamente), Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun autenticaUsuario(usr: String, pws: String){
        firebaseAuth.signInWithEmailAndPassword(usr,pws).addOnCompleteListener{ authResult->
            if(authResult.isSuccessful){

                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("psw",pws)
                startActivity(intent)
                finish()
            }else{
                binding.progressBar.visibility = View.GONE
                manejaErrores(authResult)
            }

        }
    }

}