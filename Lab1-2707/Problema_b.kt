package com.example.lab1
data class ItemData(
    val originalPos: Int,
    val originalValue: Any?,
    var type: String?,
    var info: String?
)

fun processInput(inputList: List<Any?>?): List<ItemData>? {
    inputList?.let {
        if (it.isEmpty()) {
            return emptyList()
        }

        return it.mapIndexedNotNull { index, item ->
            item?.let {
                val itemData = ItemData(index, it, null, null)

                when (it) {
                    is Int -> {
                        itemData.type = "entero"
                        itemData.info = when {
                            it % 10 == 0 -> "M10"
                            it % 5 == 0 -> "M5"
                            it % 2 == 0 -> "M2"
                            else -> null
                        }
                    }
                    is String -> {
                        itemData.type = "cadena"
                        itemData.info = "L${it.length}"
                    }
                    is Boolean -> {
                        itemData.type = "booleano"
                        itemData.info = if (it) "Verdadero" else "Falso"
                    }
                    else -> {
                        itemData.type = null
                        itemData.info = null
                    }
                }
                itemData
            }
        }
    } ?: return null
}

fun main() {
    val input = listOf<Any?>(10, "Nombre", true, null, 5, 2)
    val result = processInput(input)
    println(result)
}

