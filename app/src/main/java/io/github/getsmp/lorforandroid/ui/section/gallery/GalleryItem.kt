/*
 * Copyright 2016 getsmp
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.getsmp.lorforandroid.ui.section.gallery

import io.github.getsmp.lorforandroid.ui.section.Item

class GalleryItem(url: String, title: String, groupTitle: String?, tags: String, date: String, author: String, comments: String, val imageUrl: String, val medium2xImageUrl: String, val mediumImageUrl: String) : Item(url, title, groupTitle, tags, date, author, comments)
