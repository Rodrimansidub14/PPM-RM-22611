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
package com.raywenderlich.rwnews.repository.impl

import com.raywenderlich.rwnews.repository.NewsRepository
import com.raywenderlich.rwnews.repository.entity.News
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation in memory
 */
@Singleton
class MemoryNewsRepository @Inject constructor() : NewsRepository {

  private val newsMap = mutableMapOf<Long, News>()

  init {
    (1..100).map { it.toLong() }.forEach { id ->
      insert(
          News(
              id,
              "Lorem Ipsum #$id",
              "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
                  "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam," +
                  " quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. " +
                  "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu " +
                  "fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
                  "culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis " +
                  "iste natus error sit voluptatem accusantium doloremque laudantium, totam rem " +
                  "aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae " +
                  "vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur " +
                  "aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem " +
                  "sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, " +
                  "consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut " +
                  "labore et dolore magnam aliquam quaerat voluptatem."
          )
      )
    }
  }

  override fun byId(id: Long): News? = newsMap[id]

  override fun list(): List<News> = newsMap.values.sortedBy { it.id }

  override fun insert(news: News) {
      newsMap[news.id] = news
  }
}
