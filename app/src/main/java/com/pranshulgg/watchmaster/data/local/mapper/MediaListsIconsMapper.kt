package com.pranshulgg.watchmaster.data.local.mapper

import com.pranshulgg.watchmaster.R
import com.pranshulgg.watchmaster.core.model.MediaListsIcons

fun MediaListsIcons.toIcon(icons: MediaListsIcons): Int {
    return when (icons) {
        MediaListsIcons.FAVORITE -> R.drawable.favorite_24px
        MediaListsIcons.FOLDER_SPECIAL -> R.drawable.folder_special_24px
        MediaListsIcons.STAR -> R.drawable.star_24px
        MediaListsIcons.BOOKMARK -> R.drawable.bookmark_24px
        MediaListsIcons.THEATERS -> R.drawable.theaters_24px
        MediaListsIcons.SCHEDULE -> R.drawable.schedule_24px
        MediaListsIcons.COFFEE -> R.drawable.coffee_24px
        MediaListsIcons.BED -> R.drawable.bed_24px
        MediaListsIcons.AC_UNIT -> R.drawable.ac_unit_24px
        MediaListsIcons.CHAIR -> R.drawable.chair_24px
        MediaListsIcons.OUTDOOR_GRILL -> R.drawable.outdoor_grill_24px
        MediaListsIcons.DINING -> R.drawable.dining_24px
        MediaListsIcons.ELECTRIC_BOLT -> R.drawable.electric_bolt_24px
        MediaListsIcons.PALETTE -> R.drawable.palette_24px
        MediaListsIcons.CLEAR_DAY -> R.drawable.clear_day_24px
        MediaListsIcons.RAINY -> R.drawable.rainy_24px
        MediaListsIcons.DATE_RANGE -> R.drawable.date_range_24px
        MediaListsIcons.TROPHY -> R.drawable.trophy_24px
        MediaListsIcons.HEART_SMILE -> R.drawable.heart_smile_24px
        MediaListsIcons.MOOD -> R.drawable.mood_24px
        MediaListsIcons.MOOD_HEART -> R.drawable.mood_heart_24px
        MediaListsIcons.CAKE -> R.drawable.cake_24px
        MediaListsIcons.SENTIMENT_EXTREMELY_DISSATISFIED -> R.drawable.sentiment_extremely_dissatisfied_24px
        MediaListsIcons.MOOD_BAD -> R.drawable.mood_bad_24px
        MediaListsIcons.SENTIMENT_FRUSTRATED -> R.drawable.sentiment_frustrated_24px
        MediaListsIcons.SENTIMENT_STRESSED -> R.drawable.sentiment_stressed_24px
        MediaListsIcons.BEDTIME -> R.drawable.bedtime_24px
        MediaListsIcons.SWORDS -> R.drawable.swords_24px
        MediaListsIcons.CELEBRATION -> R.drawable.celebration_24px
        MediaListsIcons.FIREPLACE -> R.drawable.fireplace_24px
        MediaListsIcons.LOCAL_PIZZA -> R.drawable.local_pizza_24px
        MediaListsIcons.MYSTERY -> R.drawable.mystery_24px
        MediaListsIcons.QUESTION_MARK -> R.drawable.question_mark_24px
        MediaListsIcons.FLIGHT -> R.drawable.flight_24px
        MediaListsIcons.EXPERIMENT -> R.drawable.experiment_24px
        MediaListsIcons.COGNITION -> R.drawable.cognition_24px
        MediaListsIcons.PSYCHOLOGY -> R.drawable.psychology_24px
        MediaListsIcons.LOCAL_POLICE -> R.drawable.local_police_24px
        MediaListsIcons.FOLDER -> R.drawable.folder_24px
        MediaListsIcons.HEART_BROKEN -> R.drawable.heart_broken_24px
    }
}