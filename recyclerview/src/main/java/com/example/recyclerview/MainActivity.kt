package com.example.recyclerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var rvAdapter: RvAdapter
    private lateinit var rvListAdapter: RvListAdapter

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
        val items = listOf(
            Item(0, "ListAdapter 아이템1"),
            Item(1, "ListAdapter 아이템2"),
            Item(2, "ListAdapter 아이템3"),
            Item(3, "ListAdapter 아이템4"),
            Item(4, "ListAdapter 아이템5")
        )
        rvAdapter = RvAdapter(items)
        rvListAdapter = RvListAdapter()
        rvListAdapter.submitList(items)

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            //adapter = rvAdapter
            adapter = rvListAdapter
        }
    }
}