/*
 * Copyright (C) 2015-2021 Nikola Trubitsyn (getsmp)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dev.trubitsyn.lorforandroid.site

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

class JsoupParser private constructor(): HtmlParser {
    val adapters = WeakHashMap<String, DocumentAdapter<*>>()

    override fun parse(document: String): Document {
        return Jsoup.parse(document)
    }

    override fun getAdapter(type: Type): DocumentAdapter<*>? {
        val typeName = type.toString()
        return adapters[typeName]
    }

    class Builder {
        val instance = JsoupParser()

        @OptIn(ExperimentalStdlibApi::class)
        inline fun <reified T> registerDocumentAdapter(adapter: DocumentAdapter<T>) = apply {
            val typeWithExtends = object : TypeReference<T>() {}.type
            val typeWithoutExtends = typeOf<T>().javaType
            registerAdapterOfType(typeWithExtends, typeWithoutExtends, adapter)
        }

        fun registerAdapterOfType(type1: Type, type2: Type, adapter: DocumentAdapter<*>) {
            print(type1.toString())
            print(type2.toString())
            instance.adapters[type1.toString()] = adapter
        }

        fun build() = instance
    }

    abstract class TypeReference<T> {
        val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
    }
}