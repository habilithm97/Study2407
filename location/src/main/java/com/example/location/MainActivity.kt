package com.example.location

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.location.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var locationClient: FusedLocationProviderClient // 효율적인 위치 정보 관리

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions() // 위치 권한 요청
        ) {
            if (it.all { permission -> permission.value }) { // 권한이 모두 승인되면
                getLastLocation() // 위치 정보 가져오기
            } else {
                Toast.makeText(this@MainActivity, "권한이 거부되었습니다. ", Toast.LENGTH_SHORT).show()
            }
        }
        // SupportMapFragment를 찾아서 초기화하고 비동기적으로 지도 가져오기
        (supportFragmentManager.findFragmentById(R.id.main) as SupportMapFragment).getMapAsync(this@MainActivity)

        // Google play Services의 위치 제공 api를 사용하여 위치 정보를 가져올 수 있음
        locationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)

        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
            !== PackageManager.PERMISSION_GRANTED) { // 권한이 승인되지 않으면
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)) // 권한 요청
        } else { // 권한이 이미 승인되었으면
            getLastLocation() // 위치 정보 가져오기
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) { // 위치 가져오기 성공
                val currentLatLng = LatLng(location.latitude, location.longitude)
                val mapFragment = supportFragmentManager.findFragmentById(R.id.main) as SupportMapFragment
                mapFragment.getMapAsync { googleMap ->
                    googleMap.addMarker(MarkerOptions().position(currentLatLng).title("현재 위치"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                }
            } else { // 위치 가져오기 실패
                Toast.makeText(this, "위치를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onMapReady(p0: GoogleMap) {}
}