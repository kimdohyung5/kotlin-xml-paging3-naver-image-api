package com.kimdo.myimagesearchwithxml.api

import com.kimdo.myimagesearchwithxml.model.Item

data class ImageSearchResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<Item>
)
