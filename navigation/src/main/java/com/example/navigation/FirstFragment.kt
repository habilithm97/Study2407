package com.example.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    /**
     _binding : 뷰가 존재하는 동안에만 유효, 뷰가 파괴되면 null
     binding : 항상 non-null -> UI 요소에 접근할 때 안전하게 사용 가능
      (실제로는 _binding을 통해 값이 전달되기 때문에 사용중에는 null이 아님을 보장함)
     */
    private var _binding: FragmentFirstBinding? = null // nullable -> 프래그먼트 뷰가 소멸될 때 바인딩 객체를 해제하기 위함
    private val binding get() = _binding!! // null 체크 없이 안전하게 binding 사용 가능

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 릭 방지
    }
}