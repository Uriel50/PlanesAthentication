package com.camu.planeslufthansa.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.camu.planeslufthansa.R
import com.camu.planeslufthansa.data.remote.model.PlaneDto
import com.camu.planeslufthansa.application.PlanesRFApp
import com.camu.planeslufthansa.data.PlaneRepository
import com.camu.planeslufthansa.databinding.FragmentPlanesListBinding
import com.camu.planeslufthansa.ui.LoginActivity
import com.camu.planeslufthansa.ui.adapters.PlanesAdapter
import com.camu.planeslufthansa.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlanesListFragment :Fragment(){
    private var _binding : FragmentPlanesListBinding? =  null
    private val binding get() = _binding!!

    private lateinit var  repository: PlaneRepository

    private var user: FirebaseUser? = null
    private var userId: String? = null

    private lateinit var firebaseAuth:FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPlanesListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = PlanesRFApp().repository

        lifecycleScope.launch {
            val call: Call<List<PlaneDto>> = repository.getPlanesApiary()

            call.enqueue(object : Callback<List<PlaneDto>>{
                override fun onResponse(
                    call: Call<List<PlaneDto>>,
                    response: Response<List<PlaneDto>>
                ) {
                    binding.pbLoading.visibility = View.GONE


                    response.body()?.let { planes ->
                        binding.rvGames.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = PlanesAdapter(planes){ plane ->
                                plane.id?.let { id ->
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, PlaneDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<PlaneDto>>, t: Throwable) {
                    val msgerror = t.message
                    val errorMsg = getString(R.string.error, msgerror)
                    Log.d(Constants.LOGTAG, errorMsg)

                    val mensajeNoConexion = getString(R.string.no_conexion)
                    Toast.makeText(requireActivity(), mensajeNoConexion, Toast.LENGTH_SHORT).show()

                    binding.pbLoading.visibility = View.GONE
                }
            })
        }

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser
        userId = user?.uid

        binding.tvUsuario.text = getString(R.string.welcome,user?.email)

        binding.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }






    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}