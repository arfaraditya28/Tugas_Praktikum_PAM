package com.example.newsfeedsimulator.model

enum class NewsCategory(val label: String) {
    TEKNOLOGI("Teknologi"),
    OLAHRAGA("Olahraga"),
    HIBURAN("Hiburan"),
    POLITIK("Politik"),
    EKONOMI("Ekonomi")
}

data class News(
    val id: Int,
    val title: String,
    val category: NewsCategory,
    val summary: String,
    val content: String,
    val timestamp: String
)

/**
 * Presentation model hasil transformasi map().
 * Dipakai langsung oleh UI agar format sudah siap tampil.
 */
data class NewsUiModel(
    val id: Int,
    val displayTitle: String,
    val category: NewsCategory,
    val categoryLabel: String,
    val summary: String,
    val timeLabel: String
)

/** Transformasi News -> NewsUiModel (dipakai lewat operator map). */
fun News.toUiModel(): NewsUiModel = NewsUiModel(
    id = id,
    displayTitle = title,
    category = category,
    categoryLabel = category.label,
    summary = summary,
    timeLabel = timestamp
)
