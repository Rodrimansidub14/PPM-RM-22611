package com.raywenderlich.rwnews.ui.list

import com.raywenderlich.rwnews.databinding.NewsListLayoutBinding
import com.raywenderlich.rwnews.repository.entity.News

/** ViewBinder for the NewsList */
interface NewsListViewBinder {

  fun init(binding: NewsListLayoutBinding)

  fun bind(model: List<News>)
}