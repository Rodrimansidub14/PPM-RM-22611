/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.raywenderlich.rwnews.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.raywenderlich.rwnews.databinding.NewsListItemLayoutBinding
import com.raywenderlich.rwnews.repository.entity.News
import com.raywenderlich.rwnews.ui.events.OnItemSelectedListener

val DIFF_UTIL = object : DiffUtil.ItemCallback<News>() {
  override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
      oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: News, newItem: News): Boolean =
      oldItem == newItem
}

/**
 * The Adapter for the RecyclerView of the news
 */
class NewsListViewAdapter(
    private val listener: OnItemSelectedListener<News> = { _, _ -> }
) :
    ListAdapter<News, NewsListItemViewHolder>(DIFF_UTIL) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListItemViewHolder {
    val viewBinding = NewsListItemLayoutBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
    return NewsListItemViewHolder(viewBinding, listener)
  }

  override fun onBindViewHolder(holder: NewsListItemViewHolder, position: Int) {
    holder.bind(position, getItem(position))
  }
}