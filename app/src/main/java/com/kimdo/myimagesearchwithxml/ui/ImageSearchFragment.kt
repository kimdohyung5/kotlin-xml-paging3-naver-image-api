package com.kimdo.myimagesearchwithxml.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.kimdo.myimagesearchwithxml.databinding.FragmentFavoritesBinding
import com.kimdo.myimagesearchwithxml.databinding.FragmentImageSearchBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ImageSearchFragment : Fragment() {

    private lateinit var imageSearchViewModel: ImageSearchViewModel
    private val adapter: ImageSearchAdapter = ImageSearchAdapter() { item ->
        imageSearchViewModel.toggle(item)
    }

    private var _binding: FragmentImageSearchBinding? = null
    private val binding: FragmentImageSearchBinding get()= _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageSearchViewModel = ViewModelProvider(requireActivity())[ImageSearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            imageSearchViewModel.pagingDataFlow.collectLatest { items -> adapter.submitData(items) }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.search.setOnClickListener{
            imageSearchViewModel.handleQuery(query = binding.editText.text.trim().toString() )
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}