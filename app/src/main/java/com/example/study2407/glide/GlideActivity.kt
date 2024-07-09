package com.example.study2407.glide

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.study2407.R
import com.example.study2407.databinding.ActivityGlideBinding

class GlideActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGlideBinding.inflate(layoutInflater) }
    private val img = R.drawable.cube // 리소스 이미지

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
        Glide.with(this@GlideActivity) // 이미지 로딩을 위한 Context 제공
            .load(img)
            //.override(100, 100) // 이미지 크기 강제 조절 -> 크기가 맞지 않을 경우 이미지 왜곡 가능
            //.fitCenter() // 이미지의 원본 비율을 유지하며 뷰에 맞게 조정
            //.centerCrop() // 이미지의 중앙 부분을 유지하며 뷰에 맞게 크롭
            //.circleCrop() // 이미지를 원형으로 크롭
            //.transform() // 커스텀 변환(블러, 회전 등)
            .placeholder(R.drawable.load) // 로딩 이미지
            .error(R.drawable.error) // 에러 이미지
            .into(binding.iv) // 이미지 출력
    }
}