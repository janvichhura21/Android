package com.example.android

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat.getCategory
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.adapter.CategoryAdapter
import com.example.android.adapter.RestaurantsAdapter
import com.example.android.adapter.StoriesAdapter
import com.example.android.databinding.FragmentHomeBinding
import com.example.android.model.Stories
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var locationRequest: LocationRequest
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var arrayList: ArrayList<Stories>
    lateinit var categoryList: ArrayList<Stories>
    lateinit var storiesAdapter: StoriesAdapter
    lateinit var restaurantsAdapter: RestaurantsAdapter
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayList= ArrayList()
        categoryList= ArrayList()
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(requireContext())
        checkLocationPermission()
        getStories()
        getUserCategory()
        binding.resRv.apply {
            restaurantsAdapter= RestaurantsAdapter()
            layoutManager=
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            adapter=restaurantsAdapter
        }
    }

    private fun getUserCategory() {
        categoryList.addAll(listOf(
            Stories(image = R.drawable.pizza),
            Stories( image = R.drawable.roastedchicken),
            Stories( image = R.drawable.salad),
            Stories(image = R.drawable.burger),
        ))
        categoryAdapter=CategoryAdapter(requireContext(),categoryList)
        binding.categoryRv.apply {
            layoutManager=
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter=categoryAdapter
        }
    }

    private fun getStories() {
        arrayList.addAll(listOf(
            Stories(image = R.drawable.first),
            Stories( image = R.drawable.second),
            Stories( image = R.drawable.third),
            Stories(image = R.drawable.first),
            Stories( image = R.drawable.second),
            Stories( image = R.drawable.third),
        ))
        storiesAdapter=StoriesAdapter(requireContext(),arrayList)
        binding.rv.apply {
            layoutManager=
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter=storiesAdapter
        }
    }

    private fun checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(
              requireContext(),
                ACCESS_FINE_LOCATION
        )== PackageManager.PERMISSION_GRANTED){
            checkGps()
        }
        else
        {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(ACCESS_FINE_LOCATION),100)
        }
    }

    private fun checkGps() {
     locationRequest= LocationRequest.create()
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=5000
        locationRequest.fastestInterval=2000

        val builder=LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        builder.setAlwaysShow(true)

        val result=LocationServices.getSettingsClient(
            requireContext().applicationContext
        ).checkLocationSettings(builder.build())

        result.addOnCompleteListener{ task->

            try {

                val response=task.getResult(
                    ApiException::class.java
                )
                Log.d("janvi",response.toString())
                getUserLocation()
            }catch (e:ApiException){
                e.printStackTrace()
                when(e.statusCode){

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->try {

                        val resolveApiException = e as ResolvableApiException
                        resolveApiException.startResolutionForResult(requireActivity(),200)

                    }
                    catch (sendIntentException :IntentSender.SendIntentException){


                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->{

                    }
                }
            }
        }
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_COARSE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED){

            return
        }

        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task->

            val location= task.getResult()

            if (location != null)
            {
                try {

                    val geocoder=Geocoder(requireContext(), Locale.getDefault())
                    val address=geocoder.getFromLocation(location.latitude,location.longitude,1)

                    val addressLine= address?.get(0)?.getAddressLine(0)
                    binding.location.setText(addressLine)
                    val addressLocation= address?.get(0)?.getAddressLine(0)
                    Log.d("janvi",addressLine.toString())
                   // openLocation(addressLocation.toString())
                }
                catch (e:IOException){

                }
            }
        }
    }

    private fun openLocation(location: String) {

        val uri=Uri.parse("geo:0, 0?q=$location")
        val intent=Intent(Intent.ACTION_VIEW,uri)
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }
}