package com.raywenderlich.rwnews.ui.detail

import com.raywenderlich.rwnews.databinding.NewsDetailLayoutBinding
import com.raywenderlich.rwnews.repository.entity.News

/** ViewBinder for the NewsList */
interface NewsDetailViewBinder {

  fun init(binding: NewsDetailLayoutBinding)

  fun bind(model: News)
}